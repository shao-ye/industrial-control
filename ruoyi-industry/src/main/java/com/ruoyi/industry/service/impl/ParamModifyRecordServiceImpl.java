package com.ruoyi.industry.service.impl;

import com.ruoyi.industry.domain.FactoryAreaParam;
import com.ruoyi.industry.domain.IndFactoryArea;
import com.ruoyi.industry.domain.ParamModifyRecord;
import com.ruoyi.industry.mapper.ParamModifyRecordMapper;
import com.ruoyi.industry.service.ParamModifyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamModifyRecordServiceImpl implements ParamModifyRecordService {

    @Autowired
    private ParamModifyRecordMapper paramModifyRecordMapper;

    @Override
    public List<ParamModifyRecord> paramModifyRecordList(FactoryAreaParam factoryAreaParam) {
        return paramModifyRecordMapper.paramModifyRecordList(factoryAreaParam);
    }
}
