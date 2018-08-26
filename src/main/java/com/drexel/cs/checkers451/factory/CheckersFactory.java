package com.drexel.cs.checkers451.factory;

import com.drexel.cs.checkers451.model.Board;
import com.drexel.cs.checkers451.model.Chip;
import com.drexel.cs.checkers451.model.Game;
import org.springframework.stereotype.Component;

@Component
public class CheckersFactory {

    public Game makeCheckers() {
        Game ret = new Game();
        ret.board = this.makeBoard();
        return ret;
    }

    public Board makeBoard() {
        Board board = new Board();
        board.board = new Chip[8][8];
        return board;
    }
}
