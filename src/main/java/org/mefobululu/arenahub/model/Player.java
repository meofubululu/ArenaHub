package org.mefobululu.arenahub.model;

public class Player {
    private Long id;
    private String nickname;
    private Integer level;

    public Player(Long id,String nickname,Integer level) {
        this.id = id;
        this.nickname = nickname;
        this.level = level;
    }

    public Long getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public Integer getLevel() {
        return level;
    }
}
