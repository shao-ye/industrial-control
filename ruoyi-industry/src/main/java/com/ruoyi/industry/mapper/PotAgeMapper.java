package com.ruoyi.industry.mapper;

import com.ruoyi.industry.domain.PotAge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 电解槽槽龄Mapper接口
 *
 * @author shao
 * @date 2022-04-07
 */
public interface PotAgeMapper {

    /**
     * 查询电解槽槽龄列表
     */
    public List<PotAge> selectPotAgeList(@Param("potRoomId") Long potRoomId, @Param("sectionId") Long sectionId);

    /**
     * 修改电解槽槽龄
     *
     * @param potAge 电解槽槽龄
     * @return 结果
     */
    public int updatePotAge(PotAge potAge);
}
