package com.ruoyi.industry.mapper;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.industry.domain.Electrolyzer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealTimeDataDisplayMapper {

    List<Electrolyzer> electrolyzerList(@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    JSONArray connectStatus(@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    JSONArray getAeCnt(@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);
}
