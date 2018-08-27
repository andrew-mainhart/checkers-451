package com.drexel.cs.checkers451.model;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private Chip chip;
    private Coord fromSpot;
    private Coord toSpot;
    private List<Coord> intermediateSpots = new ArrayList<>();
    private User byPlayer;

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public Coord getFromSpot() {
        return fromSpot;
    }

    public void setFromSpot(Coord fromSpot) {
        this.fromSpot = fromSpot;
    }

    public Coord getToSpot() {
        return toSpot;
    }

    public void setToSpot(Coord toSpot) {
        this.toSpot = toSpot;
    }

    public User getByPlayer() {
        return byPlayer;
    }

    public void setByPlayer(User byPlayer) {
        this.byPlayer = byPlayer;
    }

    public List<Coord> getIntermediateSpots() {
        return intermediateSpots;
    }
}
