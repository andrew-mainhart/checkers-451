package com.drexel.cs.checkers451.model;

import java.util.ArrayList;
import java.util.List;

public class Game extends ChangeDetectable {

    public String id;
    public List<User> players = new ArrayList<>();
    public List<Move> moves = new ArrayList<>();
    public Board board = new Board();
}
