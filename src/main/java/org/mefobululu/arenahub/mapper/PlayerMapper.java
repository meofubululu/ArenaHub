package org.mefobululu.arenahub.mapper;

import org.apache.ibatis.annotations.*;
import org.mefobululu.arenahub.model.Player;

@Mapper
public interface PlayerMapper {
    @Select("SELECT id, nickname, level FROM player WHERE id = #{id}")
    Player findById(Long id);

    @Insert("INSERT INTO player (nickname) VALUES (#{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPlayer(Player player);

    @Update("UPDATE player SET level = #{level} WHERE id = #{id}")
    int updateLevel(
            @Param("id") Long id,
            @Param("level") Integer level
    );

    @Delete("DELETE FROM player WHERE id = #{id}")
    int deletePlayer(Long id);
}

