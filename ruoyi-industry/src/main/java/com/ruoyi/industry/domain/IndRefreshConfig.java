package com.ruoyi.industry.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 刷新配置对象 ind_refresh_config
 * 
 * @author shao
 * @date 2022-03-19
 */
public class IndRefreshConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 1：只刷一个工区，RefreshSection指定工区；
     2：按车间刷（厂房），RefreshPotroom = 1,刷新1厂房；
     3：所有工区一起刷*/
    @Excel(name = "刷新方式")
    private Long refreshStyle;

    /** 刷新厂区的厂号（PotRoom表的PotRommID） */
    @Excel(name = "刷新厂区的厂号")
    private Long refreshSectionFactoryNo;

    /** 刷新厂区的区号（PotRoom表的SectionID）*/
    @Excel(name = "刷新厂区的区号")
    private Long refreshSectionAreaNo;

    /** 刷新厂房 */
    @Excel(name = "刷新厂房")
    private Long refreshPotroom;

    /** 关联厂区 */
    private List<IndFactoryArea> factoryAreaList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRefreshStyle(Long refreshStyle) 
    {
        this.refreshStyle = refreshStyle;
    }

    public Long getRefreshStyle() 
    {
        return refreshStyle;
    }

    public Long getRefreshSectionFactoryNo() {
        return refreshSectionFactoryNo;
    }

    public void setRefreshSectionFactoryNo(Long refreshSectionFactoryNo) {
        this.refreshSectionFactoryNo = refreshSectionFactoryNo;
    }

    public Long getRefreshSectionAreaNo() {
        return refreshSectionAreaNo;
    }

    public void setRefreshSectionAreaNo(Long refreshSectionAreaNo) {
        this.refreshSectionAreaNo = refreshSectionAreaNo;
    }

    public void setRefreshPotroom(Long refreshPotroom)
    {
        this.refreshPotroom = refreshPotroom;
    }

    public Long getRefreshPotroom() 
    {
        return refreshPotroom;
    }

    public List<IndFactoryArea> getFactoryAreaList() {
        return factoryAreaList;
    }

    public void setFactoryAreaList(List<IndFactoryArea> factoryAreaList) {
        this.factoryAreaList = factoryAreaList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("refreshStyle", getRefreshStyle())
            .append("refreshSectionFactoryNo", getRefreshSectionFactoryNo())
            .append("refreshSectionAreaNo", getRefreshSectionAreaNo())
            .append("refreshPotroom", getRefreshPotroom())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
