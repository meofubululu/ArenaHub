package org.mefobululu.arenahub.controller;

import org.mefobululu.arenahub.dto.CreatePlayerRequest;
import org.mefobululu.arenahub.model.Player;
import org.mefobululu.arenahub.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return "Hello, "+name;
    }

    @GetMapping("/player/{id}")
    public Player playerId(@PathVariable Long id){
        Player player = playerService.findById(id);
        return player;
    }

    @PostMapping("/players")
    public String createPlayer(@RequestBody CreatePlayerRequest request){
        String nickname = request.getNickname();
        return "昵称创建成功！您的昵称为： "+nickname;
    }



    private final PlayerService playerService;

    public HelloController(PlayerService playerService){
        this.playerService = playerService;
    }
}
