package com.ruoyi.industry.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.industry.domain.SlotOffLineCheckBox;

public interface SlotOffLineParamChartService {

    /**
     * 获取当前用户checkbox选择状态
     */
    public SlotOffLineCheckBox slotOffLineCheckBox();

    /**
     * 获取测量数据图表数据结构
     * @param jsonObject 查询条件
     */
    public JSONObject chartData(JSONObject jsonObject);
}
