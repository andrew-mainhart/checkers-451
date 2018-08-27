package com.drexel.cs.checkers451.factory;

import com.drexel.cs.checkers451.model.*;
import org.springframework.stereotype.Component;

@Component
public class CheckersFactory {

    public CheckersGame makeCheckers(User p1, User p2) {
        CheckersGame ret = new CheckersGame(makeBoard(p1, p2), p1, p2);
        return ret;
    }

    public Board makeBoard(User red, User black) {
        int size = 8;
        Board board = new Board(size);
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < size; x++) {
                Coord coord = new Coord(x, y);
                if(board.isChipAllowedAtSpot(coord)){
                    Chip chip = new Chip(ChipType.black_single, black);
                    board.setChipAt(coord, chip);
                }

                coord = new Coord(x, (size - (y + 1)));
                if(board.isChipAllowedAtSpot(coord)){
                    Chip chip = new Chip(ChipType.red_single, red);
                    board.setChipAt(coord, chip);
                }
            }
        }
        return board;
    }
}
