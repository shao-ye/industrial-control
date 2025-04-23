package com.ruoyi.industry.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AE信息列表
 */
public class IndAeInfo extends BaseEntity {

    /** 报告 AE 数据序列 */
    private long reportAeDataSerial;

    /** 槽号*/
    private int realSlotNo;

    /** 发生时刻 */
    private String aeStartTime;

    /** 平均电压 */
    private int aeAverageVoltage;

    /** 峰值电压 */
    private int aePeakVoltage;

    /** 持续时间 */
    private int aeLastingTime;

    /** 效应类型*/
    private String aeType;

    /** 序位 */
    private int num;

    public long getReportAeDataSerial() {
        return reportAeDataSerial;
    }

    public void setReportAeDataSerial(long reportAeDataSerial) {
        this.reportAeDataSerial = reportAeDataSerial;
    }

    public int getRealSlotNo() {
        return realSlotNo;
    }

    public void setRealSlotNo(int realSlotNo) {
        this.realSlotNo = realSlotNo;
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

    public int getAePeakVoltage() {
        return aePeakVoltage;
    }

    public void setAePeakVoltage(int aePeakVoltage) {
        this.aePeakVoltage = aePeakVoltage;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
