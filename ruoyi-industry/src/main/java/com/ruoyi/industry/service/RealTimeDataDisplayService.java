package com.ruoyi.industry.service;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.industry.domain.Electrolyzer;

import java.util.List;

public interface RealTimeDataDisplayService {

    List<Electrolyzer> electrolyzerList(long potRoomId, long sectionId);

    JSONArray connectStatus(long potRoomId, long sectionId);

    JSONArray getAeCnt(long potRoomId, long sectionId);

    JSONArray getVolt(long potRoomId, long sectionId);
}
