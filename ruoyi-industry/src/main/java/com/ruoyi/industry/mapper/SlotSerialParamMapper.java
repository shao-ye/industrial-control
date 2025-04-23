package com.ruoyi.industry.mapper;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.industry.domain.SlotSerialParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlotSerialParamMapper {

    /**
     * 设定范围
     */
    public JSONArray predeterminedAreaList (@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 操作过程函数
     */
    public JSONArray operationProcessList (@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 浓度参数
     */
    public JSONArray concentrationParamList (@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 执行动作参数
     */
    public JSONArray performActionParamList (@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 系列参数
     */
    public List<SlotSerialParam> getSlotSerialParamList(@Param("realSlotNoList") List<Long> realSlotNoList);

    /**
     * 保存系列参数
     */
    public int insertSlotSerialParam(SlotSerialParam slotSerialParam);
}
