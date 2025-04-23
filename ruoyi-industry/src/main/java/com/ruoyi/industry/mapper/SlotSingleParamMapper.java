package com.ruoyi.industry.mapper;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.industry.domain.SlotSingleParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlotSingleParamMapper {

    /**
     * 常用参数
     */
    public JSONArray commonParamList (@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 控制开关
     */
    public JSONArray controlSwitchList (@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 单槽参数
     */
    public List<SlotSingleParam> getSlotSingleParamList (@Param("realSlotNoList") List<Long> realSlotNoList);

    /**
     * 保存单槽参数
     */
    public int insertSlotSingleParam(SlotSingleParam slotSingleParam);
}
