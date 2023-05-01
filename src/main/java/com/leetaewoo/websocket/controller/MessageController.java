package com.leetaewoo.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@ServerEndpoint("/websocket")
public class MessageController {
    private static final List<Session> session = new ArrayList<>();

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @OnOpen
    public void open(Session newSession) {
        session.add(newSession);
        System.out.println("=========================================================");
        System.out.println("connected : OK , newSession id : " + newSession.getId());
        System.out.println("현재 참가자 수 : " + session.size());
        System.out.println("=========================================================");
    }

    @OnMessage
    public void getMsg(Session paramSession, String msg) {
        int i = 0;
        String sender = "";
        for (Session s : session) {
            if (paramSession.getId().equals(s.getId())) {
                sender = "나";
            } else {
                sender = "상대";
            }
            try {
                session.get(i).getBasicRemote().sendText(sender + " : " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}

