package com.example.demo;

import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketHandler extends TextWebSocketHandler {
    Map<Long, List<WebSocketSession>> sessions = new HashMap<>();
    Map<Long, Project> projects = new HashMap<>();


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        

        Long projID = Long.parseLong(session.getUri().getQuery().split("=")[1]);

        for (WebSocketSession webSocketSession : sessions.get(projID)) {
            Project value = new Gson().fromJson(message.getPayload(), Project.class);
            projects.put(projID, value);
            webSocketSession.sendMessage(new TextMessage(new Gson().toJson(projects.get(projID))));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.

        Long projID = Long.parseLong(session.getUri().getQuery().split("=")[1]);

        if (!sessions.containsKey(projID)) {
            sessions.put(projID, new ArrayList<>());
        }

        sessions.get(projID).add(session);

        if (!projects.containsKey(projID)) {
            Project project = new Project();
            project.setId(projID);
            projects.put(projID, project);
        }
        session.sendMessage(new TextMessage(new Gson().toJson(projects.get(projID))));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        List<WebSocketSession> sessionsToRm = new ArrayList<>();
        for (List<WebSocketSession> sess : sessions.values()) {
            for (WebSocketSession webSocketSession : sess) {
                sessionsToRm.add(webSocketSession);
            }
        }

        for (WebSocketSession webSocketSession : sessionsToRm) {
            for (List<WebSocketSession> sess : sessions.values()) {
                sess.remove(webSocketSession);
            }
        }
    }
}
