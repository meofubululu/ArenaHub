package org.mefobululu.arenahub.controller;

import org.mefobululu.arenahub.model.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return "Hello, "+name;
    }
    @GetMapping("/player/{id}")
    public String playerId(@PathVariable Long id){
        return "Player id: "+id;
    }
    @GetMapping("/player/demo")
    public Player demoPlayer(){
        Player player = new Player(1001L,"吃葡萄不吐葡萄皮",2);
        return player;
    }

}
