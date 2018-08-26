package com.drexel.cs.checkers451.service;

import com.drexel.cs.checkers451.factory.CheckersFactory;
import com.drexel.cs.checkers451.model.Game;
import com.drexel.cs.checkers451.model.User;
import org.springframework.stereotype.Service;

@Service
public class CheckersService {

    private CheckersFactory checkersFactory;

    public CheckersService(CheckersFactory checkersFactory){
        this.checkersFactory = checkersFactory;
    }

    public Game newGame(User u1, User u2){
        Game ret = this.checkersFactory.makeCheckers();
        ret.players.add(u1);
        ret.players.add(u2);
        return ret;
    }

}
