package com.drexel.cs.checkers451.model;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public class Room extends ChangeDetectable {

    private final String code;
    private Game game;

    private final User owner;
    private final List<User> players = new ArrayList<>();
    private final List<User> observers = new ArrayList<>();
    private final List<Game> history = new ArrayList<>();

    public Room(String code, User owner) {
        this.code = code;
        this.owner = owner;
    }

    public String getCode() {
        return code;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<User> getPlayers() {
        return players;
    }

    public List<User> getObservers() {
        return observers;
    }

    public List<Game> getHistory() {
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

    public synchronized void setActiveGame(Game game){
        if(this.game != null){
            this.history.add(this.game);
        }
        this.game = game;
        game.callbackForChange((version, updatedGame) -> {
            this.triggerChange();
        });
        this.triggerChange();
    }

}
