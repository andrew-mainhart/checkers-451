package com.drexel.cs.checkers451.model;

import com.drexel.cs.checkers451.changes.ChangeDetectable;

import java.util.ArrayList;
import java.util.List;

public class Room extends ChangeDetectable {

    private final String code;
    private CheckersGame checkersGame;

    private final User owner;
    private final List<User> players = new ArrayList<>();
    private final List<User> observers = new ArrayList<>();
    private final List<CheckersGame> history = new ArrayList<>();

    public Room(String code, User owner) {
        this.code = code;
        this.owner = owner;
    }

    public String getCode() {
        return code;
    }

    public CheckersGame getCheckersGame() {
        return checkersGame;
    }

    public void setCheckersGame(CheckersGame checkersGame) {
        this.checkersGame = checkersGame;
    }

    public List<User> getPlayers() {
        return players;
    }

    public List<User> getObservers() {
        return observers;
    }

    public List<CheckersGame> getHistory() {
        return history;
    }

    public User getOwner() {
        return owner;
    }

    public synchronized void addPlayer(User player){
        if(this.players.contains(player)){
            throw new RuntimeException("Player already joined to room.");
        } else {
            this.players.add(player);
            triggerChange();
        }
    }

    public synchronized void addObserver(User observer){
        if(this.observers.contains(observer)){
            throw new RuntimeException("Observer already joined to room.");
        } else {
            this.observers.add(observer);
            triggerChange();
        }
    }

    public synchronized void setActiveGame(CheckersGame checkersGame){
        if(this.checkersGame != null){
            this.history.add(this.checkersGame);
        }
        this.checkersGame = checkersGame;
        checkersGame.callbackForChange((version, updatedGame) -> {
            this.triggerChange();
        });
        this.triggerChange();
    }

}
