package com.ruoyi.industry.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.industry.domain.IndFactoryArea;
import org.apache.ibatis.annotations.Param;

/**
 * 厂区Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-19
 */
public interface IndFactoryAreaMapper 
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
     * 查询全部厂号
     */
    public JSONArray selectFactoryAll();

    /**
     * 根据厂号和区号查询槽号
     * @param potRoomId 厂号
     * @param sectionId 区号
     * @return 槽号集合
     */
    public List<Long> getRealPotId(@Param("potRoomId") long potRoomId, @Param("sectionId") long sectionId);

    /**
     * 获取厂区IP地址
     * @param factoryNo 厂号
     * @return ip
     */
    public String getFactoryIp(int factoryNo);

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
     * 删除厂区
     * 
     * @param id 厂区主键
     * @return 结果
     */
    public int deleteIndFactoryAreaById(Long id);

    /**
     * 批量删除厂区
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIndFactoryAreaByIds(Long[] ids);
}
