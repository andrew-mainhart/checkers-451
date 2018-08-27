package com.drexel.cs.checkers451.model;

public class Chip {

    private ChipType type;
    private int id;
    private User player;

    public Chip(ChipType type, User player){
        this.type = type;
        this.player = player;
    }

    public ChipType getType() {
        return type;
    }

    public void setType(ChipType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public boolean isBlack(){
        return this.getType().equals(ChipType.black_king) || this.getType().equals(ChipType.black_single);
    }

    public boolean isRed(){
        return !this.isBlack();
    }
}
