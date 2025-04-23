package com.ruoyi.industry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.industry.domain.ParamModifyRecord;
import com.ruoyi.industry.domain.SlotSerialParam;
import com.ruoyi.industry.domain.SlotSingleParam;
import com.ruoyi.industry.mapper.ParamModifyRecordMapper;
import com.ruoyi.industry.mapper.SlotSerialParamMapper;
import com.ruoyi.industry.mapper.SlotSingleParamMapper;
import com.ruoyi.industry.service.SlotSingleParamService;
import com.ruoyi.industry.service.SlotSingleParamSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.DatagramSocket;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SlotSingleParamServiceImpl implements SlotSingleParamService {

    @Autowired
    private SlotSingleParamMapper slotSingleParamMapper;
    @Autowired
    private SlotSerialParamMapper slotSerialParamMapper;
    @Autowired
    private ParamModifyRecordMapper paramModifyRecordMapper;
    @Autowired
    private SlotSingleParamSubService slotSingleParamSubService;

    @Override
    public JSONObject controlParameterObject(long potRoomId, long sectionId) {
        JSONObject jsonObject = new JSONObject();
        // 常用参数
        jsonObject.put("commonParam",slotSingleParamMapper.commonParamList(potRoomId,sectionId));
        // 控制开关
        jsonObject.put("controlSwitch",slotSingleParamMapper.controlSwitchList(potRoomId,sectionId));
        // 设定范围
        jsonObject.put("predeterminedArea",slotSerialParamMapper.predeterminedAreaList(potRoomId,sectionId));
        // 操作过程函数
        jsonObject.put("operationProcess",slotSerialParamMapper.operationProcessList(potRoomId,sectionId));
        // 浓度参数
        jsonObject.put("concentrationParam",slotSerialParamMapper.concentrationParamList(potRoomId,sectionId));
        // 执行动作参数
        jsonObject.put("performAction",slotSerialParamMapper.performActionParamList(potRoomId,sectionId));
        return jsonObject;
    }

    @Override
    public List<SlotSingleParam> getSlotSingleParamList(List<Long> realSlotNoList) {
        return slotSingleParamMapper.getSlotSingleParamList(realSlotNoList);
    }

    @Override
    public List<SlotSerialParam> getSlotSerialParamList(List<Long> realSlotNoList) {
        return slotSerialParamMapper.getSlotSerialParamList(realSlotNoList);
    }

    /**
     * 整合变化数据
     * @param paramList 修改数据
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSingleParamList 单槽参数
     * @param slotSerialParamList 系列参数
     * @param changeRealSlotNoList 数据变化的槽号集合
     */
    @Override
    public Map<String,Integer> organizeChangeData(JSONArray paramList, List<ParamModifyRecord> paramModifyRecordList, List<SlotSingleParam> slotSingleParamList, List<SlotSerialParam> slotSerialParamList, JSONArray changeRealSlotNoList) {
        // 单槽参数计数器
        int slotSingleCount = 0;
        // 系列参数计数器
        int slotSerialCount = 0;
        for (int i = 0; i < paramList.size(); i ++ ){
            JSONObject jsonObject = new JSONObject();
            Long realSlotNo = paramList.getJSONObject(i).getLongValue("realSlotNo");
            // 单槽参数数据修改标记
            boolean slotSingleUpdateMark = false;
            // 系列参数数据修改标记
            boolean slotSerialUpdateMark = false;
            // 常用参数
            if (null != paramList.getJSONObject(i).getJSONObject("commonParam")){
                // 取到修改了的常用参数
                JSONObject commonParamJson = paramList.getJSONObject(i).getJSONObject("commonParam");
                // 整合 常用参数 数据
                slotSingleUpdateMark = adjustCommonParam(slotSingleUpdateMark,realSlotNo,commonParamJson,paramModifyRecordList,slotSingleParamList);
            }
            // 控制开关
            if (null != paramList.getJSONObject(i).getJSONObject("controlSwitch")){
                // 取到修改了的常用参数
                JSONObject controlSwitchJson = paramList.getJSONObject(i).getJSONObject("controlSwitch");
                // 整合 常用参数 数据
                slotSingleUpdateMark = adjustControlSwitch(slotSingleUpdateMark,realSlotNo,controlSwitchJson,paramModifyRecordList,slotSingleParamList);
            }
            // 特殊过程参数
            if (null != paramList.getJSONObject(i).getJSONObject("operationProcess")){
                // 取到了修改了的特殊过程参数
                JSONObject operationProcessJson = paramList.getJSONObject(i).getJSONObject("operationProcess");
                // 整合 特殊过程参数 数据
                slotSerialUpdateMark = adjustOperationProcess(slotSerialUpdateMark,realSlotNo,operationProcessJson,paramModifyRecordList,slotSerialParamList);
            }
            // 执行机构参数
            if (null != paramList.getJSONObject(i).getJSONObject("performAction")){
                // 取到了修改了的执行机构参数
                JSONObject performActionJson = paramList.getJSONObject(i).getJSONObject("performAction");
                // 整合 执行机构参数 数据
                slotSerialUpdateMark = adjustPerformAction(slotSerialUpdateMark,realSlotNo,paramModifyRecordList,slotSerialParamList,performActionJson);
            }
            // 浓度设定
            if (null != paramList.getJSONObject(i).getJSONObject("concentrationParam")){
                // 取到了修改了的浓度设定
                JSONObject concentrationParamJson = paramList.getJSONObject(i).getJSONObject("concentrationParam");
                // 整合 浓度设定 数据
                slotSerialUpdateMark = adjustConcentration(slotSerialUpdateMark,realSlotNo,paramModifyRecordList,slotSerialParamList,concentrationParamJson);
            }
            // 设定范围
            if (null != paramList.getJSONObject(i).getJSONObject("predeterminedArea")){
                // 取到了修改了的设定范围
                JSONObject predeterminedAreaJson = paramList.getJSONObject(i).getJSONObject("predeterminedArea");
                // 整合 设定范围 数据
                slotSerialUpdateMark = adjustPredeterminedArea(slotSerialUpdateMark,realSlotNo,paramModifyRecordList,slotSerialParamList,predeterminedAreaJson);
            }
            // 数据有改动，加入有数据变化的槽号集合
            if (slotSingleUpdateMark || slotSerialUpdateMark){
                jsonObject.put("realSlotNo",realSlotNo);
                jsonObject.put("slotSingleUpdateMark",slotSingleUpdateMark);
                jsonObject.put("slotSerialUpdateMark",slotSerialUpdateMark);
                changeRealSlotNoList.add(jsonObject);
                // 计数
                if (slotSingleUpdateMark){
                    slotSingleCount ++;
                }
                if (slotSerialUpdateMark){
                    slotSerialCount ++;
                }
            }
        }
        Map<String,Integer> editMap = new HashMap<>();
        // 待修改单槽参数数量
        editMap.put("slotSingleCount",slotSingleCount);
        // 待修改系列参数数量
        editMap.put("slotSerialCount",slotSerialCount);
        return editMap;
    }

    /**
     * 开启多线程保存数据
     * @param changeRealSlotNoJson 修改槽号集合
     * @param slotSingleParamList 单槽参数数据
     * @param slotSerialParamList 系列参数数据
     * @param latch 线程标记
     * @param slotSingleCount 单槽参数计数器
     * @param slotSerialCount 系列参数计数器
     */
    @Override
    @Async("SlotThreadPool")
    public void threadSaveDate(int factoryNo, DatagramSocket socket, JSONObject changeRealSlotNoJson, List<SlotSingleParam> slotSingleParamList, List<SlotSerialParam> slotSerialParamList, CountDownLatch latch, AtomicLong slotSingleCount, AtomicLong slotSerialCount, String userName) {
        try {
            int threadCount = 0;
            // 单槽参数数据修改标记
            if (changeRealSlotNoJson.getBoolean("slotSingleUpdateMark")){
                threadCount ++;
            }
            // 系列参数数据修改标记
            if (changeRealSlotNoJson.getBoolean("slotSerialUpdateMark")){
                threadCount ++;
            }
            // 设置开启线程数
            final CountDownLatch subLatch = new CountDownLatch(threadCount);
            // 单槽参数数据修改标记
            if (changeRealSlotNoJson.getBoolean("slotSingleUpdateMark")){
                // 单槽参数数据修改线程
                slotSingleParamSubService.saveSlotSingleParam(factoryNo, socket, changeRealSlotNoJson.getString("realSlotNo"),slotSingleParamList,subLatch,slotSingleCount,userName);
            }
            // 系列参数数据修改标记
            if (changeRealSlotNoJson.getBoolean("slotSerialUpdateMark")){
                // 系列参数数据修改线程
                slotSingleParamSubService.saveSlotSerialParam(factoryNo, socket, changeRealSlotNoJson.getString("realSlotNo"),slotSerialParamList,subLatch,slotSerialCount,userName);
            }
            try {
                // 等待单槽参数数据和系列参数数据修改完成
                subLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 线程执行结束标记
            latch.countDown();
        }

    }

    /**
     * 保存单槽参数
     * @param slotSingleList 待修改单槽参数
     * @param slotSingleParamList 单槽参数集合
     * @param paramModifyRecordList 修改记录集合
     */
    @Override
    @Transactional
    public int saveSlotSingleParamList(List<String> slotSingleList, List<SlotSingleParam> slotSingleParamList, List<ParamModifyRecord> paramModifyRecordList) {
        // 计数器
        int count = 0;
        for (int i = 0; i < slotSingleList.size(); i ++ ){
            for (int j = 0; j < slotSingleParamList.size(); j ++ ){
                if (slotSingleList.get(i).equals(String.valueOf(slotSingleParamList.get(j).getRealSlotNo()))){
                    // 保存单槽参数
                    count += slotSingleParamMapper.insertSlotSingleParam(slotSingleParamList.get(j));
                    // 保存修改记录
                    saveParamModifyRecordList(slotSingleList.get(i), paramModifyRecordList);
                }
            }
        }
        return count;
    }

    /**
     * 保存系列参数
     * @param slotSerialList 待修改系列参数
     * @param slotSerialParamList 系列参数集合
     * @param paramModifyRecordList 修改记录集合
     */
    @Override
    @Transactional
    public int saveSlotSerialParamList(List<String> slotSerialList, List<SlotSerialParam> slotSerialParamList, List<ParamModifyRecord> paramModifyRecordList) {
        // 修改计数器
        int count = 0;
        for (int i = 0; i < slotSerialList.size(); i ++ ){
            for (int j = 0; j < slotSerialParamList.size(); j ++ ){
                if (slotSerialList.get(i).equals(String.valueOf(slotSerialParamList.get(j).getRealSlotNo()))){
                    // 保存系列参数
                    count += slotSerialParamMapper.insertSlotSerialParam(slotSerialParamList.get(j));
                    // 保存修改记录
                    saveParamModifyRecordList(slotSerialList.get(i), paramModifyRecordList);
                }
            }
        }
        return count;
    }

    /**
     * 保存修改记录
     * @param realSlotNo 要保存的槽号
     * @param paramModifyRecordList 修改记录集合
     */
    private void saveParamModifyRecordList(String realSlotNo, List<ParamModifyRecord> paramModifyRecordList) {
        for (int i = 0; i < paramModifyRecordList.size(); i ++ ){
            // 找到要保存的记录修改数据
            if (realSlotNo.equals(String.valueOf(paramModifyRecordList.get(i).getSlotId()))){
                // 保存修改参数记录
                paramModifyRecordMapper.insertParamModifyRecord(paramModifyRecordList.get(i));
            }
        }
    }

    /**
     * 整合 常用参数 数据
     * @param updateMark 数据修改标记
     * @param realSlotNo 修改数据的槽号
     * @param commonParamJson 修改了的常用参数
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSingleParamList 单槽参数
     * @return 数据是否有修改
     */
    private boolean adjustCommonParam(boolean updateMark, Long realSlotNo,JSONObject commonParamJson,List<ParamModifyRecord> paramModifyRecordList,List<SlotSingleParam> slotSingleParamList){
        // 将修改的了参数放到旧数据结构中
        for (int j = 0; j < slotSingleParamList.size(); j ++ ){
            if (String.valueOf(slotSingleParamList.get(j).getRealSlotNo()).equals(String.valueOf(realSlotNo))){
                // 创建常用参数记录修改值集合
                List<ParamModifyRecord> commonParamModifyRecordList = new ArrayList<>();
                // 设定电压
                if (null != commonParamJson.get("volt")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("volt").getString("value"),String.valueOf(slotSingleParamList.get(j).getVolt()),commonParamJson.getJSONObject("volt").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setVolt(commonParamJson.getJSONObject("volt").getLong("value"));
                }
                // 设定间隔
                if (null != commonParamJson.get("feedIntvl0")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("feedIntvl0").getString("value"),String.valueOf(slotSingleParamList.get(j).getFeedIntvl0()),commonParamJson.getJSONObject("feedIntvl0").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setFeedIntvl0(commonParamJson.getJSONObject("feedIntvl0").getLong("value"));
                }
                // 效应间隔
                if (null != commonParamJson.get("aeIntvl")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("aeIntvl").getString("value"),String.valueOf(slotSingleParamList.get(j).getAeIntvl()),commonParamJson.getJSONObject("aeIntvl").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAeIntvl(commonParamJson.getJSONObject("aeIntvl").getLong("value"));
                }
                // 日氟盐量
                if (null != commonParamJson.get("alf3Mass")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("alf3Mass").getString("value"),String.valueOf(slotSingleParamList.get(j).getAlf3Mass()),commonParamJson.getJSONObject("alf3Mass").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAlf3Mass(commonParamJson.getJSONObject("alf3Mass").getLong("value"));
                }
                // 氟盐料斗容量
                if (null != commonParamJson.get("alf3Intvl")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("alf3Intvl").getString("value"),String.valueOf(slotSingleParamList.get(j).getAlf3Intvl()),commonParamJson.getJSONObject("alf3Intvl").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAlf3Intvl(commonParamJson.getJSONObject("alf3Intvl").getLong("value"));
                }
                // 设定RC脉冲值
                if (null != commonParamJson.get("bvAngle")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("bvAngle").getString("value"),String.valueOf(slotSingleParamList.get(j).getAlf3Intvl()),commonParamJson.getJSONObject("bvAngle").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setBvAngle(commonParamJson.getJSONObject("bvAngle").getLong("value"));
                }
                // 工作状态
                if (null != commonParamJson.get("onOffPrestaSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getJSONObject("onOffPrestaSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getOnOffPrestaSwitch()),commonParamJson.getJSONObject("onOffPrestaSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setOnOffPrestaSwitch(commonParamJson.getJSONObject("onOffPrestaSwitch").getLong("value"));
                }
                        /* 数据库字段类型不匹配
                        // 浓度自适应
                        if (null != commonParamJson.get("adaptiveSwitch")){
                            paramModifyRecordList.add(getParamModifyRecord(realSlotNo,commonParamJson.getBigDecimal("adaptiveSwitch"),slotSingleParamList.get(j).getAdaptiveSwitch(),"浓度自适应"));
                        }
                        */
                // 判断本条数据是否有改动
                if (commonParamModifyRecordList.size() > 0){
                    updateMark = true;
                }
                paramModifyRecordList.addAll(commonParamModifyRecordList);
            }
        }
        return updateMark;
    }

    /**
     * 整合 控制开关 数据
     * @param updateMark 数据修改标记
     * @param realSlotNo 修改数据的槽号
     * @param controlSwitchJson 修改了的控制开关数据
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSingleParamList 单槽参数
     * @return 数据是否有修改
     */
    private boolean adjustControlSwitch(boolean updateMark, Long realSlotNo,JSONObject controlSwitchJson,List<ParamModifyRecord> paramModifyRecordList,List<SlotSingleParam> slotSingleParamList){
        // 将修改的了参数放到旧数据结构中
        for (int j = 0; j < slotSingleParamList.size(); j ++ ){
            if (String.valueOf(slotSingleParamList.get(j).getRealSlotNo()).equals(String.valueOf(realSlotNo))){
                // 创建控制开关记录修改值集合
                List<ParamModifyRecord> commonParamModifyRecordList = new ArrayList<>();
                // 自适应开关
                if (null != controlSwitchJson.get("adaptiveSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("adaptiveSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getAdaptiveSwitch()),controlSwitchJson.getJSONObject("adaptiveSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAdaptiveSwitch(controlSwitchJson.getJSONObject("adaptiveSwitch").getLong("value"));
                }
                // 波动控制开关
                if (null != controlSwitchJson.get("noiseSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("noiseSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getNoiseSwitch()),controlSwitchJson.getJSONObject("noiseSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setNoiseSwitch(controlSwitchJson.getJSONObject("noiseSwitch").getLong("value"));
                }
                // 波动跟踪开关
                if (null != controlSwitchJson.get("noiseMonSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("noiseMonSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getNoiseMonSwitch()),controlSwitchJson.getJSONObject("noiseMonSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setNoiseMonSwitch(controlSwitchJson.getJSONObject("noiseMonSwitch").getLong("value"));
                }
                // AEB开关
                if (null != controlSwitchJson.get("aeCancelSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("aeCancelSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getAeCancelSwitch()),controlSwitchJson.getJSONObject("aeCancelSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAeCancelSwitch(controlSwitchJson.getJSONObject("aeCancelSwitch").getLong("value"));
                }
                // AE熄灭开关
                if (null != controlSwitchJson.get("aeQuenchSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("aeQuenchSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getAeQuenchSwitch()),controlSwitchJson.getJSONObject("aeQuenchSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAeQuenchSwitch(controlSwitchJson.getJSONObject("aeQuenchSwitch").getLong("value"));
                }
                // 氟盐开关
                if (null != controlSwitchJson.get("alf3Switch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("alf3Switch").getString("value"),String.valueOf(slotSingleParamList.get(j).getAlf3Switch()),controlSwitchJson.getJSONObject("alf3Switch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setAlf3Switch(controlSwitchJson.getJSONObject("alf3Switch").getLong("value"));
                }
                // 温度开关
                if (null != controlSwitchJson.get("temperatureSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("temperatureSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getTemperatureSwitch()),controlSwitchJson.getJSONObject("temperatureSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setTemperatureSwitch(controlSwitchJson.getJSONObject("temperatureSwitch").getLong("value"));
                }
                // RC脉冲复位开关
                if (null != controlSwitchJson.get("valveSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("valveSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getValveSwitch()),controlSwitchJson.getJSONObject("valveSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setValveSwitch(controlSwitchJson.getJSONObject("valveSwitch").getLong("value"));
                }
                // 联机开关
                if (null != controlSwitchJson.get("onOffLineSwitch")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("onOffLineSwitch").getString("value"),String.valueOf(slotSingleParamList.get(j).getOnOffLineSwitch()),controlSwitchJson.getJSONObject("onOffLineSwitch").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setOnOffLineSwitch(controlSwitchJson.getJSONObject("onOffLineSwitch").getLong("value"));
                }
                // 电流检修开关
                if (null != controlSwitchJson.get("spareSwitch1")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("spareSwitch1").getString("value"),String.valueOf(slotSingleParamList.get(j).getSpareSwitch1()),controlSwitchJson.getJSONObject("spareSwitch1").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setSpareSwitch1(controlSwitchJson.getJSONObject("spareSwitch1").getLong("value"));
                }
                // 停加料控制开关
                if (null != controlSwitchJson.get("spareSwitch2")){
                    // 记录修改值
                    commonParamModifyRecordList.add(getParamModifyRecord(realSlotNo,controlSwitchJson.getJSONObject("spareSwitch2").getString("value"),String.valueOf(slotSingleParamList.get(j).getSpareSwitch2()),controlSwitchJson.getJSONObject("spareSwitch2").getString("name")));
                    // 将新值替换到结构体中
                    slotSingleParamList.get(j).setSpareSwitch2(controlSwitchJson.getJSONObject("spareSwitch2").getLong("value"));
                }
                // 判断本条数据是否有改动
                if (commonParamModifyRecordList.size() > 0){
                    updateMark = true;
                }
                paramModifyRecordList.addAll(commonParamModifyRecordList);
            }
        }
        return updateMark;
    }

    /**
     * 整合 特殊过程参数 数据
     * @param updateMark 数据修改标记
     * @param realSlotNo 修改数据的槽号
     * @param operationProcessJson 修改了的特殊过程参数
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSerialParamList 系列参数
     * @return 数据是否有修改
     */
    private boolean adjustOperationProcess(boolean updateMark, Long realSlotNo,JSONObject operationProcessJson,List<ParamModifyRecord> paramModifyRecordList,List<SlotSerialParam> slotSerialParamList){
        for (int j = 0; j < slotSerialParamList.size(); j ++ ){
            if (String.valueOf(slotSerialParamList.get(j).getRealSlotNo()).equals(String.valueOf(realSlotNo))){
                // 创建特殊过程参数记录修改值集合
                List<ParamModifyRecord> operationProcessModifyRecordList = new ArrayList<>();
                // 出铝特殊加料时间min
                if (null != operationProcessJson.get("processMetalFeedTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processMetalFeedTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessMetalFeedTime()),operationProcessJson.getJSONObject("processMetalFeedTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessMetalFeedTime(operationProcessJson.getJSONObject("processMetalFeedTime").getLongValue("value"));
                }
                // 出铝附加电压mV
                if (null != operationProcessJson.get("processMetalAddVolt")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processMetalAddVolt").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessMetalAddVolt()),operationProcessJson.getJSONObject("processMetalAddVolt").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessMetalAddVolt(operationProcessJson.getJSONObject("processMetalAddVolt").getLongValue("value"));
                }
                // 出铝附加电压时间min
                if (null != operationProcessJson.get("processMetalAddVoltTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processMetalAddVoltTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessMetalAddVoltTime()),operationProcessJson.getJSONObject("processMetalAddVoltTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessMetalAddVoltTime(operationProcessJson.getJSONObject("processMetalAddVoltTime").getLongValue("value"));
                }
                // 换极特殊加料时间min
                if (null != operationProcessJson.get("processAcFeedTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processAcFeedTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessAcFeedTime()),operationProcessJson.getJSONObject("processAcFeedTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessAcFeedTime(operationProcessJson.getJSONObject("processAcFeedTime").getLongValue("value"));
                }
                // 换极附加电压mV
                if (null != operationProcessJson.get("processAcAddVolt")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processAcAddVolt").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessAcAddVolt()),operationProcessJson.getJSONObject("processAcAddVolt").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessAcAddVolt(operationProcessJson.getJSONObject("processAcAddVolt").getLongValue("value"));
                }
                // 换极附加电压时间min
                if (null != operationProcessJson.get("processAcAddVoltTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processAcAddVoltTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessAcAddVoltTime()),operationProcessJson.getJSONObject("processAcAddVoltTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessAcAddVoltTime(operationProcessJson.getJSONObject("processAcAddVoltTime").getLongValue("value"));
                }
                // 边加加料时间min
                if (null != operationProcessJson.get("addSideFeedTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("addSideFeedTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getAddSideFeedTime()),operationProcessJson.getJSONObject("addSideFeedTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAddSideFeedTime(operationProcessJson.getJSONObject("addSideFeedTime").getLongValue("value"));
                }
                // 边加附加电压mV
                if (null != operationProcessJson.get("addSideAddVolt")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("addSideAddVolt").getString("value"),String.valueOf(slotSerialParamList.get(j).getAddSideAddVolt()),operationProcessJson.getJSONObject("addSideAddVolt").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAddSideAddVolt(operationProcessJson.getJSONObject("addSideAddVolt").getLongValue("value"));
                }
                // 边加附加电压时间min
                if (null != operationProcessJson.get("addSideAddVoltTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("addSideAddVoltTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getAddSideAddVoltTime()),operationProcessJson.getJSONObject("addSideAddVoltTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAddSideAddVoltTime(operationProcessJson.getJSONObject("addSideAddVoltTime").getLongValue("value"));
                }
                // 抬母线特殊加料时间min
                if (null != operationProcessJson.get("processBrFeedTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("processBrFeedTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getProcessBrFeedTime()),operationProcessJson.getJSONObject("processBrFeedTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setProcessBrFeedTime(operationProcessJson.getJSONObject("processBrFeedTime").getLongValue("value"));
                }
                // 效应判定电压
                if (null != operationProcessJson.get("aeStartVolt0")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("aeStartVolt0").getString("value"),String.valueOf(slotSerialParamList.get(j).getAeStartVolt0()),operationProcessJson.getJSONObject("aeStartVolt0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAeStartVolt0(operationProcessJson.getJSONObject("aeStartVolt0").getLongValue("value"));
                }
                // 波动附加电压上限mV
                if (null != operationProcessJson.get("noiseAddMaxVolt")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("noiseAddMaxVolt").getString("value"),String.valueOf(slotSerialParamList.get(j).getNoiseAddMaxVolt()),operationProcessJson.getJSONObject("noiseAddMaxVolt").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setNoiseAddMaxVolt(operationProcessJson.getJSONObject("noiseAddMaxVolt").getLongValue("value"));
                }
                // 波动附加电压时间
                if (null != operationProcessJson.get("noiseAddVoltTime")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("noiseAddVoltTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getNoiseAddVoltTime()),operationProcessJson.getJSONObject("noiseAddVoltTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setNoiseAddVoltTime(operationProcessJson.getJSONObject("noiseAddVoltTime").getLongValue("value"));
                }
                // 波动判断值
                if (null != operationProcessJson.get("noiseHigh")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("noiseHigh").getString("value"),String.valueOf(slotSerialParamList.get(j).getNoiseHigh()),operationProcessJson.getJSONObject("noiseHigh").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setNoiseHigh(operationProcessJson.getJSONObject("noiseHigh").getLongValue("value"));
                }
                // 槽平稳判断值
                if (null != operationProcessJson.get("noiseLow")){
                    // 记录修改值
                    operationProcessModifyRecordList.add(getParamModifyRecord(realSlotNo,operationProcessJson.getJSONObject("noiseLow").getString("value"),String.valueOf(slotSerialParamList.get(j).getNoiseLow()),operationProcessJson.getJSONObject("noiseLow").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setNoiseLow(operationProcessJson.getJSONObject("noiseLow").getLongValue("value"));
                }
                // 判断本条数据是否有改动
                if (operationProcessModifyRecordList.size() > 0){
                    updateMark = true;
                }
                paramModifyRecordList.addAll(operationProcessModifyRecordList);
            }
        }
        return updateMark;
    }

    /**
     * 整合 执行机构参数 数据
     * @param updateMark 数据修改标记
     * @param realSlotNo 修改数据的槽号
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSerialParamList 系列参数
     * @param performActionJson 修改了的执行机构参数
     * @return 数据是否有修改
     */
    private boolean adjustPerformAction(boolean updateMark, Long realSlotNo,List<ParamModifyRecord> paramModifyRecordList,List<SlotSerialParam> slotSerialParamList,JSONObject performActionJson){
        for (int j = 0; j < slotSerialParamList.size(); j ++ ){
            if (String.valueOf(slotSerialParamList.get(j).getRealSlotNo()).equals(String.valueOf(realSlotNo))){
                // 创建执执行机构参数记录修改值集合
                List<ParamModifyRecord> performActionModifyRecordList = new ArrayList<>();
                // 打壳动作时间
                if (null != performActionJson.get("actionCrustTime1")){
                    // 记录修改值
                    performActionModifyRecordList.add(getParamModifyRecord(realSlotNo,performActionJson.getJSONObject("actionCrustTime1").getString("value"),String.valueOf(slotSerialParamList.get(j).getActionCrustTime1()),performActionJson.getJSONObject("actionCrustTime1").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setActionCrustTime1(performActionJson.getJSONObject("actionCrustTime1").getLongValue("value"));
                }
                // 打加间隔
                if (null != performActionJson.get("actionCrustTime2")){
                    // 记录修改值
                    performActionModifyRecordList.add(getParamModifyRecord(realSlotNo,performActionJson.getJSONObject("actionCrustTime2").getString("value"),String.valueOf(slotSerialParamList.get(j).getActionCrustTime2()),performActionJson.getJSONObject("actionCrustTime2").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setActionCrustTime2(performActionJson.getJSONObject("actionCrustTime2").getLongValue("value"));
                }
                // 加料动作时间
                if (null != performActionJson.get("actionAl2o3FeedTime")){
                    // 记录修改值
                    performActionModifyRecordList.add(getParamModifyRecord(realSlotNo,performActionJson.getJSONObject("actionAl2o3FeedTime").getString("value"),String.valueOf(slotSerialParamList.get(j).getActionAl2o3FeedTime()),performActionJson.getJSONObject("actionAl2o3FeedTime").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setActionAl2o3FeedTime(performActionJson.getJSONObject("actionAl2o3FeedTime").getLongValue("value"));
                }
                // 打加比
                if (null != performActionJson.get("shellFeedCount")){
                    // 记录修改值
                    performActionModifyRecordList.add(getParamModifyRecord(realSlotNo,performActionJson.getJSONObject("shellFeedCount").getString("value"),String.valueOf(slotSerialParamList.get(j).getShellFeedCount()),performActionJson.getJSONObject("shellFeedCount").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setShellFeedCount(performActionJson.getJSONObject("shellFeedCount").getLongValue("value"));
                }
                // 判断本条数据是否有改动
                if (performActionModifyRecordList.size() > 0){
                    updateMark = true;
                }
                paramModifyRecordList.addAll(performActionModifyRecordList);
            }
        }
        return updateMark;
    }

    /**
     * 整合 浓度设定 数据
     * @param updateMark 数据修改标记
     * @param realSlotNo 修改数据的槽号
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSerialParamList 系列参数
     * @param concentrationParamJson 修改了的浓度设定
     * @return 数据是否有修改
     */
    private boolean adjustConcentration(boolean updateMark, Long realSlotNo,List<ParamModifyRecord> paramModifyRecordList,List<SlotSerialParam> slotSerialParamList,JSONObject concentrationParamJson){
        for (int j = 0; j < slotSerialParamList.size(); j ++ ){
            if (String.valueOf(slotSerialParamList.get(j).getRealSlotNo()).equals(String.valueOf(realSlotNo))){
                // 创建浓度参数记录修改值集合
                List<ParamModifyRecord> concentrationModifyRecordList = new ArrayList<>();
                // 浓度系数
                if (null != concentrationParamJson.get("adptCycleCoef")){
                    // 记录修改值
                    concentrationModifyRecordList.add(getParamModifyRecord(realSlotNo,concentrationParamJson.getJSONObject("adptCycleCoef").getString("value"),String.valueOf(slotSerialParamList.get(j).getAdptCycleCoef()),concentrationParamJson.getJSONObject("adptCycleCoef").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAdptCycleCoef(concentrationParamJson.getJSONObject("adptCycleCoef").getLongValue("value"));
                }
                // 增量系数
                if (null != concentrationParamJson.get("adptOverCoef")){
                    // 记录修改值
                    concentrationModifyRecordList.add(getParamModifyRecord(realSlotNo,concentrationParamJson.getJSONObject("adptOverCoef").getString("value"),String.valueOf(slotSerialParamList.get(j).getAdptOverCoef()),concentrationParamJson.getJSONObject("adptOverCoef").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAdptOverCoef(concentrationParamJson.getJSONObject("adptOverCoef").getLongValue("value"));
                }
                // 减量系数
                if (null != concentrationParamJson.get("adptUnderCoef")){
                    // 记录修改值
                    concentrationModifyRecordList.add(getParamModifyRecord(realSlotNo,concentrationParamJson.getJSONObject("adptUnderCoef").getString("value"),String.valueOf(slotSerialParamList.get(j).getAdptUnderCoef()),concentrationParamJson.getJSONObject("adptUnderCoef").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAdptUnderCoef(concentrationParamJson.getJSONObject("adptUnderCoef").getLongValue("value"));
                }
                // 正常期
                if (null != concentrationParamJson.get("adptNormalDur")){
                    // 记录修改值
                    concentrationModifyRecordList.add(getParamModifyRecord(realSlotNo,concentrationParamJson.getJSONObject("adptNormalDur").getString("value"),String.valueOf(slotSerialParamList.get(j).getAdptNormalDur()),concentrationParamJson.getJSONObject("adptNormalDur").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAdptNormalDur(concentrationParamJson.getJSONObject("adptNormalDur").getLongValue("value"));
                }
                // 减量期
                if (null != concentrationParamJson.get("adptUnderDur")){
                    // 记录修改值
                    concentrationModifyRecordList.add(getParamModifyRecord(realSlotNo,concentrationParamJson.getJSONObject("adptUnderDur").getString("value"),String.valueOf(slotSerialParamList.get(j).getAdptUnderDur()),concentrationParamJson.getJSONObject("adptUnderDur").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAdptUnderDur(concentrationParamJson.getJSONObject("adptUnderDur").getLongValue("value"));
                }
                // 增量期
                if (null != concentrationParamJson.get("adptOverDur")){
                    // 记录修改值
                    concentrationModifyRecordList.add(getParamModifyRecord(realSlotNo,concentrationParamJson.getJSONObject("adptOverDur").getString("value"),String.valueOf(slotSerialParamList.get(j).getAdptOverDur()),concentrationParamJson.getJSONObject("adptOverDur").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setAdptOverDur(concentrationParamJson.getJSONObject("adptOverDur").getLongValue("value"));
                }

                // 判断本条数据是否有改动
                if (concentrationModifyRecordList.size() > 0){
                    updateMark = true;
                }
                paramModifyRecordList.addAll(concentrationModifyRecordList);
            }
        }
        return updateMark;
    }

    /**
     * 整合 设定范围 数据
     * @param updateMark 数据修改标记
     * @param realSlotNo 修改数据的槽号
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSerialParamList 系列参数
     * @param predeterminedAreaJson 修改了的设定范围
     * @return 数据是否有修改
     */
    private boolean adjustPredeterminedArea(boolean updateMark, Long realSlotNo,List<ParamModifyRecord> paramModifyRecordList,List<SlotSerialParam> slotSerialParamList,JSONObject predeterminedAreaJson){
        for (int j = 0; j < slotSerialParamList.size(); j ++ ){
            if (String.valueOf(slotSerialParamList.get(j).getRealSlotNo()).equals(String.valueOf(realSlotNo))){
                // 创建设定范围记录修改值集合
                List<ParamModifyRecord> predeterminedAreaModifyRecordList = new ArrayList<>();
                // 设定电流kA
                if (null != predeterminedAreaJson.get("plCurrent0")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("plCurrent0").getString("value"),String.valueOf(slotSerialParamList.get(j).getPlCurrent0() / 10),predeterminedAreaJson.getJSONObject("plCurrent0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setPlCurrent0(predeterminedAreaJson.getJSONObject("plCurrent0").getLongValue("value") * 10);
                }
                // 停NB电流kA
                if (null != predeterminedAreaJson.get("stopFeedCurrent0")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("stopFeedCurrent0").getString("value"),String.valueOf(slotSerialParamList.get(j).getStopFeedCurrent0() / 10),predeterminedAreaJson.getJSONObject("stopFeedCurrent0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setStopFeedCurrent0(predeterminedAreaJson.getJSONObject("stopFeedCurrent0").getLongValue("value") * 10);
                }
                // 设定电流上限kA
                if (null != predeterminedAreaJson.get("upCurrentRange0")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("upCurrentRange0").getString("value"),String.valueOf(slotSerialParamList.get(j).getUpCurrentRange0() / 10),predeterminedAreaJson.getJSONObject("upCurrentRange0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setUpCurrentRange0(predeterminedAreaJson.getJSONObject("upCurrentRange0").getLongValue("value") * 10);
                }
                // 设定电流下限kA
                if (null != predeterminedAreaJson.get("downCurrentRange0")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("downCurrentRange0").getString("value"),String.valueOf(slotSerialParamList.get(j).getDownCurrentRange0() / 10),predeterminedAreaJson.getJSONObject("downCurrentRange0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setDownCurrentRange0(predeterminedAreaJson.getJSONObject("downCurrentRange0").getLongValue("value") * 10);
                }
                // RC电压上限mV
                if (null != predeterminedAreaJson.get("upVoltageRange")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("upVoltageRange").getString("value"),String.valueOf(slotSerialParamList.get(j).getUpVoltageRange()),predeterminedAreaJson.getJSONObject("upVoltageRange").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setUpVoltageRange(predeterminedAreaJson.getJSONObject("upVoltageRange").getLongValue("value"));
                }
                // RC电压下限mV
                if (null != predeterminedAreaJson.get("downVoltageRange")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("downVoltageRange").getString("value"),String.valueOf(slotSerialParamList.get(j).getDownVoltageRange()),predeterminedAreaJson.getJSONObject("downVoltageRange").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setDownVoltageRange(predeterminedAreaJson.getJSONObject("downVoltageRange").getLongValue("value"));
                }
                // 大灵敏上限mV
                if (null != predeterminedAreaJson.get("upResistRange0")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("upResistRange0").getString("value"),String.valueOf(slotSerialParamList.get(j).getUpResistRange0()),predeterminedAreaJson.getJSONObject("upResistRange0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setUpResistRange0(predeterminedAreaJson.getJSONObject("upResistRange0").getLongValue("value"));
                }
                // 大灵敏下限mV
                if (null != predeterminedAreaJson.get("downResistRange0")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("downResistRange0").getString("value"),String.valueOf(slotSerialParamList.get(j).getDownResistRange0()),predeterminedAreaJson.getJSONObject("downResistRange0").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setDownResistRange0(predeterminedAreaJson.getJSONObject("downResistRange0").getLongValue("value"));
                }
                // 小灵敏上限mV
                if (null != predeterminedAreaJson.get("upResistRange1")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("upResistRange1").getString("value"),String.valueOf(slotSerialParamList.get(j).getUpResistRange1()),predeterminedAreaJson.getJSONObject("upResistRange1").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setUpResistRange1(predeterminedAreaJson.getJSONObject("upResistRange1").getLongValue("value"));
                }
                // 小灵敏下限mV
                if (null != predeterminedAreaJson.get("downResistRange1")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("downResistRange1").getString("value"),String.valueOf(slotSerialParamList.get(j).getDownResistRange1()),predeterminedAreaJson.getJSONObject("downResistRange1").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setDownResistRange1(predeterminedAreaJson.getJSONObject("downResistRange1").getLongValue("value"));
                }
                // 电压高报警值
                if (null != predeterminedAreaJson.get("highVoltageAlarm")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("highVoltageAlarm").getString("value"),String.valueOf(slotSerialParamList.get(j).getHighVoltageAlarm()),predeterminedAreaJson.getJSONObject("highVoltageAlarm").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setHighVoltageAlarm(predeterminedAreaJson.getJSONObject("highVoltageAlarm").getLongValue("value"));
                }
                // 电压低报警值
                if (null != predeterminedAreaJson.get("lowVoltageAlarm")){
                    // 记录修改值
                    predeterminedAreaModifyRecordList.add(getParamModifyRecord(realSlotNo,predeterminedAreaJson.getJSONObject("lowVoltageAlarm").getString("value"),String.valueOf(slotSerialParamList.get(j).getLowVoltageAlarm()),predeterminedAreaJson.getJSONObject("lowVoltageAlarm").getString("name")));
                    // 将新值替换到结构体中
                    slotSerialParamList.get(j).setLowVoltageAlarm(predeterminedAreaJson.getJSONObject("lowVoltageAlarm").getLongValue("value"));
                }
                // 判断本条数据是否有改动
                if (predeterminedAreaModifyRecordList.size() > 0){
                    updateMark = true;
                }
                paramModifyRecordList.addAll(predeterminedAreaModifyRecordList);
            }
        }
        return updateMark;
    }
    /**
     * 记录修改值
     * @param realSlotNo 槽号
     * @param newValue 新值
     * @param oldValue 旧值
     * @param paramStyle 修改字段
     * @return 记录修改值实体
     */
    private ParamModifyRecord getParamModifyRecord(Long realSlotNo, String newValue, String oldValue, String paramStyle){
        SysUser user = SecurityUtils.getLoginUser().getUser();
        ParamModifyRecord paramModifyRecord = new ParamModifyRecord();
        paramModifyRecord.setSlotId(Integer.parseInt(String.valueOf(realSlotNo)));
        paramModifyRecord.setNewValue(newValue);
        paramModifyRecord.setOriginalValue(oldValue);
        paramModifyRecord.setAuthor(user.getUserName());
        paramModifyRecord.setParamStyle(paramStyle);
        paramModifyRecord.setWriteDate(DateUtils.getTime());
        return paramModifyRecord;
    }
}
