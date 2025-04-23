package com.ruoyi.industry.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.industry.domain.Electrolyzer;
import com.ruoyi.industry.service.RealTimeDataDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时数据显示
 */
@RestController
@RequestMapping("/realtime/display")
public class RealTimeDataDisplayController extends BaseController {

    @Autowired
    RealTimeDataDisplayService realTimeDataDisplayService;

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody JSONObject queryParam)
    {
        long potRoomId = 0L;
        long sectionId = 0L;
        TableDataInfo dataInfo = new TableDataInfo();
        List<Electrolyzer> electrolyzerList = new ArrayList<>();
        if (null != queryParam.get("factoryNo") && null != queryParam.get("areaNo")){
            potRoomId = Long.parseLong(queryParam.get("factoryNo").toString());
            sectionId = Long.parseLong(queryParam.get("areaNo").toString());
            electrolyzerList.addAll(realTimeDataDisplayService.electrolyzerList(potRoomId,sectionId));
        }
        dataInfo.setRows(electrolyzerList);
        dataInfo.setTotal(electrolyzerList.size());
        return dataInfo;
    }

    /**
     * 连接状态，1：通信；2：无通信（对应行的整行颜色变灰色 状态列 显示 ： “无通信”，工作电压、实际NB、高频噪声、低频噪声四列数据变为空）
     * @param queryParam factoryAreaId 厂区id
     * @return 厂区下所有槽点连接状态
     */
    @PostMapping("/connectStatus")
    public AjaxResult connectStatus(@RequestBody JSONObject queryParam)
    {
        long potRoomId = 0L;
        long sectionId = 0L;
        try {
            if (null != queryParam.get("factoryNo") && null != queryParam.get("areaNo")){
                potRoomId = Long.parseLong(queryParam.get("factoryNo").toString());
                sectionId = Long.parseLong(queryParam.get("areaNo").toString());
                return AjaxResult.success(realTimeDataDisplayService.connectStatus(potRoomId,sectionId));
            } else {
                return AjaxResult.error("必填参数不可为空！");
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("实时数据显示获取连接状态失败！" + e.toString());
        }
    }

    /**
     * 24H AE数
     * @param queryParam factoryAreaId 厂区id
     * @return 厂区下所有槽点24H AE数
     */
    @PostMapping("/aeCnt")
    public AjaxResult getAeCnt(@RequestBody JSONObject queryParam)
    {
        long potRoomId = 0L;
        long sectionId = 0L;
        try {
            if (null != queryParam.get("factoryNo") && null != queryParam.get("areaNo")){
                potRoomId = Long.parseLong(queryParam.get("factoryNo").toString());
                sectionId = Long.parseLong(queryParam.get("areaNo").toString());
                return AjaxResult.success(realTimeDataDisplayService.getAeCnt(potRoomId,sectionId));
            } else {
                return AjaxResult.error("必填参数不可为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("实时数据显示获取24H AE数失败！" + e.toString());
        }
    }

    /**
     * 电压值
     * @param queryParam factoryNo:厂号,areaNo:区号
     * @return 电压值集合
     */
    @PostMapping("/volt")
    public AjaxResult getVolt(@RequestBody JSONObject queryParam)
    {
        long potRoomId = 0L;
        long sectionId = 0L;
        try {
            if (null != queryParam.get("factoryNo") && null != queryParam.get("areaNo")){
                potRoomId = Long.parseLong(queryParam.get("factoryNo").toString());
                sectionId = Long.parseLong(queryParam.get("areaNo").toString());
            } else {
                return AjaxResult.error("必填参数不可为空！");
            }
            return AjaxResult.success(realTimeDataDisplayService.getVolt(potRoomId,sectionId));
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("实时数据显示获取电压值失败！" + e.toString());
        }
    }
}
