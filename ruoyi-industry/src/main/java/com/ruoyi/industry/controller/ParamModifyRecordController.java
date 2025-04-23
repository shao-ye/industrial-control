package com.ruoyi.industry.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.industry.domain.FactoryAreaParam;
import com.ruoyi.industry.domain.ParamModifyRecord;
import com.ruoyi.industry.service.ParamModifyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/industry/paramModify")
public class ParamModifyRecordController extends BaseController {

    @Autowired
    private ParamModifyRecordService paramModifyRecordService;

    /**
     * 修改参数记录查询
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody FactoryAreaParam factoryAreaParam){
        if (null == factoryAreaParam){
            return getDataTable(new ArrayList<>());
        }
        startPage();
        List<ParamModifyRecord> paramModifyRecordList = paramModifyRecordService.paramModifyRecordList(factoryAreaParam);
        return getDataTable(paramModifyRecordList);

    }
}
