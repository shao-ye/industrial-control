package com.ruoyi.industry.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.industry.domain.ParamModifyRecord;
import com.ruoyi.industry.domain.SlotSerialParam;
import com.ruoyi.industry.domain.SlotSingleParam;
import com.ruoyi.industry.service.SlotSingleParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 单槽参数Controller
 *
 * @author shao
 * @date 2022-04-05
 */
@RestController
@RequestMapping("/industry/SlotSingleParam")
public class SlotSingleParamController {

    private static Logger logger = LoggerFactory.getLogger(SlotSingleParamController.class);

    @Autowired
    private SlotSingleParamService slotSingleParamService;

    // 多线程发送socket的延迟时间（毫秒）
    @Value("${spring.task.sleepMs}")
    private int taskSleepMs;
    // 修改参数发送端口
    @Value("${socket.udp.send_port}")
    private int sendPort;
    // 修改参数发送端口超时时间
    @Value("${socket.udp.timeout}")
    private int timeout;

    /**
     * 控制参数查询
     * @param paramJson
     * @return
     */
    @PostMapping("/list")
    public AjaxResult getList( @RequestBody JSONObject paramJson) {
        long potRoomId = 0L;
        long sectionId = 0L;
        try {
            if (null != paramJson.get("factoryNo") && null != paramJson.get("areaNo")){
                potRoomId = Long.parseLong(paramJson.get("factoryNo").toString());
                sectionId = Long.parseLong(paramJson.get("areaNo").toString());
            } else {
                return AjaxResult.error("必填参数不可为空！");
            }
            return AjaxResult.success(slotSingleParamService.controlParameterObject(potRoomId,sectionId));
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("控制参数查询失败！" + e.toString());
        }
    }

    /**
     * 控制参数修改
     * @param paramJson
     * @return
     */
    @PostMapping("/edit")
    public AjaxResult edit( @RequestBody JSONObject paramJson) {
        try {
            if (null == paramJson.get("factoryNo") || 0 == paramJson.getIntValue("factoryNo")){
                return AjaxResult.error("factoryNo不可为空！");
            }
            // 取到厂号
            int factoryNo = paramJson.getIntValue("factoryNo");
            JSONArray paramList = paramJson.getJSONArray("data");
            List<Long> realSlotNoList = new ArrayList<>();
            // 判断是否有槽号
            for (int i = 0; i < paramList.size(); i ++ ){
                if (null != paramList.getJSONObject(i).get("realSlotNo") && 0 != paramList.getJSONObject(i).getLongValue("realSlotNo")){
                    realSlotNoList.add(paramList.getJSONObject(i).getLongValue("realSlotNo"));
                } else {
                    return AjaxResult.error("控制参数修改失败，缺少槽号信息！");
                }
            }
            // 修改数据
            String editResult = editData(factoryNo, paramList, realSlotNoList);
            return AjaxResult.success(editResult);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("控制参数修改失败！" + e.toString());
        }
    }

    /**
     * 修改数据
     * @param factoryNo 厂号
     * @param paramList 修改参数集合
     * @param realSlotNoList 修改槽号集合
     * @throws IOException
     * @throws InterruptedException
     */
    private synchronized String editData(int factoryNo, JSONArray paramList, List<Long> realSlotNoList) throws IOException, InterruptedException {
        // 从数据库中取出旧数据-单槽参数
        List<SlotSingleParam> slotSingleParamList = slotSingleParamService.getSlotSingleParamList(realSlotNoList);
        // 从数据库中取出旧数据-系列参数
        List<SlotSerialParam> slotSerialParamList = slotSingleParamService.getSlotSerialParamList(realSlotNoList);
        // 创建记录修改值集合
        List<ParamModifyRecord> paramModifyRecordList = new ArrayList<>();
        // 有数据变化的槽号集合
        JSONArray changeRealSlotNoList = new JSONArray();
        // 整合变化数据,得到待修改数据数量
        Map<String,Integer> editCountMap = slotSingleParamService.organizeChangeData(paramList,paramModifyRecordList,slotSingleParamList,slotSerialParamList,changeRealSlotNoList);
        // 发送socket
//        sendSocket(slotSingleParamList);
        // 保存修改数据，得到保存数据数量
        Map<String,Integer> saveResult = saveData(factoryNo, changeRealSlotNoList,paramModifyRecordList,slotSingleParamList,slotSerialParamList);
        if (null != saveResult){
            return "待修改" + editCountMap.get("slotSingleCount") + "个槽单槽参数，" + editCountMap.get("slotSerialCount") + "个槽系列参数，"
                    + "实际校验并修改" + saveResult.get("saveSlotSingleCount") + "个槽单槽参数，" + saveResult.get("saveSlotSerialCount") + "个槽系列参数";
        }else {
            return "有正在执行的任务,无法创建发送socket，请稍后再试";
        }
    }

