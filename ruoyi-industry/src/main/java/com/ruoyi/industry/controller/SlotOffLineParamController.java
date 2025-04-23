package com.ruoyi.industry.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.industry.domain.IndFactoryArea;
import com.ruoyi.industry.service.IIndFactoryAreaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.industry.domain.SlotOffLineParam;
import com.ruoyi.industry.service.ISlotOffLineParamService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测量数据Controller
 *
 * @author shao
 * @date 2022-03-22
 */
@RestController
@RequestMapping("/industry/SlotOffLineParam")
public class SlotOffLineParamController extends BaseController {

    @Autowired
    private ISlotOffLineParamService slotOffLineParamService;

    /**
     * 查询测量数据列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:SlotOffLineParam:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody JSONObject paramJson)
    {
        long factoryNo = 0L;
        long areaNo = 0L;
        List<SlotOffLineParam> list = new ArrayList<>();
        String signTime = null;
        if (null == paramJson.get("signTime") || null == paramJson.get("factoryNo") || null == paramJson.get("areaNo")){
            return getDataTable(list);
        } else {
            signTime = paramJson.getString("signTime");
            factoryNo = paramJson.getLongValue("factoryNo");
            areaNo = paramJson.getLongValue("areaNo");
            List<SlotOffLineParam> solpList = slotOffLineParamService.selectSlotOffLineParamList(signTime,areaNo);
            // 如果能查到数据则返回，查不到数据则新建数据，然后将数据返回
            if (solpList.size() > 0 ){
                list.addAll(slotOffLineParamService.selectSlotOffLineParamList(signTime,areaNo));
            } else {
                list.addAll(slotOffLineParamService.supplementaryData(signTime,factoryNo,areaNo));
            }
            return getDataTable(list);
        }
    }

    /**
     * 修改测量数据
     */
//    @PreAuthorize("@ss.hasPermi('industry:SlotOffLineParam:edit')")
    @Log(title = "测量数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody List<SlotOffLineParam> slotOffLineParamList)
    {
        try {
            slotOffLineParamService.updateSlotOffLineParam(slotOffLineParamList);
            return AjaxResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.toString());
        }
    }
}
