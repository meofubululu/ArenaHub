package org.mefobululu.arenahub.dto;

public class LeaderboardEntry {
    private Integer rank;
    private Long playerId;
    private String nickname;
    private Double score;

    public LeaderboardEntry(
            Integer rank,
            Long playerId,
            String nickname,
            Double score){
        this.rank = rank;
        this.playerId = playerId;
        this.nickname = nickname;
        this.score = score;
    }

    public Integer getRank(){ return rank;}
    public Long getPlayerId(){ return playerId; }
    public String getNickname(){ return nickname;}
    public Double getScore(){ return score;}
}
