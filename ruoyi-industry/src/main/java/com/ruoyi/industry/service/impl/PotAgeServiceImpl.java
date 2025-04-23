package com.ruoyi.industry.service.impl;

import com.ruoyi.industry.domain.PotAge;
import com.ruoyi.industry.mapper.PotAgeMapper;
import com.ruoyi.industry.service.IPotAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电解槽槽龄Service业务层处理
 *
 * @author shao
 * @date 2022-04-07
 */
@Service
public class PotAgeServiceImpl implements IPotAgeService {

    @Autowired
    private PotAgeMapper potAgeMapper;

    /**
     * 查询电解槽槽龄列表
     */
    @Override
    public List<PotAge> selectPotAgeList(Long potRoomId, Long sectionId)
    {
        return potAgeMapper.selectPotAgeList(potRoomId,sectionId);
    }

    /**
     * 修改电解槽槽龄
     */
    @Override
    public void updatePotAgeList(List<PotAge> potAgeList) {
        for (int i = 0; i < potAgeList.size(); i ++ ){
            potAgeMapper.updatePotAge(potAgeList.get(i));
        }
    }
}
