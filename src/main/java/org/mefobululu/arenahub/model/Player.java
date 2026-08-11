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
    public Player(){}

    public Long getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public Integer getLevel() {
        return level;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public void setLevel(Integer level){
        this.level = level;
    }
}
