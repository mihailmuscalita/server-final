package com.ubb.mihail.license.restcontrollers.websockets;

import com.ubb.mihail.license.websocketmodel.SendRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

public class WebSocketHelper {


    public WebSocketHelper(){}


    public void notifySendRequest(Map<WebSocketSession,String> session , String userName, String sendBy) throws IOException {
        for (Map.Entry<WebSocketSession,String> entry : session.entrySet()){
            if (entry.getValue().equals(userName)){
               if (entry.getKey().isOpen()){
                   entry.getKey().sendMessage(new TextMessage( new SendRequest("send",sendBy).toString()));
               }
            }
        }
    }

    public void notifyAcceptRequest(Map<WebSocketSession,String> session , String acceptedBy, String toUser) throws IOException {
        for (Map.Entry<WebSocketSession,String> entry : session.entrySet()){
            if (entry.getValue().equals(toUser)){
                if (entry.getKey().isOpen()){
                    entry.getKey().sendMessage(new TextMessage( new SendRequest("accept",acceptedBy).toString()));
                }
            }
        }
    }

    public void notifyCompetitionStarted(Map<WebSocketSession,String> session , String competition) throws IOException {
        for (WebSocketSession webSocketSession : session.keySet() ){
            if (webSocketSession.isOpen()){
                webSocketSession.sendMessage(new TextMessage(competition));
            }
        }
    }


}
