package com.ruoyi.industry.domain;

/**
 * 测量数据显示界面checkbox
 */
public class SlotOffLineCheckBox {

    /** 主键 */
    private long id;
    /** 用户id */
    private long userId;
    /** 设定电压 */
    private int tartgetVoltage;
    /** 工作电压 */
    private int workingVoltage;
    /** 平均电压 */
    private int averageVoltage;
    /** 高频噪声 */
    private int instHighNoiseValue;
    /** 低频噪声 */
    private int instLowNoiseValue;
    /** 实际氟盐量 */
    private int actualFluorideSalt;
    /** AE次数 */
    private int dayReport;
    /** 出铝指示量 */
    private int metalMass;
    /** 铝水高度 */
    private int metalHeight;
    /** 电解质高度 */
    private int bathHeight;
    /** 槽温/电解质温度 */
    private int bathTemperature;
    /** 分子比 */
    private int bathRatio;
    /** 铁含量 */
    private int feContent;
    /** 硅含量 */
    private int siContent;
    /** 质量 */
    private int metalQuality;
    /** 浓度 */
    private int al2O3Concen;
    /** 炉底压降	 */
    private int cvd;
    /** 极距 */
    private int acd;
    /** 实际出铝量 */
    private int realMetalMass;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTartgetVoltage() {
        return tartgetVoltage;
    }

    public void setTartgetVoltage(int tartgetVoltage) {
        this.tartgetVoltage = tartgetVoltage;
    }

    public int getWorkingVoltage() {
        return workingVoltage;
    }

    public void setWorkingVoltage(int workingVoltage) {
        this.workingVoltage = workingVoltage;
    }

    public int getAverageVoltage() {
        return averageVoltage;
    }

    public void setAverageVoltage(int averageVoltage) {
        this.averageVoltage = averageVoltage;
    }

    public int getInstHighNoiseValue() {
        return instHighNoiseValue;
    }

    public void setInstHighNoiseValue(int instHighNoiseValue) {
        this.instHighNoiseValue = instHighNoiseValue;
    }

    public int getInstLowNoiseValue() {
        return instLowNoiseValue;
    }

    public void setInstLowNoiseValue(int instLowNoiseValue) {
        this.instLowNoiseValue = instLowNoiseValue;
    }

    public int getActualFluorideSalt() {
        return actualFluorideSalt;
    }

    public void setActualFluorideSalt(int actualFluorideSalt) {
        this.actualFluorideSalt = actualFluorideSalt;
    }

    public int getDayReport() {
        return dayReport;
    }

    public void setDayReport(int dayReport) {
        this.dayReport = dayReport;
    }

    public int getMetalMass() {
        return metalMass;
    }

    public void setMetalMass(int metalMass) {
        this.metalMass = metalMass;
    }

    public int getMetalHeight() {
        return metalHeight;
    }

    public void setMetalHeight(int metalHeight) {
        this.metalHeight = metalHeight;
    }

    public int getBathHeight() {
        return bathHeight;
    }

    public void setBathHeight(int bathHeight) {
        this.bathHeight = bathHeight;
    }

    public int getBathTemperature() {
        return bathTemperature;
    }

    public void setBathTemperature(int bathTemperature) {
        this.bathTemperature = bathTemperature;
    }

    public int getBathRatio() {
        return bathRatio;
    }

    public void setBathRatio(int bathRatio) {
        this.bathRatio = bathRatio;
    }

    public int getFeContent() {
        return feContent;
    }

    public void setFeContent(int feContent) {
        this.feContent = feContent;
    }

    public int getSiContent() {
        return siContent;
    }

    public void setSiContent(int siContent) {
        this.siContent = siContent;
    }

    public int getMetalQuality() {
        return metalQuality;
    }

    public void setMetalQuality(int metalQuality) {
        this.metalQuality = metalQuality;
    }

    public int getAl2O3Concen() {
        return al2O3Concen;
    }

    public void setAl2O3Concen(int al2O3Concen) {
        this.al2O3Concen = al2O3Concen;
    }

    public int getCvd() {
        return cvd;
    }

    public void setCvd(int cvd) {
        this.cvd = cvd;
    }

    public int getAcd() {
        return acd;
    }

    public void setAcd(int acd) {
        this.acd = acd;
    }

    public int getRealMetalMass() {
        return realMetalMass;
    }

    public void setRealMetalMass(int realMetalMass) {
        this.realMetalMass = realMetalMass;
    }
}
