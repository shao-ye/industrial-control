package com.ruoyi.industry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.industry.domain.SlotOffLineCheckBox;
import com.ruoyi.industry.mapper.SlotOffLineParamChartMapper;
import com.ruoyi.industry.service.SlotOffLineParamChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SlotOffLineParamChartServiceImpl implements SlotOffLineParamChartService {

    @Autowired
    private SlotOffLineParamChartMapper slotOffLineParamChartMapper;

    /**
     * 获取当前用户checkbox选择状态
     */
    @Override
    public SlotOffLineCheckBox slotOffLineCheckBox() {
        // 获取当前用户id
        SysUser user = SecurityUtils.getLoginUser().getUser();
        long userId = user.getUserId();
        // 查询用户历史查询条件
        SlotOffLineCheckBox slotOffLineCheckBox = slotOffLineParamChartMapper.selectIndSlot0ffLineCheckboxByUserId(userId);
        // 如果没有，就插入数据库中一条默认查询条件数据
        if (null == slotOffLineCheckBox){
            SlotOffLineCheckBox newSlotOffLineCheckBox = new SlotOffLineCheckBox();
            newSlotOffLineCheckBox.setUserId(userId);
            slotOffLineParamChartMapper.insertIndSlot0ffLineCheckbox(newSlotOffLineCheckBox);
            slotOffLineCheckBox = slotOffLineParamChartMapper.selectIndSlot0ffLineCheckboxByUserId(userId);
        }
        return slotOffLineCheckBox;
    }

    /**
     * 获取测量数据图表数据结构
     * @param jsonObject 查询条件
     */
    @Override
    public JSONObject chartData(JSONObject jsonObject) {
        SlotOffLineCheckBox slotOffLineCheckBox = new SlotOffLineCheckBox();
        String startDate = jsonObject.getString("startDate");
        String end = jsonObject.getString("endDate");
        // 槽点
        int realPotId = jsonObject.getIntValue("realPotId");
        // 得到每日日期
        List<String> dateList = DateUtils.getDatesBetweenTwoDate(startDate,end);
        JSONArray yList = new JSONArray();
        JSONArray xList = new JSONArray();
        xList.addAll(dateList);
        // 设定电压
        int tartgetVoltage = jsonObject.getIntValue("tartgetVoltage");
        slotOffLineCheckBox.setTartgetVoltage(tartgetVoltage);
        // 工作电压
        int workingVoltage = jsonObject.getIntValue("workingVoltage");
        slotOffLineCheckBox.setWorkingVoltage(workingVoltage);
        // 平均电压
        int averageVoltage = jsonObject.getIntValue("averageVoltage");
        slotOffLineCheckBox.setAverageVoltage(averageVoltage);
        // 高频噪声
        int instHighNoiseValue = jsonObject.getIntValue("instHighNoiseValue");
        slotOffLineCheckBox.setInstHighNoiseValue(instHighNoiseValue);
        // 低频噪声
        int instLowNoiseValue = jsonObject.getIntValue("instLowNoiseValue");
        slotOffLineCheckBox.setInstLowNoiseValue(instLowNoiseValue);
        // 实际氟盐量
        int actualFluorideSalt = jsonObject.getIntValue("actualFluorideSalt");
        slotOffLineCheckBox.setActualFluorideSalt(actualFluorideSalt);
        // AE次数
        int dayReport = jsonObject.getIntValue("dayReport");
        if (0 == tartgetVoltage || 0 == workingVoltage || 0 == averageVoltage || 0 == instHighNoiseValue || 0 == instLowNoiseValue || 0 == actualFluorideSalt){
            List<Map<String,String>> dayReportSlotOffLineList = slotOffLineParamChartMapper.dayReportSlotOffLine(startDate,end,realPotId);

            JSONObject tartgetVoltageObject = null;
            JSONArray tartgetVoltageArray = null;
            if (0 == tartgetVoltage ){
                tartgetVoltageObject = new JSONObject();
                tartgetVoltageObject.put("name","设定电压");
                tartgetVoltageArray = initYList(dateList.size());
            }

            JSONObject workingVoltageObject = null;
            JSONArray workingVoltageArray = null;
            if (0 == workingVoltage ){
                workingVoltageObject = new JSONObject();
                workingVoltageObject.put("name","工作电压");
                workingVoltageArray = initYList(dateList.size());
            }

            JSONObject averageVoltageObject = null;
            JSONArray averageVoltageArray = null;
            if (0 == averageVoltage ){
                averageVoltageObject = new JSONObject();
                averageVoltageObject.put("name","平均电压");
                averageVoltageArray = initYList(dateList.size());
            }

            JSONObject instHighNoiseValueObject = null;
            JSONArray instHighNoiseValueArray = null;
            if (0 == instHighNoiseValue ){
                instHighNoiseValueObject = new JSONObject();
                instHighNoiseValueObject.put("name","高频噪声");
                instHighNoiseValueArray = initYList(dateList.size());
            }

            JSONObject instLowNoiseValueObject = null;
            JSONArray instLowNoiseValueArray = null;
            if (0 == instLowNoiseValue ){
                instLowNoiseValueObject = new JSONObject();
                instLowNoiseValueObject.put("name","低频噪声");
                instLowNoiseValueArray = initYList(dateList.size());
            }

            JSONObject actualFluorideSaltObject = null;
            JSONArray actualFluorideSaltArray = null;
            if (0 == actualFluorideSalt ){
                actualFluorideSaltObject = new JSONObject();
                actualFluorideSaltObject.put("name","实际氟盐量");
                actualFluorideSaltArray = initYList(dateList.size());
            }

            JSONObject dayReportObject = null;
            JSONArray dayReportArray = null;
            if (0 == dayReport ){
                dayReportObject = new JSONObject();
                dayReportObject.put("name","AE次数");
                dayReportArray = initYList(dateList.size());
            }

            for (int i = 0; i < dateList.size(); i ++ ){
                for (int j = 0; j < dayReportSlotOffLineList.size(); j ++ ){
                    if (dateList.get(i).equals(dayReportSlotOffLineList.get(j).get("date"))){
                        // 设定电压
                        if (0 == tartgetVoltage ){
                            tartgetVoltageArray.set(i,dayReportSlotOffLineList.get(j).get("tartgetVoltage"));
                        }
                        // 工作电压
                        if (0 == workingVoltage ){
                            workingVoltageArray.set(i,dayReportSlotOffLineList.get(j).get("workingVoltage"));
                        }
                        // 平均电压
                        if (0 == averageVoltage ){
                            averageVoltageArray.set(i,dayReportSlotOffLineList.get(j).get("averageVoltage"));
                        }
                        // 高频噪声
                        if (0 == instHighNoiseValue ){
                            instHighNoiseValueArray.set(i,dayReportSlotOffLineList.get(j).get("instHighNoiseValue"));
                        }
                        // 低频噪声
                        if (0 == instLowNoiseValue ){
                            instLowNoiseValueArray.set(i,dayReportSlotOffLineList.get(j).get("instLowNoiseValue"));
                        }
                        // 实际氟盐量
                        if (0 == actualFluorideSalt ){
                            actualFluorideSaltArray.set(i,dayReportSlotOffLineList.get(j).get("actualFluorideSalt"));
                        }
                        // AE次数
                        if (0 == dayReport ){
                            dayReportArray.set(i,dayReportSlotOffLineList.get(j).get("dayReport"));
                        }
                    }

                }
            }
            // 设定电压
            if (0 == tartgetVoltage ){
                tartgetVoltageObject.put("data",tartgetVoltageArray);
                yList.add(tartgetVoltageObject);
            }
            // 工作电压
            if (0 == workingVoltage ){
                workingVoltageObject.put("data",workingVoltageArray);
                yList.add(workingVoltageObject);
            }
            // 平均电压
            if (0 == averageVoltage ){
                averageVoltageObject.put("data",averageVoltageArray);
                yList.add(averageVoltageObject);
            }
            // 高频噪声
            if (0 == instHighNoiseValue ){
                instHighNoiseValueObject.put("data",instHighNoiseValueArray);
                yList.add(instHighNoiseValueObject);
            }
            // 低频噪声
            if (0 == instLowNoiseValue ){
                instLowNoiseValueObject.put("data",instLowNoiseValueArray);
                yList.add(instLowNoiseValueObject);
            }
            // 实际氟盐量
            if (0 == actualFluorideSalt ){
                actualFluorideSaltObject.put("data",actualFluorideSaltArray);
                yList.add(actualFluorideSaltObject);
            }
            // AE次数
            if (0 == dayReport ){
                dayReportObject.put("data",dayReportArray);
                yList.add(dayReportObject);
            }
        }

        // 出铝指示量
        int metalMass = jsonObject.getIntValue("metalMass");
        slotOffLineCheckBox.setMetalMass(metalMass);
        // 铝水高度
        int metalHeight = jsonObject.getIntValue("metalHeight");
        slotOffLineCheckBox.setMetalHeight(metalHeight);
        // 电解质高度
        int bathHeight = jsonObject.getIntValue("bathHeight");
        slotOffLineCheckBox.setBathHeight(bathHeight);
        // 槽温/电解质温度
        int bathTemperature = jsonObject.getIntValue("bathTemperature");
        slotOffLineCheckBox.setBathTemperature(bathTemperature);
        // 分子比
        int bathRatio = jsonObject.getIntValue("bathRatio");
        slotOffLineCheckBox.setBathRatio(bathRatio);
        // 铁含量
        int feContent = jsonObject.getIntValue("feContent");
        slotOffLineCheckBox.setFeContent(feContent);
        // 硅含量
        int siContent = jsonObject.getIntValue("siContent");
        slotOffLineCheckBox.setSiContent(siContent);
        // 质量
        int metalQuality = jsonObject.getIntValue("metalQuality");
        slotOffLineCheckBox.setMetalQuality(metalQuality);
        // 浓度
        int al2O3Concen = jsonObject.getIntValue("al2O3Concen");
        slotOffLineCheckBox.setAl2O3Concen(al2O3Concen);
        // 炉底压降
        int cvd = jsonObject.getIntValue("cvd");
        slotOffLineCheckBox.setCvd(cvd);
        // 极距
        int acd = jsonObject.getIntValue("acd");
        slotOffLineCheckBox.setAcd(acd);
        // 实际出铝量
        int realMetalMass = jsonObject.getIntValue("realMetalMass");
        slotOffLineCheckBox.setRealMetalMass(realMetalMass);
        if (0 == dayReport || 0 == metalMass || 0 == metalHeight || 0 == bathHeight || 0 == bathTemperature
                || 0 == bathRatio || 0 == feContent || 0 == siContent || 0 == metalQuality || 0 == al2O3Concen
                || 0 == cvd || 0 == acd || 0 == realMetalMass){
            List<Map<String,String>> slotOffLineList = slotOffLineParamChartMapper.slotOffLine(startDate,end,realPotId);

            JSONObject metalMassObject = null;
            JSONArray metalMassArray = null;
            if (0 == metalMass ){
                metalMassObject = new JSONObject();
                metalMassObject.put("name","出铝指示量");
                metalMassArray = initYList(dateList.size());
            }
            // 铝水高度
            JSONObject metalHeightObject = null;
            JSONArray metalHeightArray = null;
            if (0 == metalHeight ){
                metalHeightObject = new JSONObject();
                metalHeightObject.put("name","铝水平");
                metalHeightArray = initYList(dateList.size());
            }
            // 电解质高度
            JSONObject bathHeightObject = null;
            JSONArray bathHeightArray = null;
            if (0 == bathHeight ){
                bathHeightObject = new JSONObject();
                bathHeightObject.put("name","电解质水平");
                bathHeightArray = initYList(dateList.size());
            }
            // 槽温/电解质温度
            JSONObject bathTemperatureObject = null;
            JSONArray bathTemperatureArray = null;
            if (0 == bathTemperature ){
                bathTemperatureObject = new JSONObject();
                bathTemperatureObject.put("name","电解温度");
                bathTemperatureArray = initYList(dateList.size());
            }

            JSONObject bathRatioObject = null;
            JSONArray bathRatioArray = null;
            if (0 == bathRatio ){
                bathRatioObject = new JSONObject();
                bathRatioObject.put("name","分子比");
                bathRatioArray = initYList(dateList.size());
            }
            // 铁含量
            JSONObject feContentObject = null;
            JSONArray feContentArray = null;
            if (0 == feContent ){
                feContentObject = new JSONObject();
                feContentObject.put("name","Fe含量");
                feContentArray = initYList(dateList.size());
            }
            // 硅含量
            JSONObject siContentObject = null;
            JSONArray siContentArray = null;
            if (0 == siContent ){
                siContentObject = new JSONObject();
                siContentObject.put("name","Si含量");
                siContentArray = initYList(dateList.size());
            }
            // 浓度
            JSONObject al2O3ConcenObject = null;
            JSONArray al2O3ConcenArray = null;
            if (0 == al2O3Concen ){
                al2O3ConcenObject = new JSONObject();
                al2O3ConcenObject.put("name","Al2O3量");
                al2O3ConcenArray = initYList(dateList.size());
            }

            JSONObject cvdObject = null;
            JSONArray cvdArray = null;
            if (0 == cvd ){
                cvdObject = new JSONObject();
                cvdObject.put("name","炉底压降");
                cvdArray = initYList(dateList.size());
            }

            JSONObject acdObject = null;
            JSONArray acdArray = null;
            if (0 == acd ){
                acdObject = new JSONObject();
                acdObject.put("name","极距");
                acdArray = initYList(dateList.size());
            }

            JSONObject realMetalMassObject = null;
            JSONArray realMetalMassArray = null;
            if (0 == realMetalMass ){
                realMetalMassObject = new JSONObject();
                realMetalMassObject.put("name","实际出铝量");
                realMetalMassArray = initYList(dateList.size());
            }
            for (int i = 0; i < dateList.size(); i ++ ) {
                for (int j = 0; j < slotOffLineList.size(); j++) {
                    if (dateList.get(i).equals(slotOffLineList.get(j).get("date"))){
                        // 出铝指示量
                        if (0 == metalMass ){
                            metalMassArray.set(i,slotOffLineList.get(j).get("metalMass"));
                        }
                        // 铝水平
                        if (0 == metalHeight ){
                            metalHeightArray.set(i,slotOffLineList.get(j).get("metalHeight"));
                        }
                        // 电解质水平
                        if (0 == bathHeight ){
                            bathHeightArray.set(i,slotOffLineList.get(j).get("bathHeight"));
                        }
                        // 电解温度
                        if (0 == bathTemperature ){
                            bathTemperatureArray.set(i,slotOffLineList.get(j).get("bathTemperature"));
                        }
                        // 分子比
                        if (0 == bathRatio ){
                            bathRatioArray.set(i,slotOffLineList.get(j).get("bathRatio"));
                        }
                        // Fe含量
                        if (0 == feContent ){
                            feContentArray.set(i,slotOffLineList.get(j).get("feContent"));
                        }
                        // Si含量
                        if (0 == siContent ){
                            siContentArray.set(i,slotOffLineList.get(j).get("siContent"));
                        }
                        // Al2O3量
                        if (0 == al2O3Concen ){
                            al2O3ConcenArray.set(i,slotOffLineList.get(j).get("al2O3Concen"));
                        }
                        // 炉底压降
                        if (0 == cvd ){
                            cvdArray.set(i,slotOffLineList.get(j).get("cvd"));
                        }
                        // 极距
                        if (0 == acd ){
                            acdArray.set(i,slotOffLineList.get(j).get("acd"));
                        }
                        // 实际出铝量
                        if (0 == realMetalMass ){
                            realMetalMassArray.set(i,slotOffLineList.get(j).get("realMetalMass"));
                        }
                    }
                }
            }
            // 出铝指示量
            if (0 == metalMass ){
                metalMassObject.put("data",metalMassArray);
                yList.add(metalMassObject);
            }
            // 铝水平
            if (0 == metalHeight ){
                metalHeightObject.put("data",metalHeightArray);
                yList.add(metalHeightObject);
            }
            // 电解质水平
            if (0 == bathHeight ){
                bathHeightObject.put("data",bathHeightArray);
                yList.add(bathHeightObject);
            }
            // 电解温度
            if (0 == bathTemperature ){
                bathTemperatureObject.put("data",bathTemperatureArray);
                yList.add(bathTemperatureObject);
            }
            // 分子比
            if (0 == bathRatio ){
                bathRatioObject.put("data",bathHeightArray);
                yList.add(bathRatioObject);
            }
            // Fe含量
            if (0 == feContent ){
                feContentObject.put("data",feContentArray);
                yList.add(feContentObject);
            }
            // Si含量
            if (0 == siContent ){
                siContentObject.put("data",siContentArray);
                yList.add(siContentObject);
            }
            // Al2O3量
            if (0 == al2O3Concen ){
                al2O3ConcenObject.put("data",al2O3ConcenArray);
                yList.add(al2O3ConcenObject);
            }
            // 炉底压降
            if (0 == cvd ){
                cvdObject.put("data",cvdArray);
                yList.add(cvdObject);
            }
            // 极距
            if (0 == acd ){
                acdObject.put("data",acdArray);
                yList.add(acdObject);
            }
            // 实际出铝量
            if (0 == realMetalMass ){
                realMetalMassObject.put("data",realMetalMassArray);
                yList.add(realMetalMassObject);
            }
        }
        JSONObject chartObject = new JSONObject();
        chartObject.put("yList",yList);
        chartObject.put("xList",xList);
        // 获取当前用户id
        SysUser user = SecurityUtils.getLoginUser().getUser();
        slotOffLineCheckBox.setUserId(user.getUserId());
        // 保存当前用户选择状态
        slotOffLineParamChartMapper.updateIndSlot0ffLineCheckboxByUserId(slotOffLineCheckBox);
        return chartObject;
    }

    /**
     * 初始化一个Y轴集合
     */
    private JSONArray initYList(int count){
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < count; i ++ ){
            jsonArray.add(0);
        }
        return jsonArray;
    }
}
