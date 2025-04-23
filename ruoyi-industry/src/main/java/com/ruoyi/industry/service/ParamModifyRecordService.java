package com.ruoyi.industry.service;

import com.ruoyi.industry.domain.FactoryAreaParam;
import com.ruoyi.industry.domain.ParamModifyRecord;

import java.util.List;

public interface ParamModifyRecordService {

    /**
     * 修改参数记录查询
     */
    public List<ParamModifyRecord> paramModifyRecordList(FactoryAreaParam factoryAreaParam);
}
