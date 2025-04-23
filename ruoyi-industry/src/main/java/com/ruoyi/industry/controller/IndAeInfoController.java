package com.ruoyi.industry.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.industry.service.IIndAeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AE信息列表Controller
 *
 * @author shao
 * @date 2022-03-26
 */
@RestController
@RequestMapping("/industry/aeInfo")
public class IndAeInfoController extends BaseController {

    @Autowired
    private IIndAeInfoService indAeInfoService;

    /**
     * 获取AE信息列表
     */
    @PostMapping(value = "/list")
    public TableDataInfo getInfo(@RequestBody JSONObject paramJson)
    {
        int areaNo = paramJson.getIntValue("areaNo");
        List<Map<String,Object>> indAeInfoList = indAeInfoService.indAeInfoList(areaNo);
        return getDataTable(indAeInfoList);
    }
}
