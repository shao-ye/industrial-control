package com.ruoyi.industry.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.industry.mapper.IndSocketInfoMapper;
import com.ruoyi.industry.domain.IndSocketInfo;
import com.ruoyi.industry.service.IIndSocketInfoService;

/**
 * socket管理Service业务层处理
 * 
 * @author shao
 * @date 2022-03-19
 */
@Service
public class IndSocketInfoServiceImpl implements IIndSocketInfoService 
{
    @Autowired
    private IndSocketInfoMapper indSocketInfoMapper;

    /**
     * 查询socket管理
     * 
     * @param id socket管理主键
     * @return socket管理
     */
    @Override
    public IndSocketInfo selectIndSocketInfoById(Long id)
    {
        return indSocketInfoMapper.selectIndSocketInfoById(id);
    }

    /**
     * 查询socket管理列表
     * 
     * @param indSocketInfo socket管理
     * @return socket管理
     */
    @Override
    public List<IndSocketInfo> selectIndSocketInfoList(IndSocketInfo indSocketInfo)
    {
        return indSocketInfoMapper.selectIndSocketInfoList(indSocketInfo);
    }

    /**
     * 新增socket管理
     * 
     * @param indSocketInfo socket管理
     * @return 结果
     */
    @Override
    public int insertIndSocketInfo(IndSocketInfo indSocketInfo)
    {
        indSocketInfo.setCreateTime(DateUtils.getNowDate());
        return indSocketInfoMapper.insertIndSocketInfo(indSocketInfo);
    }

    /**
     * 修改socket管理
     * 
     * @param indSocketInfo socket管理
     * @return 结果
     */
    @Override
    public int updateIndSocketInfo(IndSocketInfo indSocketInfo)
    {
        indSocketInfo.setUpdateTime(DateUtils.getNowDate());
        return indSocketInfoMapper.updateIndSocketInfo(indSocketInfo);
    }

    /**
     * 批量删除socket管理
     * 
     * @param ids 需要删除的socket管理主键
     * @return 结果
     */
    @Override
    public int deleteIndSocketInfoByIds(Long[] ids)
    {
        return indSocketInfoMapper.deleteIndSocketInfoByIds(ids);
    }

    /**
     * 删除socket管理信息
     * 
     * @param id socket管理主键
     * @return 结果
     */
    @Override
    public int deleteIndSocketInfoById(Long id)
    {
        return indSocketInfoMapper.deleteIndSocketInfoById(id);
    }
}
