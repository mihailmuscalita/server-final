package com.ubb.mihail.license.restcontrollers;

import com.ubb.mihail.license.domain.Friends;
import com.ubb.mihail.license.model.Friend;
import com.ubb.mihail.license.model.Message;
import com.ubb.mihail.license.services.FriendsService;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class FriendsController {
    @Autowired
    FriendsService friendsService;


    @PostMapping({"/friends"})
    public ResponseEntity<?> getFriends(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        try {
            List<String> names = this.friendsService.getFriends(userTokenName);
            if (names != null) {
                List<Friend> friends = new ArrayList<>();
                names.forEach(x->{friends.add(new Friend(x));});
                return new ResponseEntity<>(friends, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping({"/friends/{name}"})
    public ResponseEntity<?> getStepsForMyFriend(@PathVariable String name){
        try{
            Integer totalSteps = this.friendsService.getStepsForMyFriend(name);
            return new ResponseEntity<>(totalSteps,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
        }
    }

    @PostMapping({"/search"})
    public ResponseEntity<?> getNewFriends(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        try {
            List<String> names = this.friendsService.searchForNewFriends(userTokenName);
            List<Friend> friends = new ArrayList<>();
            names.forEach(x->{friends.add(new Friend(x));});
            return new ResponseEntity<>(friends, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping({"/friend/{friendName}"})
    public ResponseEntity<?> getFriend(@PathVariable String friendName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        try{

            String newFriend = this.friendsService.findNewFriend(userTokenName,friendName);
            return new ResponseEntity<>(newFriend,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.OK);
        }
    }

}
