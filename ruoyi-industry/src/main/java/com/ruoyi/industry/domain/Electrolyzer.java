package com.ruoyi.industry.domain;

import java.util.Date;

/**
 * 电解槽
 */
public class Electrolyzer {

    /** 槽号 */
    private int realSlotNo;
    /** 手/自动code(1:半自动；2：手动；3：自动) */
    private int modleCode;
    /** 手/自动name */
    private String modleName;
    /** 状态code(1:波动;2:禁止RC) */
    private int statusCode;
    /** 状态name */
    private String statusName;
    /** 动作code(1:RC升;2:RC降;4:加料1;8:加料2) */
    private int operateCode;
    /** 动作name */
    private String operateName;
    /** 设定电压 */
    private int volt;
    /** 设定氟盐 */
    private int aif3mass;
    /** 目标电压 */
    private int targetVolt;
    /** 工作电压 */
    private int workVolt;
    /** 设定NB */
    private int setNb;
    /** 实际NB */
    private int actualNb;
    /** 加料周期code */
    private int feedingCycleCode;
    /** 加料周期name(1:正常期;2:减量期;3:增量期) */
    private String feedingCycleName;
    /** 实时电阻 */
    private int resistance;
    /** 今日阳极开始位置 */
    private int startAnodePosition;
    /** 当前阳极位置 */
    private int currentAnodePosition;
    /** 当前故障 */
    private int currentFault;
    /** 高频噪声 */
    private int highNoise;
    /** 低频噪声 */
    private int lowNoise;
    /** 最近AE时刻 */
    private String aeStartTime;
    /** AE均压 */
    private int aeAverageVoltage;
    /** AE持续时间 */
    private int aeLastingTime;
    /** AE类型 */
    private String aeType;
    /** 24H AE数 */
    private int aeCnt;
    /** 连接状态 0：通信；1：无通信*/
    private int connectStatus;

    public int getRealSlotNo() {
        return realSlotNo;
    }

    public void setRealSlotNo(int realSlotNo) {
        this.realSlotNo = realSlotNo;
    }

    public int getModleCode() {
        return modleCode;
    }

    public void setModleCode(int modleCode) {
        this.modleCode = modleCode;
    }

    public String getModleName() {
        return modleName;
    }

    public void setModleName(String modleName) {
        this.modleName = modleName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(int operateCode) {
        this.operateCode = operateCode;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public int getVolt() {
        return volt;
    }

    public void setVolt(int volt) {
        this.volt = volt;
    }

    public int getAif3mass() {
        return aif3mass;
    }

    public void setAif3mass(int aif3mass) {
        this.aif3mass = aif3mass;
    }

    public int getTargetVolt() {
        return targetVolt;
    }

    public void setTargetVolt(int targetVolt) {
        this.targetVolt = targetVolt;
    }

    public int getWorkVolt() {
        return workVolt;
    }

    public void setWorkVolt(int workVolt) {
        this.workVolt = workVolt;
    }

    public int getSetNb() {
        return setNb;
    }

    public void setSetNb(int setNb) {
        this.setNb = setNb;
    }

    public int getActualNb() {
        return actualNb;
    }

    public void setActualNb(int actualNb) {
        this.actualNb = actualNb;
    }

    public int getFeedingCycleCode() {
        return feedingCycleCode;
    }

    public void setFeedingCycleCode(int feedingCycleCode) {
        this.feedingCycleCode = feedingCycleCode;
    }

    public String getFeedingCycleName() {
        return feedingCycleName;
    }

    public void setFeedingCycleName(String feedingCycleName) {
        this.feedingCycleName = feedingCycleName;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getStartAnodePosition() {
        return startAnodePosition;
    }

    public void setStartAnodePosition(int startAnodePosition) {
        this.startAnodePosition = startAnodePosition;
    }

    public int getCurrentAnodePosition() {
        return currentAnodePosition;
    }

    public void setCurrentAnodePosition(int currentAnodePosition) {
        this.currentAnodePosition = currentAnodePosition;
    }

    public int getCurrentFault() {
        return currentFault;
    }

    public void setCurrentFault(int currentFault) {
        this.currentFault = currentFault;
    }

    public int getHighNoise() {
        return highNoise;
    }

    public void setHighNoise(int highNoise) {
        this.highNoise = highNoise;
    }

    public int getLowNoise() {
        return lowNoise;
    }

    public void setLowNoise(int lowNoise) {
        this.lowNoise = lowNoise;
    }

    public String getAeStartTime() {
        return aeStartTime;
    }

    public void setAeStartTime(String aeStartTime) {
        this.aeStartTime = aeStartTime;
    }

    public int getAeAverageVoltage() {
        return aeAverageVoltage;
    }

    public void setAeAverageVoltage(int aeAverageVoltage) {
        this.aeAverageVoltage = aeAverageVoltage;
    }

    public int getAeLastingTime() {
        return aeLastingTime;
    }

    public void setAeLastingTime(int aeLastingTime) {
        this.aeLastingTime = aeLastingTime;
    }

    public String getAeType() {
        return aeType;
    }

    public void setAeType(String aeType) {
        this.aeType = aeType;
    }

    public int getAeCnt() {
        return aeCnt;
    }

    public void setAeCnt(int aeCnt) {
        this.aeCnt = aeCnt;
    }

    public int getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(int connectStatus) {
        this.connectStatus = connectStatus;
    }
}
