package com.ruoyi.industry.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 厂区对象 ind_factory_area
 * 
 * @author ruoyi
 * @date 2022-03-19
 */
public class IndFactoryArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 厂房号 */
    @Excel(name = "厂房号")
    private Long factoryNo;

    /** 区域号 */
    @Excel(name = "区域号")
    private Long areaNo;

    /** 厂区名称 */
    @Excel(name = "厂区名称")
    private String sectionName;

    private Long slotNo;

    private Long realSlotNo;

    /** 槽号 */
    private List<Long> realSlotNoList;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFactoryNo(Long factoryNo) 
    {
        this.factoryNo = factoryNo;
    }

    public Long getFactoryNo() 
    {
        return factoryNo;
    }
    public void setAreaNo(Long areaNo) 
    {
        this.areaNo = areaNo;
    }

    public Long getAreaNo() 
    {
        return areaNo;
    }
    public void setSectionName(String sectionName) 
    {
        this.sectionName = sectionName;
    }

    public String getSectionName() 
    {
        return sectionName;
    }

    public Long getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(Long slotNo) {
        this.slotNo = slotNo;
    }

    public List<Long> getRealSlotNoList() {
        return realSlotNoList;
    }

    public Long getRealSlotNo() {
        return realSlotNo;
    }

    public void setRealSlotNo(Long realSlotNo) {
        this.realSlotNo = realSlotNo;
    }

    public void setRealSlotNoList(List<Long> realSlotNoList) {
        this.realSlotNoList = realSlotNoList;
    }

    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("factoryNo", getFactoryNo())
            .append("areaNo", getAreaNo())
            .append("sectionName", getSectionName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
