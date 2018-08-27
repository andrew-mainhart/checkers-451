package com.drexel.cs.checkers451.service;

import com.drexel.cs.checkers451.model.CheckersGame;
import com.drexel.cs.checkers451.model.Move;
import com.drexel.cs.checkers451.model.Room;
import com.drexel.cs.checkers451.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@PropertySource("classpath:application.properties")
public class RoomService {

    @Value("#{'${checkers.roomnames.words}'.split(' ')}")
    private List<String> roomNameWordList;

    @Autowired
    private CheckersService checkersService;

    private final Map<String, Room> activeRooms = new HashMap<>();

    public Map<String, Room> getActiveRooms() {
        return activeRooms;
    }

    public Room newRoom(User playerOne) {
        synchronized (this.activeRooms) {
            Room ret = new Room(this.findNewRoomCode(), playerOne);
            ret.getPlayers().add(playerOne);
            this.activeRooms.put(ret.getCode(), ret);
            return ret;
        }
    }

    public Room joinRoomAsPlayer(String code, User player){
        Room r = this.getRoomByCode(code);
        r.addPlayer(player);
        return r;
    }

    public Room joinRoomAsObserver(String code, User observer){
        Room r = this.getRoomByCode(code);
        r.addObserver(observer);
        return r;
    }

    public Room startNewGame(String code, User authorizingUser){
        Room r = this.getRoomByCode(code);
        if(r.getOwner().equals(authorizingUser)) {
            CheckersGame g = this.checkersService.newGame(r.getPlayers().get(0), r.getPlayers().get(1));
            r.setActiveGame(g);
        } else {
            throw new RuntimeException("Current user is not room owner.");
        }
        return r;
    }

    public Room getRoomUpdates(String code, int currentVersion) throws Exception{
        Room r = this.getRoomByCode(code);
        r.blockForChange(currentVersion);
        return r;
    }

    public Room doGameMove(String code, Move m, User playerMoving){
        m.setByPlayer(playerMoving);
        Room r = getRoomByCode(code);
        if(r.getPlayers().contains(playerMoving)){
            if (playerMoving == r.getCheckersGame().getTurnUser()){
                r.getCheckersGame().doMove(m);
                r.getCheckersGame().alternateTurnUser();
                return r;
            } else{
                throw new RuntimeException("It is not turn of user submitting move.");
            }
        } else {
            throw new RuntimeException("Current user is not player in room.");
        }
    }

    public Room getRoomByCode(String code){
        Room room = this.activeRooms.get(code);
        if(room == null){
            throw new RuntimeException("Room no exist");
        } else {
            return room;
        }
    }

    private String findNewRoomCode() {
        int maxIndex = roomNameWordList.size() - 1;
        Random r = new Random();
        int word1number = r.nextInt(maxIndex);
        int word2number = r.nextInt(maxIndex);

        String ret = roomNameWordList.get(word1number) + "-" + roomNameWordList.get(word2number);
        if (activeRooms.containsKey(ret)) {
            return findNewRoomCode();
        } else {
            return ret;
        }
    }

}
