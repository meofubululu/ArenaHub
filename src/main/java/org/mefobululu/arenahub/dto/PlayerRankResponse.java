package org.mefobululu.arenahub.dto;

public class PlayerRankResponse {
    private Long playerId;
    private String nickname;
    private Long rank;
    private Double score;

    public PlayerRankResponse(
            Long playerId,
            String nickname,
            Long rank,
            Double score
    ){
        this.playerId = playerId;
        this.nickname = nickname;
        this.rank = rank;
        this.score = score;
    }
    public Long getPlayerId(){ return playerId;}
    public String getNickname(){ return nickname;}
    public Long getRank(){ return rank;}
    public Double getScore(){ return score;}
}
