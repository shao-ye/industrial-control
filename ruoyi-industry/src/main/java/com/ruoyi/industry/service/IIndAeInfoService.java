package com.ruoyi.industry.service;

import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

public interface IIndAeInfoService {

    /**
     * AE信息列表
     * @param areaNo 区域号
     * @return AE信息列表
     */
    public List<Map<String,Object>> indAeInfoList(int areaNo);
}
