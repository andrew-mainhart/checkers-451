package com.drexel.cs.checkers451.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RoomRestController {

    @GetMapping(path = "/demo")
    public void continuousDeliveryTest(HttpServletRequest request, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html");
        resp.getWriter().print("<h1>Api Test! It works!</h1>");
    }

}
