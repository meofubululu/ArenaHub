package org.mefobululu.arenahub.controller;

import org.mefobululu.arenahub.dto.CreatePlayerRequest;
import org.mefobululu.arenahub.dto.UpdatePlayerLevelRequest;
import org.mefobululu.arenahub.model.Player;
import org.mefobululu.arenahub.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    private final PlayerService playerService;

    public HelloController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return "Hello, "+name;
    }

    @GetMapping("/players/{id}")
    public Player playerId(@PathVariable Long id){
        Player player = playerService.findById(id);
        return player;
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody CreatePlayerRequest request){
        String nickname = request.getNickname();
        Player player = playerService.createPlayer(nickname);
        return player;
    }

    @PutMapping("/players/{id}/level")
    public String updateLevel(
            @PathVariable Long id,
            @RequestBody UpdatePlayerLevelRequest request){
        Integer level = request.getLevel();
        boolean success = playerService.updateLevel(id,level);
        if(success){
            return "玩家等级修改成功";
        }
        return "玩家等级修改失败";
    }

    @DeleteMapping("/players/{id}")
    public String deletePlayer(@PathVariable Long id){
        boolean success = playerService.deletePlayer(id);
        if(success){ return "玩家删除成功";}
        return "玩家删除失败";
    }

}
