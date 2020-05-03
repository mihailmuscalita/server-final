package com.ubb.mihail.license.restcontrollers;

import com.ubb.mihail.license.domain.FriendRequest;
import com.ubb.mihail.license.model.FriendRequestModel;
import com.ubb.mihail.license.model.Message;
import com.ubb.mihail.license.restcontrollers.websockets.WebSocketHandler;
import com.ubb.mihail.license.services.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class FriendRequestController {


    private final FriendRequestService friendsRequestService;
    private final WebSocketHandler webSocketHandler;

    @Autowired
    public FriendRequestController(FriendRequestService friendsRequestService, WebSocketHandler webSocketHandler) {
        this.friendsRequestService = friendsRequestService;
        this.webSocketHandler = webSocketHandler;
    }

    @PostMapping({"/friendRequest/{userName}"})
    public ResponseEntity<?> sendRequest(@PathVariable String userName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        FriendRequest friendsRequest;
        boolean ok = false;
        try {
            friendsRequest = this.friendsRequestService.sendRequest(userTokenName,userName);
            if (friendsRequest!=null){
                ok = true;
                this.webSocketHandler.sendRequest(userName,userTokenName);
            }
            return new ResponseEntity<>(ok,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"/friendRequestCanceled/{userName}"})
    public ResponseEntity<?> cancelRequest(@PathVariable String userName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        boolean ok = false;
        try {
            ok = this.friendsRequestService.cancelRequest(userTokenName,userName);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ok,HttpStatus.OK);
    }

    @PostMapping({"/friendRequestAccepted/{userName}"})
    public ResponseEntity<?> acceptRequest(@PathVariable String userName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        boolean ok = false;
        try {
            ok = this.friendsRequestService.acceptRequest(userTokenName,userName);
            if ( ok ){
                this.webSocketHandler.acceptRequest(userTokenName, userName);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ok,HttpStatus.OK);
    }

    @PostMapping({"/friendsRequest"})
    public ResponseEntity<?> getAllFriendsRequest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        try {
            List<FriendRequestModel> friendsRequestModels = this.friendsRequestService.getAllFriendsRequest(userTokenName);
            return new ResponseEntity<>(friendsRequestModels,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
