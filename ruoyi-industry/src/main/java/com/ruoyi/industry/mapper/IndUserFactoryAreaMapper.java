package com.ruoyi.industry.mapper;

import java.util.List;
import com.ruoyi.industry.domain.IndUserFactoryArea;

/**
 * 用户厂区映射Mapper接口
 *
 * @author shao
 * @date 2022-03-19
 */
public interface IndUserFactoryAreaMapper
{
    /**
     * 查询用户厂区映射
     *
     * @param id 用户厂区映射主键
     * @return 用户厂区映射
     */
    public IndUserFactoryArea selectIndUserFactoryAreaById(Long id);

    /**
     * 查询用户厂区映射列表
     *
     * @param indUserFactoryArea 用户厂区映射
     * @return 用户厂区映射集合
     */
    public List<IndUserFactoryArea> selectIndUserFactoryAreaList(IndUserFactoryArea indUserFactoryArea);

    /**
     * 新增用户厂区映射
     *
     * @param indUserFactoryArea 用户厂区映射
     * @return 结果
     */
    public int insertIndUserFactoryArea(IndUserFactoryArea indUserFactoryArea);

    /**
     * 修改用户厂区映射
     *
     * @param indUserFactoryArea 用户厂区映射
     * @return 结果
     */
    public int updateIndUserFactoryArea(IndUserFactoryArea indUserFactoryArea);

    /**
     * 删除用户厂区映射
     *
     * @param id 用户厂区映射主键
     * @return 结果
     */
    public int deleteIndUserFactoryAreaById(Long id);

    /**
     * 删除用户厂区映射
     *
     * @param userId 用户id
     * @return 结果
     */
    public int deleteIndUserFactoryAreaByUserId(Long userId);

    /**
     * 批量删除用户厂区映射
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIndUserFactoryAreaByIds(Long[] ids);
}
