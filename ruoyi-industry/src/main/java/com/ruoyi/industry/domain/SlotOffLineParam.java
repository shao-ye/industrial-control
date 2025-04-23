package com.ruoyi.industry.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 测量数据对象 SlotOffLineParam
 *
 * @author shao
 * @date 2022-03-22
 */
public class SlotOffLineParam {

    /** 厂房号 */
    private Long machineNo;

    /** 槽序号 */
    private Long slotNo;

    /** 槽号 */
    @Excel(name = "槽号")
    private Long realSlotNo;

    /** 记录时间 */
    private String signTime;

    /** 记录人 */
    @Excel(name = "记录人")
    private String author;

    /** 点位开始记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "点位开始记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String potBegintime;

    /** 出铝量 */
    @Excel(name = "出铝量")
    private Long metalMass;

    /** 质量 */
    @Excel(name = "质量")
    private BigDecimal metalQuality;

    /** 铁含量 */
    @Excel(name = "铁含量")
    private Float feContent;

    /** 硅含量 */
    @Excel(name = "硅含量")
    private Float siContent;

    /** 分子比 */
    @Excel(name = "分子比")
    private Float bathRatio;

    /** 电解质高度 */
    @Excel(name = "电解质高度")
    private Long bathHeight;

    /** 铝水高度 */
    @Excel(name = "铝水高度")
    private Long metalHeight;

    /** 槽温 */
    @Excel(name = "槽温")
    private Long bathTemperature;

    /** 炉底压降 */
    @Excel(name = "炉底压降")
    private Long cvd;

    /** 极距 */
    @Excel(name = "极距")
    private Long acd;

    /** 过热度 */
    @Excel(name = "过热度")
    private Long superheat;

    /** 浓度 */
    @Excel(name = "浓度")
    private Float al2o3Concen;

    /** 实际出铝量 */
    @Excel(name = "实际出铝量")
    private Long realmetalMass;

    public void setMachineNo(Long machineNo)
    {
        this.machineNo = machineNo;
    }

    public Long getMachineNo()
    {
        return machineNo;
    }
    public void setSlotNo(Long slotNo)
    {
        this.slotNo = slotNo;
    }

    public Long getSlotNo()
    {
        return slotNo;
    }
    public void setRealSlotNo(Long realSlotNo)
    {
        this.realSlotNo = realSlotNo;
    }

    public Long getRealSlotNo()
    {
        return realSlotNo;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getPotBegintime() {
        return potBegintime;
    }

    public void setPotBegintime(String potBegintime) {
        this.potBegintime = potBegintime;
    }

    public void setMetalMass(Long metalMass)
    {
        this.metalMass = metalMass;
    }

    public Long getMetalMass()
    {
        return metalMass;
    }

    public BigDecimal getMetalQuality() {
        return metalQuality;
    }

    public void setMetalQuality(BigDecimal metalQuality) {
        this.metalQuality = metalQuality;
    }

    public Float getFeContent() {
        return feContent;
    }

    public void setFeContent(Float feContent) {
        this.feContent = feContent;
    }

    public Float getSiContent() {
        return siContent;
    }

    public void setSiContent(Float siContent) {
        this.siContent = siContent;
    }

    public Float getBathRatio() {
        return bathRatio;
    }

    public void setBathRatio(Float bathRatio) {
        this.bathRatio = bathRatio;
    }

    public void setBathHeight(Long bathHeight)
    {
        this.bathHeight = bathHeight;
    }

    public Long getBathHeight()
    {
        return bathHeight;
    }
    public void setMetalHeight(Long metalHeight)
    {
        this.metalHeight = metalHeight;
    }

    public Long getMetalHeight()
    {
        return metalHeight;
    }
    public void setBathTemperature(Long bathTemperature)
    {
        this.bathTemperature = bathTemperature;
    }

    public Long getBathTemperature()
    {
        return bathTemperature;
    }
    public void setCvd(Long cvd)
    {
        this.cvd = cvd;
    }

    public Long getCvd()
    {
        return cvd;
    }
    public void setAcd(Long acd)
    {
        this.acd = acd;
    }

    public Long getAcd()
    {
        return acd;
    }
    public void setSuperheat(Long superheat)
    {
        this.superheat = superheat;
    }

    public Long getSuperheat()
    {
        return superheat;
    }

    public Float getAl2o3Concen() {
        return al2o3Concen;
    }

    public void setAl2o3Concen(Float al2o3Concen) {
        this.al2o3Concen = al2o3Concen;
    }

    public void setRealmetalMass(Long realmetalMass)
    {
        this.realmetalMass = realmetalMass;
    }

    public Long getRealmetalMass()
    {
        return realmetalMass;
    }
}
