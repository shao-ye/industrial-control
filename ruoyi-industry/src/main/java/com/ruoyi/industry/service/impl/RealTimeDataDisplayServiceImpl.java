package com.ruoyi.industry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.service.ISocketUdpService;
import com.ruoyi.industry.domain.Electrolyzer;
import com.ruoyi.industry.mapper.IndFactoryAreaMapper;
import com.ruoyi.industry.mapper.RealTimeDataDisplayMapper;
import com.ruoyi.industry.service.RealTimeDataDisplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("realTimeDataDisplay")
public class RealTimeDataDisplayServiceImpl implements RealTimeDataDisplayService, ISocketUdpService {

    private static Logger logger = LoggerFactory.getLogger(RealTimeDataDisplayServiceImpl.class);

    @Autowired
    RealTimeDataDisplayMapper realTimeDataDisplayMapper;
    @Autowired
    IndFactoryAreaMapper factoryAreaMapper;

    /** 实时数据显示模块电压redis key*/
    private static final String realTimeDataDisplayVoltRedisKey = "real_time_data_display_volt:";

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Electrolyzer> electrolyzerList(long potRoomId, long sectionId) {
        return realTimeDataDisplayMapper.electrolyzerList(potRoomId, sectionId);
    }

    @Override
    public JSONArray connectStatus(long potRoomId, long sectionId) {
        return realTimeDataDisplayMapper.connectStatus(potRoomId, sectionId);
    }

    @Override
    public JSONArray getAeCnt(long potRoomId, long sectionId) {
        return realTimeDataDisplayMapper.getAeCnt(potRoomId, sectionId);
    }

    /**
     * 取电压值
     * @param potRoomId 厂号
     * @param sectionId 区号
     * @return 电压值集合
     */
    @Override
    public JSONArray getVolt(long potRoomId, long sectionId) {
        // 取槽号集合
        List<Long> realPotIdList = factoryAreaMapper.getRealPotId(potRoomId,sectionId);
        JSONArray refreshList = new JSONArray();
        for (int i = 0; i < realPotIdList.size(); i ++ ){
            long realPotId = realPotIdList.get(i);
            JSONObject voltObject = new JSONObject();
            voltObject.put("realSlotNo", realPotId);
            // 从redis中取对应槽号的电压值
            String voltJsonStr = redisCache.getCacheObject(realTimeDataDisplayVoltRedisKey+ realPotId);
            if (StringUtils.isNotBlank(voltJsonStr)){
                JSONObject voltJson = JSONObject.parseObject(voltJsonStr);
                voltObject.put("workVolt",voltJson.getLongValue("Volt"));
            }else {
                voltObject.put("workVolt",null);
            }
            refreshList.add(voltObject);
        }
        return refreshList;
    }

    /**
     * 处理socket信息
     * @param udpMsg socket信息
     */
    @Override
    public void udpMsgHandle(String udpMsg) {
//        logger.info("业务类开始处理socket信息：" + udpMsg);
        // 处理socket信息
        if (StringUtils.isNotBlank(udpMsg)){
            JSONArray jsonArray = JSONObject.parseObject(udpMsg).getJSONArray("RawData");
            if (null != jsonArray && jsonArray.size() > 0){
                for (int i = 0; i < jsonArray.size(); i ++ ){
                    String potId = jsonArray.getJSONObject(i).getString("PotID");
                    JSONObject voltJson = jsonArray.getJSONObject(i).getJSONObject("Data");
                    // 加个时间戳
                    voltJson.put("date",new Date().getTime());
                    // 存入redis
                    redisCache.setCacheObject(realTimeDataDisplayVoltRedisKey + potId, voltJson.toJSONString());
                }
            }
        }

    }
}
