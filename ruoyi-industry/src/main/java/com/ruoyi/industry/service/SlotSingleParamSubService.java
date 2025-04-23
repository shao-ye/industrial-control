package com.ruoyi.industry.service;

import com.ruoyi.industry.domain.ParamModifyRecord;
import com.ruoyi.industry.domain.SlotSerialParam;
import com.ruoyi.industry.domain.SlotSingleParam;

import java.net.DatagramSocket;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public interface SlotSingleParamSubService {

    /**
     * 单槽参数数据修改线程
     * @param realSlotNo 要保存的槽号
     * @param slotSingleParamList 单槽参数数据集
     * @param subLatch 线程标记
     * @param atomicLong 修改计数器
     */
    public void saveSlotSingleParam(int factoryNo, DatagramSocket socket, String realSlotNo, List<SlotSingleParam> slotSingleParamList, CountDownLatch subLatch, AtomicLong atomicLong, String userName);

    /**
     * 系列参数数据修改线程
     * @param realSlotNo 要保存的槽号
     * @param slotSerialParamList 系列参数数据集合
     * @param subLatch 线程标记
     * @param atomicLong 修改计数器
     */
    public void saveSlotSerialParam(int factoryNo, DatagramSocket socket, String realSlotNo, List<SlotSerialParam> slotSerialParamList, CountDownLatch subLatch, AtomicLong atomicLong, String userName);
}
