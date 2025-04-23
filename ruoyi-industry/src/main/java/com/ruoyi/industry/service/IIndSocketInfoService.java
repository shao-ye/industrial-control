package com.ruoyi.industry.service;

import java.util.List;
import com.ruoyi.industry.domain.IndSocketInfo;

/**
 * socket管理Service接口
 * 
 * @author shao
 * @date 2022-03-19
 */
public interface IIndSocketInfoService 
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
     * 批量删除socket管理
     * 
     * @param ids 需要删除的socket管理主键集合
     * @return 结果
     */
    public int deleteIndSocketInfoByIds(Long[] ids);

    /**
     * 删除socket管理信息
     * 
     * @param id socket管理主键
     * @return 结果
     */
    public int deleteIndSocketInfoById(Long id);
}
