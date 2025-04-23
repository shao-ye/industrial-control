package com.ruoyi.industry.service;

import java.util.List;
import com.ruoyi.industry.domain.SlotOffLineParam;

/**
 * 测量数据Service接口
 *
 * @author shao
 * @date 2022-03-22
 */
public interface ISlotOffLineParamService
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
     *
     * @param signTime 数据日期
     * @param areaNo 查询厂区
     * @return 测量数据集合
     */
    public List<SlotOffLineParam> selectSlotOffLineParamList(String signTime, long areaNo);

    /**
     * 新增测量数据
     *
     * @param slotOffLineParam 测量数据
     * @return 结果
     */
    public int insertSlotOffLineParam(SlotOffLineParam slotOffLineParam);

    /**
     * 补充测量数据
     * @return 补充的测量数据集合
     */
    List<SlotOffLineParam> supplementaryData(String signTime, long factoryNo, long areaNo);

    /**
     * 修改测量数据
     *
     * @param slotOffLineParamList 测量数据
     * @return 结果
     */
    public int updateSlotOffLineParam(List<SlotOffLineParam> slotOffLineParamList);

    /**
     * 批量删除测量数据
     *
     * @param machineNos 需要删除的测量数据主键集合
     * @return 结果
     */
    public int deleteSlotOffLineParamByMachineNos(Long[] machineNos);

    /**
     * 删除测量数据信息
     *
     * @param machineNo 测量数据主键
     * @return 结果
     */
    public int deleteSlotOffLineParamByMachineNo(Long machineNo);
}
