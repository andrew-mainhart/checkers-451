package com.drexel.cs.checkers451.rest;

import com.drexel.cs.checkers451.model.Game;
import com.drexel.cs.checkers451.model.Room;
import com.drexel.cs.checkers451.model.User;
import com.drexel.cs.checkers451.model.UserMode;
import com.drexel.cs.checkers451.service.CheckersService;
import com.drexel.cs.checkers451.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
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

    public Room startNewGame(@SessionAttribute("user") User user,
                             @RequestParam("code") String code){
       return this.roomService.startNewGame(code, user);
    }
}
