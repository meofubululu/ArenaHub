package org.mefobululu.arenahub.service;

import org.mefobululu.arenahub.mapper.PlayerMapper;
import org.mefobululu.arenahub.model.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerMapper playerMapper){
        this.playerMapper = playerMapper;
    }

    public Player findById(Long id){
        Player player = playerMapper.findById(id);
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
        return rows == 1;
    }

    public boolean deletePlayer(Long id){
        int rows = playerMapper.deletePlayer(id);
        return rows == 1;
    }

}
