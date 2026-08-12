package org.mefobululu.arenahub.service;

import org.mefobululu.arenahub.dto.LeaderboardEntry;
import org.mefobululu.arenahub.dto.PlayerRankResponse;
import org.mefobululu.arenahub.mapper.PlayerMapper;
import org.mefobululu.arenahub.model.Player;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            stringRedisTemplate.opsForZSet().remove("leaderboard:global",String.valueOf(id));
        }
        return rows == 1;
    }

    public Double addScore(Long id, Double deltaScore){
        Player player = findById(id);
        if(player == null) {
            return null;
        }
        return stringRedisTemplate.opsForZSet().incrementScore(
                "leaderboard:global",
                String.valueOf(id),
                deltaScore
        );
    }

    public Set<ZSetOperations.TypedTuple<String>> getTopPlayers(int limit){
        return stringRedisTemplate
                .opsForZSet()
                .reverseRangeWithScores(
                        "leaderboard:global",
                        0,
                        limit-1
                );
    }

    public List<LeaderboardEntry> getLeaderboard(int limit){
        if(limit <= 0){
            return new ArrayList<>();
        }
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().reverseRangeWithScores(
                "leaderboard:global",
                0,
                -1
        );
        List<LeaderboardEntry> result = new ArrayList<>();

        int rank = 1;
        for(ZSetOperations.TypedTuple<String> tuple : tuples){
            String playerIdString = tuple.getValue();
            Double score = tuple.getScore();
            Long playerId = Long.valueOf(playerIdString);
            Player player = findById(playerId);

            if(player == null){
                stringRedisTemplate.opsForZSet().remove(
                        "leaderboard:global",
                        playerIdString
                );
                continue;
            }
            LeaderboardEntry entry = new LeaderboardEntry(
                    rank,
                    playerId,
                    player.getNickname(),
                    score
            );
            result.add(entry);
            rank++;
            if(result.size() == limit){
                break;
            }
        }
        return result;
    }

    public Long getPlayerRank(Long id){
        Long rank = stringRedisTemplate
                .opsForZSet()
                .reverseRank(
                        "leaderboard:global",
                        String.valueOf(id)
                );
        if(rank == null){
            return null;
        }
            return rank+1;
    }

    public PlayerRankResponse getPlayerRankInfo(Long id){
        Player player = findById(id);

        if(player == null){
            return null;
        }
        Long rank = getPlayerRank(id);
        Double score = stringRedisTemplate
                .opsForZSet()
                .score(
                        "leaderboard:global",
                        String.valueOf(id)
                );
        PlayerRankResponse response = new PlayerRankResponse(
                id,
                player.getNickname(),
                rank,
                score
        );
        return response;
    }
}
