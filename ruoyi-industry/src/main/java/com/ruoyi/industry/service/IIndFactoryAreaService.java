package com.ruoyi.industry.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.industry.domain.IndFactoryArea;

/**
 * 厂区Service接口
 * 
 * @author ruoyi
 * @date 2022-03-19
 */
public interface IIndFactoryAreaService 
{
    /**
     * 查询厂区
     * 
     * @param id 厂区主键
     * @return 厂区
     */
    public IndFactoryArea selectIndFactoryAreaById(Long id);

    /**
     * 查询厂区列表
     *
     * @param indFactoryArea 厂区
     * @return 厂区集合
     */
    public List<IndFactoryArea> selectIndFactoryAreaList(IndFactoryArea indFactoryArea);

    /**
     * 根据用户id查询厂区列表
     * @param userId 用户id
     * @return 厂区集合
     */
    public List<IndFactoryArea> selectIndFactoryAreaListByUserId(long userId);

    /**
     * 查询全部厂区列表
     * @return 厂区集合
     */
    public JSONArray selectIndFactoryAreaListAll();

    /**
     * 查询全部厂区列表
     * @return 厂区集合
     */
    public JSONArray selectFactoryAll();

    /**
     * 新增厂区
     * 
     * @param indFactoryArea 厂区
     * @return 结果
     */
    public int insertIndFactoryArea(IndFactoryArea indFactoryArea);

    /**
     * 修改厂区
     * 
     * @param indFactoryArea 厂区
     * @return 结果
     */
    public int updateIndFactoryArea(IndFactoryArea indFactoryArea);

    /**
     * 批量删除厂区
     * 
     * @param ids 需要删除的厂区主键集合
     * @return 结果
     */
    public int deleteIndFactoryAreaByIds(Long[] ids);

    /**
     * 删除厂区信息
     * 
     * @param id 厂区主键
     * @return 结果
     */
    public int deleteIndFactoryAreaById(Long id);
}
