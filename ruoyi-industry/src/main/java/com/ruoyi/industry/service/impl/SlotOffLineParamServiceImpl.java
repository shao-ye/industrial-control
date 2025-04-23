package com.ruoyi.industry.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.industry.domain.IndFactoryArea;
import com.ruoyi.industry.mapper.IndFactoryAreaMapper;
import com.ruoyi.industry.service.IIndFactoryAreaService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.industry.mapper.SlotOffLineParamMapper;
import com.ruoyi.industry.domain.SlotOffLineParam;
import com.ruoyi.industry.service.ISlotOffLineParamService;

/**
 * 测量数据Service业务层处理
 *
 * @author shao
 * @date 2022-03-22
 */
@Service
public class SlotOffLineParamServiceImpl implements ISlotOffLineParamService
{
    @Autowired
    private SlotOffLineParamMapper slotOffLineParamMapper;
    @Autowired
    private IndFactoryAreaMapper indFactoryAreaMapper;
    @Autowired
    IndFactoryAreaMapper factoryAreaMapper;

    /**
     * 查询测量数据
     *
     * @param machineNo 测量数据主键
     * @return 测量数据
     */
    @Override
    public SlotOffLineParam selectSlotOffLineParamByMachineNo(Long machineNo)
    {
        return slotOffLineParamMapper.selectSlotOffLineParamByMachineNo(machineNo);
    }

    /**
     * 查询测量数据列表
     *
     * @param signTime 数据日期
     * @param areaNo 查询厂区
     * @return 测量数据
     */
    @Override
    public List<SlotOffLineParam> selectSlotOffLineParamList(String signTime, long areaNo)
    {
        return slotOffLineParamMapper.selectSlotOffLineParamList(signTime, areaNo);
    }

    /**
     * 新增测量数据
     *
     * @param slotOffLineParam 测量数据
     * @return 结果
     */
    @Override
    public int insertSlotOffLineParam(SlotOffLineParam slotOffLineParam)
    {
        return slotOffLineParamMapper.insertSlotOffLineParam(slotOffLineParam);
    }

    /**
     * 补充测量数据
     * @return 补充的测量数据集合
     */
    @Override
    public List<SlotOffLineParam> supplementaryData(String signTime, long factoryNo, long areaNo) {
        IndFactoryArea indFactoryArea = new IndFactoryArea();
        indFactoryArea.setFactoryNo(factoryNo);
        indFactoryArea.setAreaNo(areaNo);
        List<IndFactoryArea> selectIndFactoryAreaList = indFactoryAreaMapper.selectIndFactoryAreaList(indFactoryArea);
        SysUser user = SecurityUtils.getLoginUser().getUser();
        for (int i = 0; i < selectIndFactoryAreaList.size(); i ++ ){
            SlotOffLineParam slotOffLineParam = new SlotOffLineParam();
            slotOffLineParam.setMachineNo(selectIndFactoryAreaList.get(i).getFactoryNo());
            slotOffLineParam.setSlotNo(selectIndFactoryAreaList.get(i).getSlotNo());
            slotOffLineParam.setRealSlotNo(selectIndFactoryAreaList.get(i).getRealSlotNo());
            slotOffLineParam.setSignTime(signTime);
            slotOffLineParam.setAuthor(user.getUserName());
            insertSlotOffLineParam(slotOffLineParam);
        }
        return selectSlotOffLineParamList(signTime,areaNo);
    }

    /**
     * 修改测量数据
     *
     * @param slotOffLineParamList 测量数据
     * @return 结果
     */
    @Override
    public int updateSlotOffLineParam(List<SlotOffLineParam> slotOffLineParamList)
    {
        int updateCount = 0;
        SysUser user = SecurityUtils.getLoginUser().getUser();
        for (int i = 0; i < slotOffLineParamList.size(); i ++ ){
            slotOffLineParamList.get(i).setAuthor(user.getUserName());
            updateCount = updateCount + slotOffLineParamMapper.updateSlotOffLineParam(slotOffLineParamList.get(i));
        }
        return updateCount;
    }

    /**
     * 批量删除测量数据
     *
     * @param machineNos 需要删除的测量数据主键
     * @return 结果
     */
    @Override
    public int deleteSlotOffLineParamByMachineNos(Long[] machineNos)
    {
        return slotOffLineParamMapper.deleteSlotOffLineParamByMachineNos(machineNos);
    }

    /**
     * 删除测量数据信息
     *
     * @param machineNo 测量数据主键
     * @return 结果
     */
    @Override
    public int deleteSlotOffLineParamByMachineNo(Long machineNo)
    {
        return slotOffLineParamMapper.deleteSlotOffLineParamByMachineNo(machineNo);
    }
}
