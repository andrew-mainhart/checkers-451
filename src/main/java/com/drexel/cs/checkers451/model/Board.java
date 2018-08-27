package com.drexel.cs.checkers451.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int size;
    private Chip[][] board;
    private List<Chip> chipsOutOfPlay = new ArrayList<>();

    public Board(int size){
        this.board = new Chip[size][size];
        this.size = size;
    }

    public Chip getChipAt(Coord coord) {
        if (board.length > coord.x) {
            if (board[coord.x].length > coord.y) {
                return board[coord.x][coord.y];
            }
        }
        throw new RuntimeException("Invalid coordinates.");
    }

    public void setChipAt(Coord coord, Chip chip) {
        if (board.length > coord.x) {
            if (board[coord.x].length > coord.y) {
                board[coord.x][coord.y] = chip;
                return;
            }
        }
        throw new RuntimeException("Invalid coordinates.");
    }

    public boolean isSpotEmpty(Coord coord) {
        return getChipAt(coord) == null;
    }

    public boolean isChipAllowedAtSpot(Coord coord) {
        if (coord.x % 2 == 0) {
            return coord.y % 2 == 0;
        } else {
            return coord.y % 2 != 0;
        }
    }

    public void removeChipAt(Coord coord){
        Chip chip = getChipAt(coord);
        if(chip == null){
            throw new RuntimeException("Chip being removed doesn't exist.");
        }
        chipsOutOfPlay.add(chip);
        setChipAt(coord, null);
    }

    public List<Chip> getChipsOutOfPlay() {
        return chipsOutOfPlay;
    }

    public Chip[][] getBoard() {
        return board;
    }
}
