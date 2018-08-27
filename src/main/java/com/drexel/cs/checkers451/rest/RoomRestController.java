package com.drexel.cs.checkers451.rest;

import com.drexel.cs.checkers451.model.Move;
import com.drexel.cs.checkers451.model.Room;
import com.drexel.cs.checkers451.model.User;
import com.drexel.cs.checkers451.model.UserMode;
import com.drexel.cs.checkers451.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = {"localhost:4200", "http://localhost:8080", "http://localhost:4200", "https://www.checkers451.com", "https://checkers451.com"}, allowCredentials = "true")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @GetMapping(path = "/demo")
    public Object continuousDeliveryTest(HttpServletRequest request, HttpServletResponse resp) throws Exception {
        return roomService.getActiveRooms();
    }


    @PostMapping(path = "/rest/new-room")
    public Room newRoom(@SessionAttribute("user") User user){
        return roomService.newRoom(user);
    }

    @PostMapping(path = "/rest/join-room")
    public Room joinRoom(@SessionAttribute("user") User user,
                         @RequestParam("code") String code,
                         @RequestParam("as") UserMode as) {

        if (as == UserMode.PLAYER) {
            return this.roomService.joinRoomAsPlayer(code, user);
        } else if (as == UserMode.OBSERVER) {
            return this.roomService.joinRoomAsObserver(code, user);
        } else {
            throw new RuntimeException("Invalid room parameters.");
        }
    }

    @GetMapping(path = "/rest/update-room")
    public Room updateRoom(@RequestParam("code") String code, @RequestParam("version") int version) throws Exception{
        return this.roomService.getRoomUpdates(code, version);
    }

    @PostMapping(path = "/rest/new-game")
    public Room startNewGame(@SessionAttribute("user") User user,
                             @RequestParam("code") String code){
       return this.roomService.startNewGame(code, user);
    }

    @PostMapping(path = "/rest/game-move")
    public Room doGameMove(@SessionAttribute("user") User user,
                           @RequestParam("code") String code,
                           @RequestBody Move move){
        return this.roomService.doGameMove(code, move, user);
    }
}