    /**
     * 保存修改数据
     * @param changeRealSlotNoList 修改槽号集合
     * @param paramModifyRecordList 修改记录集合
     * @param slotSingleParamList 单槽参数数据集合
     * @param slotSerialParamList 系列参数数据集合
     */
    @Transactional
    public Map<String,Integer> saveData(int factoryNo, JSONArray changeRealSlotNoList, List<ParamModifyRecord> paramModifyRecordList, List<SlotSingleParam> slotSingleParamList, List<SlotSerialParam> slotSerialParamList) throws IOException, InterruptedException {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 单槽参数数据修改计数器
        AtomicLong slotSingleCount = new AtomicLong(0);
        // 系列参数数据修改计数器
        AtomicLong slotSerialCount = new AtomicLong(0);
        // 设置开启线程数
        final CountDownLatch latch = new CountDownLatch(changeRealSlotNoList.size());
        // 开启socket发送端口
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(sendPort);
        } catch (SocketException e) {
            // 开启socket发送端口报错，有正在执行的任务
            logger.info("开启socket发送端口报错，有正在执行的任务");
            return null;
        }
        List<String> slotSingleList = new ArrayList<>();
        List<String> slotSerialList = new ArrayList<>();
        try {
            // 设置超时时间
            socket.setSoTimeout(timeout);
            for (int i = 0; i < changeRealSlotNoList.size(); i ++ ){
                JSONObject changeRealSlotNoJson = changeRealSlotNoList.getJSONObject(i);
                if (0 < taskSleepMs){
                    // 延时启动多线程
                    Thread.sleep(taskSleepMs);
                }
                // 开启多线程保存数据
                slotSingleParamService.threadSaveDate(factoryNo,socket,changeRealSlotNoJson,slotSingleParamList,slotSerialParamList,latch,slotSingleCount,slotSerialCount,user.getUserName());
            }
            // 等待线程全部执行完成
            latch.await();
            logger.info("单槽参数数据修改计数器" + slotSingleCount);
            logger.info("系列参数数据修改计数器" + slotSerialCount);

            while (true) {
                byte[] buffer = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    // 等待接收消息
                    socket.receive(packet);
                }catch (SocketTimeoutException e){
                    logger.info("捕获socket超时异常");
                    break;
                }
                int packetLength = packet.getLength();
                logger.info("收到数据长度：" + packetLength);
                // 根据长度判断是单槽参数数据
                if (164 == packetLength){
                    logger.info("收到单槽参数数据!");
                    byte receiveByteArray[] = new byte[packetLength];
                    System.arraycopy(buffer, 0, receiveByteArray, 0, packetLength);
                    // 记录校验通过的槽号
                    slotSingleList.add(String.valueOf(SlotSingleParam.toSlotSingleParam(receiveByteArray).getRealSlotNo()));
                }

                // 根据长度判断是系列参数数据
                if (216 == packetLength){
                    logger.info("收到系列参数数据!");
                    byte receiveByteArray[] = new byte[packetLength];
                    System.arraycopy(buffer, 0, receiveByteArray, 0, packetLength);
                    // 记录校验通过的槽号
                    slotSerialList.add(String.valueOf(SlotSerialParam.toSlotSerialParam(receiveByteArray).getRealSlotNo()));
                }
                // 如果收到的数据与发送的数据条数相同，结束等待
                if ((slotSingleCount.intValue() + slotSerialCount.intValue()) == (slotSingleList.size() + slotSerialList.size())){
                    break;
                }
            }
        } finally {
            // 关闭资源
            socket.close();
        }
        // 修改单槽参数计数器
        int slotSingleSaveCount = 0;
        // 修改系列参数计数器
        int slotSerialSaveCount = 0;
        if (0 < slotSingleList.size()) {
            // 去重
            slotSingleList = slotSingleList.stream().distinct().collect(Collectors.toList());
            // 保存单槽参数
            slotSingleSaveCount = slotSingleParamService.saveSlotSingleParamList(slotSingleList, slotSingleParamList, paramModifyRecordList);
        }
        if (0 < slotSerialList.size()) {
            // 去重
            slotSerialList = slotSerialList.stream().distinct().collect(Collectors.toList());
            // 保存系列参数
            slotSerialSaveCount = slotSingleParamService.saveSlotSerialParamList(slotSerialList, slotSerialParamList, paramModifyRecordList);
        }

        Map<String,Integer> saveMap = new HashMap<>();
        // 保存单槽参数数量
        saveMap.put("saveSlotSingleCount",slotSingleSaveCount);
        // 保存系列参数数量
        saveMap.put("saveSlotSerialCount",slotSerialSaveCount);
        return saveMap;
    }



    private static final String SERVER_HOSTNAME = "192.168.252.155";
    // 服务器端口
    private static final int SERVER_PORT = 9017;
    // 本地发送端口
    private static final int LOCAL_PORT = 9011;
    // 发送socket验证测试代码
    private void sendSocket(List<SlotSingleParam> slotSingleParamList) throws IOException {
        SlotSingleParam slotSingleParam = slotSingleParamList.get(0);
        slotSingleParam.setbModifySend(1);
        slotSingleParam.setSignTime(new Date());
        slotSingleParam.setAuthor("admin");
//        String str = slotSingleParam.toValueString();
        // 1，创建udp服务。通过DatagramSocket对象。
        DatagramSocket socket = new DatagramSocket(LOCAL_PORT);
        try {

            // 2，确定数据，并封装成数据包。DatagramPacket(byte[] buf, int length, InetAddress
            // address, int port)
            //        byte[] buf = str.getBytes();
            byte[] buf = slotSingleParam.toBytes();
            System.out.println(buf.length);
            System.out.println("buf::"+Arrays.toString(buf));
            System.out.println(JSONObject.toJSONString(slotSingleParam));
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(SERVER_HOSTNAME),
                    SERVER_PORT);
            // 设置超时时间
            socket.setSoTimeout(2000);
            // 3，通过socket服务，将已有的数据包发送出去。通过send方法。
            socket.send(dp);
            slotSingleParam.setRealSlotNo(201L);
            byte[] buf2 = slotSingleParam.toBytes();
            DatagramPacket dp2 = new DatagramPacket(buf2, buf2.length, InetAddress.getByName(SERVER_HOSTNAME),
                    SERVER_PORT);
            socket.send(dp2);
            while (true) {
                byte[] buffer = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                }catch (SocketTimeoutException e){
                    System.out.println("捕获socket超时异常");
                    break;
                }
                int packetLength = packet.getLength();
                System.out.println("收到数据长度：" + packetLength);
                // 根据长度判断是单槽参数数据
                if (164 == packetLength){
                    logger.info("收到单槽参数数据!");
                    byte receiveByteArray[] = new byte[packetLength];
                    System.arraycopy(buffer, 0, receiveByteArray, 0, packetLength);
                    System.out.println(SlotSingleParam.toSlotSingleParam(receiveByteArray).getRealSlotNo());
                }

                /*byte[] data_byte = packet.getData();
                if(data_byte != null && data_byte.length == 164){
                    System.out.println(JSONObject.toJSONString(SlotSingleParam.toSlotSingleParam(data_byte)));
                }else{
                    System.out.println("指定哪有点毛病");
                }*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 4，关闭资源。
            socket.close();
        }

    }
}
