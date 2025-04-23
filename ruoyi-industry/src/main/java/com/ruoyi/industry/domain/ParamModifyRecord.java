package com.ruoyi.industry.domain;

import java.math.BigDecimal;

/**
 * 修改参数
 */
public class ParamModifyRecord {

    /** 主键ID */
    private Long serialId;
    /** 槽号 */
    private int slotId;
    /** 修改时间 */
    private String writeDate;
    /** 修改人 */
    private String author;
    /** 参数名称 */
    private String paramStyle;
    /** 原始值 */
    private String originalValue;
    /** 修改值 */
    private String newValue;

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParamStyle() {
        return paramStyle;
    }

    public void setParamStyle(String paramStyle) {
        this.paramStyle = paramStyle;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
