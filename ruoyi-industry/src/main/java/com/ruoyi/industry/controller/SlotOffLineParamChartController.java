package com.ruoyi.industry.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.industry.domain.SlotOffLineCheckBox;
import com.ruoyi.industry.service.SlotOffLineParamChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测量数据显示
 */
@RestController
@RequestMapping("/industry/SlotOffLineParamChart")
public class SlotOffLineParamChartController extends BaseController {

    @Autowired
    private SlotOffLineParamChartService slotOffLineParamChartService;

    /**
     * 获取当前用户checkbox选择状态
     */
    @PostMapping("/checkbox")
    public AjaxResult checkbox()
    {
        try {
            SlotOffLineCheckBox slotOffLineCheckBox = slotOffLineParamChartService.slotOffLineCheckBox();
            return AjaxResult.success(slotOffLineCheckBox);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("测量数据显示获取当前用户checkbox选择状态失败" + e.toString());
        }
    }

    @PostMapping("/chart")
    public AjaxResult list(@RequestBody JSONObject paramJson){
        try {
            JSONObject chartData = slotOffLineParamChartService.chartData(paramJson);
            return AjaxResult.success(chartData);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("测量数据显示图表数据查询失败！" + e.toString());
        }

    }
}
