package org.mefobululu.arenahub.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mefobululu.arenahub.model.Player;

@Mapper
public interface PlayerMapper {
    @Select("SELECT id, nickname, level FROM player WHERE id = #{id}")
    Player findById(Long id);
}
