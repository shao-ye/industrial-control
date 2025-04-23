package com.ruoyi.industry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.industry.domain.IndAeInfo;
import com.ruoyi.industry.mapper.IndAeInfoMapper;
import com.ruoyi.industry.service.IIndAeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndAeInfoServiceImpl implements IIndAeInfoService {

    @Autowired
    private IndAeInfoMapper indAeInfoMapper;

    @Override
    public List<Map<String,Object>>  indAeInfoList(int areaNo) {
        List<IndAeInfo> indAeInfoList = indAeInfoMapper.indAeInfoList(areaNo);
        List<Map<String,Object>> list = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < indAeInfoList.size(); i ++ ){
            if (1 == indAeInfoList.get(i).getNum()){
                Map<String,Object> map = new HashMap<>();
                map.put("realSlotNo",indAeInfoList.get(i).getRealSlotNo());
                map.put("aeStartTime" + counter,indAeInfoList.get(i).getAeStartTime());
                map.put("aeAverageVoltage" + counter,indAeInfoList.get(i).getAeAverageVoltage());
                map.put("aePeakVoltage" + counter,indAeInfoList.get(i).getAePeakVoltage());
                map.put("aeLastingTime" + counter,indAeInfoList.get(i).getAeLastingTime());
                map.put("aeType" + counter,indAeInfoList.get(i).getAeType());
                list.add(map);
            } else {
                for (int n = 0; n < list.size(); n ++ ){
                    Map<String,Object> map = list.get(n);
                    if (indAeInfoList.get(i).getRealSlotNo() ==  Integer.parseInt(map.get("realSlotNo").toString())){
                        list.get(n).put("aeStartTime" + counter,indAeInfoList.get(i).getAeStartTime());
                        list.get(n).put("aeAverageVoltage" + counter,indAeInfoList.get(i).getAeAverageVoltage());
                        list.get(n).put("aePeakVoltage" + counter,indAeInfoList.get(i).getAePeakVoltage());
                        list.get(n).put("aeLastingTime" + counter,indAeInfoList.get(i).getAeLastingTime());
                        list.get(n).put("aeType" + counter,indAeInfoList.get(i).getAeType());
                    }
                }
            }
            if (5 == counter){
                counter = 1;
            } else {
                counter ++ ;
            }
        }
        return list;
    }
}
