package com.ruoyi.industry.mapper;

import java.util.List;
import com.ruoyi.industry.domain.IndSocketInfo;

/**
 * socket管理Mapper接口
 * 
 * @author shao
 * @date 2022-03-19
 */
public interface IndSocketInfoMapper 
{
    /**
     * 查询socket管理
     * 
     * @param id socket管理主键
     * @return socket管理
     */
    public IndSocketInfo selectIndSocketInfoById(Long id);

    /**
     * 查询socket管理列表
     * 
     * @param indSocketInfo socket管理
     * @return socket管理集合
     */
    public List<IndSocketInfo> selectIndSocketInfoList(IndSocketInfo indSocketInfo);

    /**
     * 新增socket管理
     * 
     * @param indSocketInfo socket管理
     * @return 结果
     */
    public int insertIndSocketInfo(IndSocketInfo indSocketInfo);

    /**
     * 修改socket管理
     * 
     * @param indSocketInfo socket管理
     * @return 结果
     */
    public int updateIndSocketInfo(IndSocketInfo indSocketInfo);

    /**
     * 删除socket管理
     * 
     * @param id socket管理主键
     * @return 结果
     */
    public int deleteIndSocketInfoById(Long id);

    /**
     * 批量删除socket管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIndSocketInfoByIds(Long[] ids);
}
