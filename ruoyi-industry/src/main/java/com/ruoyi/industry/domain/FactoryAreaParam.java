package com.ruoyi.industry.domain;

/**
 * 请求参数
 */
public class FactoryAreaParam {

    /** 厂号 */
    private Long factoryNo;
    /** 区号 */
    private Long areaNo;
    /** 槽号 */
    private Long realSlotNo;
    /** 开始日期 */
    private String startDate;
    /** 结束日期 */
    private String endDate;

    public Long getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(Long factoryNo) {
        this.factoryNo = factoryNo;
    }

    public Long getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(Long areaNo) {
        this.areaNo = areaNo;
    }

    public Long getRealSlotNo() {
        return realSlotNo;
    }

    public void setRealSlotNo(Long realSlotNo) {
        this.realSlotNo = realSlotNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
