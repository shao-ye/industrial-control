package com.ruoyi.industry.mapper;

import com.ruoyi.industry.domain.FactoryAreaParam;
import com.ruoyi.industry.domain.ParamModifyRecord;

import java.util.List;

public interface ParamModifyRecordMapper {

    /**
     * 修改参数记录查询
     */
    public List<ParamModifyRecord> paramModifyRecordList(FactoryAreaParam factoryAreaParam);

    /**
     * 保存修改参数记录
     */
    void insertParamModifyRecord(ParamModifyRecord paramModifyRecord);
}
