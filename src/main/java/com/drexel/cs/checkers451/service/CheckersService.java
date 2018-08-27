package com.drexel.cs.checkers451.service;

import com.drexel.cs.checkers451.factory.CheckersFactory;
import com.drexel.cs.checkers451.model.CheckersGame;
import com.drexel.cs.checkers451.model.User;
import org.springframework.stereotype.Service;

@Service
public class CheckersService {

    private CheckersFactory checkersFactory;

    public CheckersService(CheckersFactory checkersFactory){
        this.checkersFactory = checkersFactory;
    }

    public CheckersGame newGame(User u1, User u2){
        CheckersGame ret = this.checkersFactory.makeCheckers(u1, u2);
        return ret;
    }

}
