package com.ruoyi.industry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.industry.domain.SlotSerialParam;
import com.ruoyi.industry.domain.SlotSingleParam;
import com.ruoyi.industry.mapper.IndFactoryAreaMapper;
import com.ruoyi.industry.mode.UdpComputerPort;
import com.ruoyi.industry.service.SlotSingleParamSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SlotSingleParamSubServiceImpl implements SlotSingleParamSubService {

    @Autowired
    private IndFactoryAreaMapper indFactoryAreaMapper;

    @Autowired
    private UdpComputerPort udpComputerPort;

    /**
     * 单槽参数数据修改线程
     * @param realSlotNo 要保存的槽号
     * @param slotSingleParamList 单槽参数数据集
     * @param subLatch 线程标记
     * @param atomicLong 修改计数器
     */
    @Override
    @Transactional
    @Async("SlotThreadPool")
    public void saveSlotSingleParam(int factoryNo, DatagramSocket socket, String realSlotNo, List<SlotSingleParam> slotSingleParamList, CountDownLatch subLatch, AtomicLong atomicLong, String userName) {
        try {
            for (int j = 0; j < slotSingleParamList.size(); j ++ ){
                // 根据realSlotNo从单槽参数数据集合中找到要修改的数据
                if (realSlotNo.equals(String.valueOf(slotSingleParamList.get(j).getRealSlotNo()))){
                    // 修改标记
                    slotSingleParamList.get(j).setbModifySend(1);
                    // 当前修改人
                    slotSingleParamList.get(j).setAuthor(userName);
                    // 当前修改时间
                    slotSingleParamList.get(j).setSignTime(new Date());
                    // 发送socket验证
                    DatagramPacket datagramPacket = createUdp(factoryNo,slotSingleParamList.get(j).toBytes());
                    socket.send(datagramPacket);
                    // 记录成功保存次数
                    atomicLong.incrementAndGet();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            // 线程执行结束标记
            subLatch.countDown();
        }
    }

    /**
     * 系列参数数据修改线程
     * @param realSlotNo 要保存的槽号
     * @param slotSerialParamList 系列参数数据集合
     * @param subLatch 线程标记
     * @param atomicLong 修改计数器
     */
    @Override
    @Transactional
    @Async("SlotThreadPool")
    public void saveSlotSerialParam(int factoryNo, DatagramSocket socket, String realSlotNo, List<SlotSerialParam> slotSerialParamList, CountDownLatch subLatch, AtomicLong atomicLong, String userName) {
        try {
            for (int j = 0; j < slotSerialParamList.size(); j ++ ){
                // 根据realSlotNo从系列参数数据集合中找到要修改的数据
                if (realSlotNo.equals(String.valueOf(slotSerialParamList.get(j).getRealSlotNo()))){
                    // 修改标记
                    slotSerialParamList.get(j).setbModifySend(1);
                    // 当前修改人
                    slotSerialParamList.get(j).setAuthor(userName);
                    // 当前修改时间
                    slotSerialParamList.get(j).setSignTime(new Date());
                    // 发送socket验证
                    DatagramPacket datagramPacket = createUdp(factoryNo,slotSerialParamList.get(j).toBytes());
                    socket.send(datagramPacket);
                    // 记录成功保存次数
                    atomicLong.incrementAndGet();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            // 线程执行结束标记
            subLatch.countDown();
        }
    }

    /**
     * 创建udp对象
     * @param factoryNo 厂号
     * @return udp对象
     * @throws UnknownHostException 创建udp失败异常
     */
    private DatagramPacket createUdp(int factoryNo, byte[] byteParam) throws UnknownHostException {
        // 获取厂ip地址
        String factoryIp = indFactoryAreaMapper.getFactoryIp(factoryNo).trim();
        // 获取厂端口号
        int port = udpComputerPort.getPortJson().getIntValue(String.valueOf(factoryNo));
        // 创建udp对象
        return new DatagramPacket(byteParam, byteParam.length, InetAddress.getByName(factoryIp), port);
    }


}
