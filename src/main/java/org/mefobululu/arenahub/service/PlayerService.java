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
}
