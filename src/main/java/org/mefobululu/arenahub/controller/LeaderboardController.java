package org.mefobululu.arenahub.controller;

import org.mefobululu.arenahub.dto.AddScoreRequest;
import org.mefobululu.arenahub.dto.LeaderboardEntry;
import org.mefobululu.arenahub.dto.PlayerRankResponse;
import org.mefobululu.arenahub.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaderboardController {
    private final PlayerService playerService;

    public LeaderboardController(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping("/leaderboard/players/{id}/score")
    public Double addScore(
            @PathVariable Long id,
            @RequestBody AddScoreRequest request){
        Double deltaScore = request.getDeltaScore();
        return playerService.addScore(id,deltaScore);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard(
            @RequestParam int limit){
        return playerService.getLeaderboard(limit);
    }

    @GetMapping("/leaderboard/players/{id}")
    public ResponseEntity<PlayerRankResponse> getPlayerRank(
            @PathVariable Long id){
        PlayerRankResponse response = playerService.getPlayerRankInfo(id);
        if(response == null)
            return ResponseEntity.notFound().build() ;
        return ResponseEntity.ok(response);
    }
}
