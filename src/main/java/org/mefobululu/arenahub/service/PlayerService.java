package org.mefobululu.arenahub.service;

import org.mefobululu.arenahub.mapper.PlayerMapper;
import org.mefobululu.arenahub.model.Player;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.time.Duration;

@Service
public class PlayerService {
    private final PlayerMapper playerMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final JsonMapper jsonMapper;

    public PlayerService(PlayerMapper playerMapper,StringRedisTemplate stringRedisTemplate,JsonMapper jsonMapper){
        this.playerMapper = playerMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.jsonMapper = jsonMapper;
    }

    public Player findById(Long id){
        String key = "player:"+id;
        String json = stringRedisTemplate.opsForValue().get(key);

        if(json != null){
            return jsonMapper.readValue(json,Player.class);
        }
        Player player = playerMapper.findById(id);
        if(player == null) {
            return null;
        }
        String playerJson = jsonMapper.writeValueAsString(player);
        stringRedisTemplate.opsForValue().set(
                key,
                playerJson,
                Duration.ofMinutes(5));
        return player;
    }

    public Player createPlayer(String nickname){
        Player player = new Player(null,nickname,1);
        int rows = playerMapper.insertPlayer(player);
        if(rows == 1) {return player;}
        return null;
    }
    public boolean updateLevel(Long id, Integer level){
        int rows = playerMapper.updateLevel(id,level);
        if(rows == 1){
            stringRedisTemplate.delete("player:"+id);
        }
        return rows == 1;
    }

    public boolean deletePlayer(Long id){
        int rows = playerMapper.deletePlayer(id);
        if(rows == 1){
            stringRedisTemplate.delete("player:"+id);
        }
        return rows == 1;
    }
}
