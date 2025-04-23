package com.ruoyi.industry.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.industry.domain.ParamModifyRecord;
import com.ruoyi.industry.domain.SlotSerialParam;
import com.ruoyi.industry.domain.SlotSingleParam;
import org.apache.ibatis.annotations.Param;

import java.net.DatagramSocket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public interface SlotSingleParamService {

    JSONObject controlParameterObject(long potRoomId ,long sectionId );

    /**
     * 单槽参数
     */
    public List<SlotSingleParam> getSlotSingleParamList (@Param("realSlotNoList") List<Long> realSlotNoList);

    /**
     * 系列参数
     */
    public List<SlotSerialParam> getSlotSerialParamList(@Param("realSlotNoList") List<Long> realSlotNoList);

    /**
     * 整合变化数据
     * @param paramList 修改数据
     * @param paramModifyRecordList 记录修改值集合
     * @param slotSingleParamList 单槽参数
     * @param slotSerialParamList 系列参数
     * @param changeRealSlotNoList 数据变化的槽号集合
     */
    public Map<String,Integer> organizeChangeData(JSONArray paramList, List<ParamModifyRecord> paramModifyRecordList, List<SlotSingleParam> slotSingleParamList, List<SlotSerialParam> slotSerialParamList, JSONArray changeRealSlotNoList);

    /**
     * 开启多线程保存数据
     * @param changeRealSlotNoJson 修改槽号集合
     * @param slotSingleParamList 单槽参数数据
     * @param slotSerialParamList 系列参数数据
     * @param latch 线程标记
     * @param slotSingleCount 单槽参数计数器
     * @param slotSerialCount 系列参数计数器
     */
    public void threadSaveDate(int factoryNo, DatagramSocket socket, JSONObject changeRealSlotNoJson, List<SlotSingleParam> slotSingleParamList, List<SlotSerialParam> slotSerialParamList, CountDownLatch latch, AtomicLong slotSingleCount, AtomicLong slotSerialCount, String userName);

    /**
     * 保存单槽参数
     * @param slotSingleList 待修改单槽参数
     * @param slotSingleParamList 单槽参数集合
     * @param paramModifyRecordList 修改记录集合
     */
    public int saveSlotSingleParamList(List<String> slotSingleList, List<SlotSingleParam> slotSingleParamList, List<ParamModifyRecord> paramModifyRecordList);

    /**
     * 保存系列参数
     * @param slotSerialList 待修改系列参数
     * @param slotSerialParamList 系列参数集合
     * @param paramModifyRecordList 修改记录集合
     */
    public int saveSlotSerialParamList(List<String> slotSerialList, List<SlotSerialParam> slotSerialParamList, List<ParamModifyRecord> paramModifyRecordList);

}
