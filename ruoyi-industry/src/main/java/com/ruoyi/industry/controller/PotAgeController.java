package com.ruoyi.industry.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.industry.domain.PotAge;
import com.ruoyi.industry.service.IPotAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电解槽槽龄Controller
 *
 * @author shao
 * @date 2022-04-07
 */
@RestController
@RequestMapping("/industry/potAge")
public class PotAgeController extends BaseController {

    @Autowired
    private IPotAgeService potAgeService;

    /**
     * 查询电解槽槽龄列表
     */
//    @PreAuthorize("@ss.hasPermi('industry:PotAge:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject paramJson)
    {
        long potRoomId = 0L;
        long sectionId = 0L;
        try {
            if (null != paramJson.get("factoryNo") && null != paramJson.get("areaNo")){
                potRoomId = Long.parseLong(paramJson.get("factoryNo").toString());
                sectionId = Long.parseLong(paramJson.get("areaNo").toString());
                return AjaxResult.success(potAgeService.selectPotAgeList(potRoomId,sectionId));
            } else {
                return AjaxResult.error("必填参数不可为空！");
            }
        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("电解槽槽龄列表查询失败！" + e.toString());
        }
    }

    /**
     * 修改电解槽槽龄
     */
//    @PreAuthorize("@ss.hasPermi('industry:PotAge:edit')")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody List<PotAge> potAgeList) {
        try {
            // 校验数据
            String check = checkParam(potAgeList);
            if (StringUtils.isNotBlank(check)){
                return AjaxResult.error(check);
            }
            potAgeService.updatePotAgeList(potAgeList);
            return AjaxResult.success();
        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("修改电解槽槽龄失败！" + e.toString());
        }
    }

    /**
     * 校验
     */
    private String checkParam(List<PotAge> potAgeList){
        for (int i = 0; i < potAgeList.size(); i ++ ){
            PotAge potAge = potAgeList.get(i);
            // 当前槽代
            Long potCurrentGeneration = potAge.getPotCurrentGeneration();
            if (null == potCurrentGeneration){
                return "槽号：" + potAge.getRealPotId() + "，当前槽代不可为空！";
            }
            if (null == potAge.getCurrentGenStartTime()){
                return "槽号：" + potAge.getRealPotId() + "，当前起槽时间不可为空！";
            }
            if(1L <= potCurrentGeneration){
                if (null == potAge.getGenStartTime1()){
                    return "槽号：" + potAge.getRealPotId() + "，1代开始时间不可为空！";
                }
            }
            if(2L <= potCurrentGeneration){
                if (null == potAge.getGenEndTime1()){
                    return "槽号：" + potAge.getRealPotId() + "，1代结束时间不可为空！";
                }
                if (null == potAge.getGenStartTime2()){
                    return "槽号：" + potAge.getRealPotId() + "，2代开始时间不可为空！";
                }
                int daysBetween = DateUtils.daysBetween(DateUtils.parseDate(potAge.getGenEndTime1()),DateUtils.parseDate(potAge.getGenStartTime2()));
                if ( 0 > daysBetween){
                    return "槽号：" + potAge.getRealPotId() + "，2代开始时间不可小于1代结束时间！";
                }
            }
            if(3L <= potCurrentGeneration){
                if (null == potAge.getGenEndTime2()){
                    return "槽号：" + potAge.getRealPotId() + "，2代结束时间不可为空！";
                }
                if (null == potAge.getGenStartTime3()){
                    return "槽号：" + potAge.getRealPotId() + "，3代开始时间不可为空！";
                }
                int daysBetween = DateUtils.daysBetween(DateUtils.parseDate(potAge.getGenEndTime2()),DateUtils.parseDate(potAge.getGenStartTime3()));
                if ( 0 > daysBetween){
                    return "槽号：" + potAge.getRealPotId() + "，3代开始时间不可小于2代结束时间！";
                }
            }
            if(4L <= potCurrentGeneration){
                if (null == potAge.getGenEndTime3()){
                    return "槽号：" + potAge.getRealPotId() + "，3代结束时间不可为空！";
                }
                if (null == potAge.getGenStartTime4()){
                    return "槽号：" + potAge.getRealPotId() + "，4代开始时间不可为空！";
                }
                int daysBetween = DateUtils.daysBetween(DateUtils.parseDate(potAge.getGenEndTime3()),DateUtils.parseDate(potAge.getGenStartTime4()));
                if ( 0 > daysBetween){
                    return "槽号：" + potAge.getRealPotId() + "，4代开始时间不可小于3代结束时间！";
                }
            }
            if(5L <= potCurrentGeneration){
                if (null == potAge.getGenEndTime4()){
                    return "槽号：" + potAge.getRealPotId() + "，4代结束时间不可为空！";
                }
                if (null == potAge.getGenStartTime5()){
                    return "槽号：" + potAge.getRealPotId() + "，5代开始时间不可为空！";
                }
                int daysBetween = DateUtils.daysBetween(DateUtils.parseDate(potAge.getGenEndTime4()),DateUtils.parseDate(potAge.getGenStartTime5()));
                if ( 0 > daysBetween){
                    return "槽号：" + potAge.getRealPotId() + "，5代开始时间不可小于4代结束时间！";
                }
            }
        }
        return null;
    }
}
