package com.ruoyi.industry.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.industry.domain.IndFactoryArea;
import com.ruoyi.industry.domain.IndUserFactoryArea;
import com.ruoyi.industry.mapper.IndFactoryAreaMapper;
import com.ruoyi.industry.mapper.IndUserFactoryAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.industry.mapper.IndRefreshConfigMapper;
import com.ruoyi.industry.domain.IndRefreshConfig;
import com.ruoyi.industry.service.IIndRefreshConfigService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 刷新配置Service业务层处理
 * 
 * @author shao
 * @date 2022-03-19
 */
@Service
public class IndRefreshConfigServiceImpl implements IIndRefreshConfigService 
{
    @Autowired
    private IndRefreshConfigMapper indRefreshConfigMapper;
    @Autowired
    private IndFactoryAreaMapper indFactoryAreaMapper;
    @Autowired
    private IndUserFactoryAreaMapper indUserFactoryAreaMapper;
    @Autowired
    IndFactoryAreaMapper factoryAreaMapper;

    /**
     * 查询刷新配置
     * 
     * @param id 刷新配置主键
     * @return 刷新配置
     */
    @Override
    public IndRefreshConfig selectIndRefreshConfigById(Long id)
    {
        return indRefreshConfigMapper.selectIndRefreshConfigById(id);
    }

    /**
     * 根据用户查询配置信息
     * @param userId 用户id
     * @return 刷新配置
     */
    @Override
    public IndRefreshConfig selectIndRefreshConfigByUserId(Long userId) {
        IndRefreshConfig refreshConfig = indRefreshConfigMapper.selectIndRefreshConfigByUserId(userId);
        List<IndFactoryArea> indFactoryAreaList = indFactoryAreaMapper.selectIndFactoryAreaListByUserId(userId);
        for (int i = 0; i < indFactoryAreaList.size(); i ++ ){
            // 取槽号集合
            List<Long> realPotIdList = factoryAreaMapper.getRealPotId(indFactoryAreaList.get(i).getFactoryNo(),indFactoryAreaList.get(i).getAreaNo());
            indFactoryAreaList.get(i).setRealSlotNoList(realPotIdList);
        }

        refreshConfig.setFactoryAreaList(indFactoryAreaList);
        return refreshConfig;
    }

    /**
     * 查询刷新配置列表
     * 
     * @param indRefreshConfig 刷新配置
     * @return 刷新配置
     */
    @Override
    public List<IndRefreshConfig> selectIndRefreshConfigList(IndRefreshConfig indRefreshConfig)
    {
        return indRefreshConfigMapper.selectIndRefreshConfigList(indRefreshConfig);
    }

    /**
     * 新增刷新配置
     * 
     * @param indRefreshConfig 刷新配置
     * @return 结果
     */
    @Transactional
    @Override
    public int insertIndRefreshConfig(IndRefreshConfig indRefreshConfig)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        long userId = user.getUserId();
        // 删除已有的用户厂区关系
        indUserFactoryAreaMapper.deleteIndUserFactoryAreaByUserId(userId);
        indRefreshConfig.setUserId(userId);
        // 添加新的用户厂区关系
        List<IndFactoryArea> indFactoryAreaList = indRefreshConfig.getFactoryAreaList();
        for (int i = 0; i < indFactoryAreaList.size(); i ++ ){
            IndUserFactoryArea indUserFactoryArea = new IndUserFactoryArea();
            indUserFactoryArea.setUserId(userId);
            indUserFactoryArea.setPotRoomId(indFactoryAreaList.get(i).getFactoryNo());
            indUserFactoryArea.setSectionId(indFactoryAreaList.get(i).getAreaNo());
            indUserFactoryArea.setCreateBy(String.valueOf(userId));
            indUserFactoryArea.setCreateTime(DateUtils.getNowDate());
            indUserFactoryAreaMapper.insertIndUserFactoryArea(indUserFactoryArea);
        }
        IndRefreshConfig userConfig = indRefreshConfigMapper.selectIndRefreshConfigByUserId(userId);
        if (null == userConfig){
            indRefreshConfig.setCreateBy(String.valueOf(userId));
            indRefreshConfig.setCreateTime(DateUtils.getNowDate());
            return indRefreshConfigMapper.insertIndRefreshConfig(indRefreshConfig);
        }else {
            indRefreshConfig.setId(userConfig.getId());
            indRefreshConfig.setUpdateBy(String.valueOf(userId));
            indRefreshConfig.setUpdateTime(DateUtils.getNowDate());
            return indRefreshConfigMapper.updateIndRefreshConfig(indRefreshConfig);
        }
    }

    /**
     * 修改刷新配置
     * 
     * @param indRefreshConfig 刷新配置
     * @return 结果
     */
    @Transactional
    @Override
    public int updateIndRefreshConfig(IndRefreshConfig indRefreshConfig)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        long userId = user.getUserId();
        // 删除已有的用户厂区关系
        indUserFactoryAreaMapper.deleteIndUserFactoryAreaByUserId(userId);
        indRefreshConfig.setUserId(userId);
        // 添加新的用户厂区关系
        List<IndFactoryArea> indFactoryAreaList = indRefreshConfig.getFactoryAreaList();
        for (int i = 0; i < indFactoryAreaList.size(); i ++ ){
            IndUserFactoryArea indUserFactoryArea = new IndUserFactoryArea();
            indUserFactoryArea.setUserId(userId);
            indUserFactoryArea.setPotRoomId(indFactoryAreaList.get(i).getFactoryNo());
            indUserFactoryArea.setSectionId(indFactoryAreaList.get(i).getAreaNo());
            indUserFactoryArea.setCreateBy(String.valueOf(userId));
            indUserFactoryArea.setCreateTime(DateUtils.getNowDate());
            indUserFactoryAreaMapper.insertIndUserFactoryArea(indUserFactoryArea);
        }
        IndRefreshConfig userConfig = indRefreshConfigMapper.selectIndRefreshConfigByUserId(userId);
        if (null == userConfig){
            indRefreshConfig.setCreateBy(String.valueOf(userId));
            indRefreshConfig.setCreateTime(DateUtils.getNowDate());
            return indRefreshConfigMapper.insertIndRefreshConfig(indRefreshConfig);
        }else {
            indRefreshConfig.setId(userConfig.getId());
            indRefreshConfig.setUpdateBy(String.valueOf(userId));
            indRefreshConfig.setUpdateTime(DateUtils.getNowDate());
            return indRefreshConfigMapper.updateIndRefreshConfig(indRefreshConfig);
        }
    }

    /**
     * 批量删除刷新配置
     * 
     * @param ids 需要删除的刷新配置主键
     * @return 结果
     */
    @Override
    public int deleteIndRefreshConfigByIds(Long[] ids)
    {
        return indRefreshConfigMapper.deleteIndRefreshConfigByIds(ids);
    }

    /**
     * 删除刷新配置信息
     * 
     * @param id 刷新配置主键
     * @return 结果
     */
    @Override
    public int deleteIndRefreshConfigById(Long id)
    {
        return indRefreshConfigMapper.deleteIndRefreshConfigById(id);
    }
}
