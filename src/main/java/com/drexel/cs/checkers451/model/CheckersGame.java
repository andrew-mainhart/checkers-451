package com.drexel.cs.checkers451.model;

import com.drexel.cs.checkers451.changes.ChangeDetectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckersGame extends ChangeDetectable {

    public String id;
    public List<Move> moves = new ArrayList<>();
    private final Board board;
    private final User player1;
    private final User player2;
    private User turnUser;

    public CheckersGame(Board board, User player1, User player2){
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.turnUser = player1;
    }

    public boolean doMove(Move move){
        Chip chipToMove = board.getChipAt(move.getFromSpot());
        if(chipToMove == null){
            throw new RuntimeException("No chip at chip to move spot.");
        }

        if(!chipToMove.getPlayer().equals(move.getByPlayer())){
            throw new RuntimeException("User making move must own chip to move.");
        }

        if(!board.isSpotEmpty(move.getToSpot())){
            throw new RuntimeException("Destination spot is not empty.");
        }

        List<Coord> chipsToEat = new ArrayList<>();
        Coord current = move.getFromSpot();
        for(int i = 0; i < move.getIntermediateSpots().size(); i++){
            Coord tmp = checkSingleMove(current, move.getIntermediateSpots().get(i), chipToMove);
            current = move.getIntermediateSpots().get(i);
            if(tmp != null){
                chipsToEat.add(tmp);
            }
        }

        Coord tmp = checkSingleMove(current, move.getToSpot(), chipToMove);
        if(tmp != null){
            chipsToEat.add(tmp);
        }

        //Made it here! Commit the move.
        for(Coord coord : chipsToEat){
            board.removeChipAt(coord);
        }
        board.setChipAt(move.getFromSpot(), null);
        board.setChipAt(move.getToSpot(), chipToMove);
        if(move.getToSpot().y == 0 || move.getToSpot().y == board.getSize() - 1){
            if(chipToMove.isBlack()){
                chipToMove.setType(ChipType.black_king);
            }

            if(chipToMove.isRed()){
                chipToMove.setType(ChipType.red_king);
            }
        }
        this.moves.add(move);
        alternateTurnUser();
        triggerChange();
        return true;
    }

    private Coord checkSingleMove(Coord current, Coord next, Chip chip){
        int xdiff = next.x - current.x;
        int ydiff = next.y - current.y;

        if(chip.getType().equals(ChipType.red_single) && ydiff >= 0){
            throw new RuntimeException("Red single can only move up.");
        }

        if(chip.getType().equals(ChipType.black_single) && ydiff <= 0){
            throw new RuntimeException("Black single can only move down.");
        }

        if(Math.abs(xdiff) != Math.abs(ydiff)){
            throw new RuntimeException("Checkers move must be same x and y delta");
        }

        if(!board.isSpotEmpty(next)){
            throw new RuntimeException("Can't place chip on occupied spot.");
        }

        if(Math.abs(xdiff) > 2){
            throw new RuntimeException("Can't move more than two spaces in one hop.");
        }

        if(Math.abs(xdiff) == 2){
            int tmpx = xdiff / 2;
            int tmpy = ydiff / 2;
            Coord spotBeingHoppedOver = new Coord(current.x + tmpx, current.y + tmpy);

            if(board.isSpotEmpty(spotBeingHoppedOver)){
                throw new RuntimeException("You can't hop over an empty spot.");
            }

            Chip chipBeingHoppedOver = board.getChipAt(spotBeingHoppedOver);
            if(chipBeingHoppedOver.isBlack() == chip.isBlack()){
                throw new RuntimeException("Can't hop over chip of own color.");
            }

            return spotBeingHoppedOver;
        }
        return null;
    }
    public User getTurnUser(){
        return this.turnUser;
    }

    public void alternateTurnUser(){
        if (this.turnUser == this.player1){
            turnUser = this.player2;
        } else{
            this.turnUser = this.player1;
        }
    }
    public int getUserScore(User user){
        ChipType sc1;
        ChipType sc2;
        int score = 0;
        if (user != this.player1){
            sc1 = ChipType.red_king;
            sc2 = ChipType.red_single;
        } else{
            sc1 = ChipType.black_king;
            sc2 = ChipType.black_single;
        }
        List<Chip> chips = this.board.getChipsOutOfPlay();

        for(Chip c: chips){
            if (c.getType() == sc1 || c.getType() == sc2)
                score++;
        }
        return score;
    }
    public Board getBoard() {
        return board;
    }

    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public int getPlayerOneScore(){
        return getUserScore(this.player1);
    }
    public int getPlayerTwoScore(){
        return getUserScore(this.player2);
    }
}
