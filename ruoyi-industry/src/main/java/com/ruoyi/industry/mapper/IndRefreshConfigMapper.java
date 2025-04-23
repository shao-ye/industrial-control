package com.ruoyi.industry.mapper;

import java.util.List;
import com.ruoyi.industry.domain.IndRefreshConfig;

/**
 * 刷新配置Mapper接口
 * 
 * @author shao
 * @date 2022-03-19
 */
public interface IndRefreshConfigMapper 
{
    /**
     * 查询刷新配置
     * 
     * @param id 刷新配置主键
     * @return 刷新配置
     */
    public IndRefreshConfig selectIndRefreshConfigById(Long id);

    /**
     * 根据用户查询配置信息
     * @param userId 用户id
     * @return 刷新配置
     */
    public IndRefreshConfig selectIndRefreshConfigByUserId(Long userId);
    /**
     * 查询刷新配置列表
     * 
     * @param indRefreshConfig 刷新配置
     * @return 刷新配置集合
     */
    public List<IndRefreshConfig> selectIndRefreshConfigList(IndRefreshConfig indRefreshConfig);

    /**
     * 新增刷新配置
     * 
     * @param indRefreshConfig 刷新配置
     * @return 结果
     */
    public int insertIndRefreshConfig(IndRefreshConfig indRefreshConfig);

    /**
     * 修改刷新配置
     * 
     * @param indRefreshConfig 刷新配置
     * @return 结果
     */
    public int updateIndRefreshConfig(IndRefreshConfig indRefreshConfig);

    /**
     * 删除刷新配置
     * 
     * @param id 刷新配置主键
     * @return 结果
     */
    public int deleteIndRefreshConfigById(Long id);

    /**
     * 批量删除刷新配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIndRefreshConfigByIds(Long[] ids);
}
