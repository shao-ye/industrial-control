package com.ruoyi.industry.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.industry.mapper.IndFactoryAreaMapper;
import com.ruoyi.industry.domain.IndFactoryArea;
import com.ruoyi.industry.service.IIndFactoryAreaService;

/**
 * 厂区Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-19
 */
@Service
public class IndFactoryAreaServiceImpl implements IIndFactoryAreaService
{
    @Autowired
    private IndFactoryAreaMapper indFactoryAreaMapper;
    @Autowired
    IndFactoryAreaMapper factoryAreaMapper;

    /**
     * 查询厂区
     * 
     * @param id 厂区主键
     * @return 厂区
     */
    @Override
    public IndFactoryArea selectIndFactoryAreaById(Long id)
    {
        return indFactoryAreaMapper.selectIndFactoryAreaById(id);
    }

    /**
     * 查询厂区列表
     * 
     * @param indFactoryArea 厂区
     * @return 厂区
     */
    @Override
    public List<IndFactoryArea> selectIndFactoryAreaList(IndFactoryArea indFactoryArea)
    {
        return indFactoryAreaMapper.selectIndFactoryAreaList(indFactoryArea);
    }

    /**
     * 根据用户id查询厂区列表
     * @param userId 用户id
     * @return 厂区集合
     */
    @Override
    public List<IndFactoryArea> selectIndFactoryAreaListByUserId(long userId) {
        List<IndFactoryArea> indFactoryAreaList = indFactoryAreaMapper.selectIndFactoryAreaListByUserId(userId);
        for (int i = 0; i < indFactoryAreaList.size(); i ++ ){
            // 取槽号集合
            List<Long> realPotIdList = factoryAreaMapper.getRealPotId(indFactoryAreaList.get(i).getFactoryNo(),indFactoryAreaList.get(i).getAreaNo());
            indFactoryAreaList.get(i).setRealSlotNoList(realPotIdList);
        }
        return indFactoryAreaList;
    }

    @Override
    public JSONArray selectIndFactoryAreaListAll() {
        JSONArray indFactoryAreaList = factoryAreaMapper.selectIndFactoryAreaListAll();
        for (int i = 0; i < indFactoryAreaList.size(); i ++ ){
            List<Long> realPotIdList = factoryAreaMapper.getRealPotId(indFactoryAreaList.getJSONObject(i).getLongValue("factoryNo"),indFactoryAreaList.getJSONObject(i).getLongValue("areaNo"));
            indFactoryAreaList.getJSONObject(i).put("realSlotNoList",realPotIdList);
        }
        return indFactoryAreaList;
    }

    @Override
    public JSONArray selectFactoryAll() {
        return factoryAreaMapper.selectFactoryAll();
    }

    /**
     * 新增厂区
     * 
     * @param indFactoryArea 厂区
     * @return 结果
     */
    @Override
    public int insertIndFactoryArea(IndFactoryArea indFactoryArea)
    {
        indFactoryArea.setCreateTime(DateUtils.getNowDate());
        return indFactoryAreaMapper.insertIndFactoryArea(indFactoryArea);
    }

    /**
     * 修改厂区
     * 
     * @param indFactoryArea 厂区
     * @return 结果
     */
    @Override
    public int updateIndFactoryArea(IndFactoryArea indFactoryArea)
    {
        indFactoryArea.setUpdateTime(DateUtils.getNowDate());
        return indFactoryAreaMapper.updateIndFactoryArea(indFactoryArea);
    }

    /**
     * 批量删除厂区
     * 
     * @param ids 需要删除的厂区主键
     * @return 结果
     */
    @Override
    public int deleteIndFactoryAreaByIds(Long[] ids)
    {
        return indFactoryAreaMapper.deleteIndFactoryAreaByIds(ids);
    }

    /**
     * 删除厂区信息
     * 
     * @param id 厂区主键
     * @return 结果
     */
    @Override
    public int deleteIndFactoryAreaById(Long id)
    {
        return indFactoryAreaMapper.deleteIndFactoryAreaById(id);
    }
}
