package com.ruoyi.industry.mapper;

import com.ruoyi.industry.domain.SlotOffLineCheckBox;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 测量数据显示
 *
 * @author shao
 */
public interface SlotOffLineParamChartMapper {

    /**
     * 查询测量数据显示界面checkbox
     *
     * @param userId 用户id
     * @return 测量数据显示界面checkbox
     */
    public SlotOffLineCheckBox selectIndSlot0ffLineCheckboxByUserId(Long userId);

    /**
     * 新增测量数据显示界面checkbox
     *
     * @param slotOffLineCheckBox 测量数据显示界面checkbox
     * @return 结果
     */
    public int insertIndSlot0ffLineCheckbox(SlotOffLineCheckBox slotOffLineCheckBox);

    /**
     * 修改测量数据显示界面checkbox
     *
     * @param slotOffLineCheckBox 测量数据显示界面checkbox
     * @return 结果
     */
    public int updateIndSlot0ffLineCheckboxByUserId(SlotOffLineCheckBox slotOffLineCheckBox);

    /**
     * 修改测量数据
     * */
    public List<Map<String,String>> dayReportSlotOffLine(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("realPotId") int realPotId);

    /**
     * 修改测量数据
     * */
    public List<Map<String,String>> slotOffLine(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("realPotId") int realPotId);

}
