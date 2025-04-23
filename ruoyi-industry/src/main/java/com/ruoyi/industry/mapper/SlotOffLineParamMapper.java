package com.ruoyi.industry.mapper;

import java.util.List;
import com.ruoyi.industry.domain.SlotOffLineParam;
import org.apache.ibatis.annotations.Param;

/**
 * 测量数据Mapper接口
 *
 * @author shao
 * @date 2022-03-22
 */
public interface SlotOffLineParamMapper
{
    /**
     * 查询测量数据
     *
     * @param machineNo 测量数据主键
     * @return 测量数据
     */
    public SlotOffLineParam selectSlotOffLineParamByMachineNo(Long machineNo);

    /**
     * 查询测量数据列表
     * @param signTime 数据日期
     * @param areaNo 查询厂区
     * @return 测量数据
     */
    public List<SlotOffLineParam> selectSlotOffLineParamList(@Param("signTime") String signTime,@Param("areaNo") long areaNo);

    /**
     * 新增测量数据
     *
     * @param slotOffLineParam 测量数据
     * @return 结果
     */
    public int insertSlotOffLineParam(SlotOffLineParam slotOffLineParam);

    /**
     * 修改测量数据
     *
     * @param slotOffLineParam 测量数据
     * @return 结果
     */
    public int updateSlotOffLineParam(SlotOffLineParam slotOffLineParam);

    /**
     * 删除测量数据
     *
     * @param machineNo 测量数据主键
     * @return 结果
     */
    public int deleteSlotOffLineParamByMachineNo(Long machineNo);

    /**
     * 批量删除测量数据
     *
     * @param machineNos 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSlotOffLineParamByMachineNos(Long[] machineNos);
}
