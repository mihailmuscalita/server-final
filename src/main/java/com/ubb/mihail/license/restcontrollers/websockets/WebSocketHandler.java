package com.ubb.mihail.license.restcontrollers.websockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ubb.mihail.license.domain.JwtRequest;
import com.ubb.mihail.license.services.CompetitionService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    //private final CompetitionService competitionService;
    private static Map<WebSocketSession,String> socketSession = new HashMap<>();
    private WebSocketHelper webSocketHelper;

    @Autowired
    public WebSocketHandler(CompetitionService competitionService) {
       // this.competitionService = competitionService;
        this.webSocketHelper = new WebSocketHelper();
    }

    public void notifyUsers(String competition) {
        try {
            this.webSocketHelper.notifyCompetitionStarted(socketSession, competition);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendRequest(String toUser, String sendBy){
        try {
            this.webSocketHelper.notifySendRequest(socketSession, toUser , sendBy);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void acceptRequest( String acceptedBy, String toUser ){
        try {
            this.webSocketHelper.notifyAcceptRequest(socketSession, acceptedBy , toUser);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String userTokenName = userDetails.getUsername();
        //System.out.println("S-a conectat la websocket pe server = " + userTokenName);
        socketSession.put(session,"");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        socketSession.remove(session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Se face handling la tokenul = " + message.getPayload());
        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, ?> tokenData = parser.parseMap(JwtHelper.decode(message.getPayload()).getClaims());
        String userName = (String) tokenData.get("sub");
        socketSession.put(session,userName);
    }

}
