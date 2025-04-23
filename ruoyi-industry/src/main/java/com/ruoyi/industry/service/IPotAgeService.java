package com.ruoyi.industry.service;

import com.ruoyi.industry.domain.PotAge;

import java.util.List;

/**
 * 电解槽槽龄Service接口
 *
 * @author shao
 * @date 2022-04-07
 */
public interface IPotAgeService {

    /**
     * 查询电解槽槽龄列表
     */
    public List<PotAge> selectPotAgeList(Long potRoomId, Long sectionId);

    /**
     * 修改电解槽槽龄
     */
    public void updatePotAgeList(List<PotAge> potAgeList);
}
