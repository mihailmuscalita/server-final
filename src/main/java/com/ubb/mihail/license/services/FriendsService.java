package com.ubb.mihail.license.services;

import com.ubb.mihail.license.domain.FriendRequest;
import com.ubb.mihail.license.domain.Friends;
import com.ubb.mihail.license.domain.Steps;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.UserModel;
import com.ubb.mihail.license.repository.FriendRequestRepository;
import com.ubb.mihail.license.repository.FriendsRepository;
import com.ubb.mihail.license.repository.StepsRepository;
import com.ubb.mihail.license.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendsService {

    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;
    private final StepsRepository stepsRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendsService(UserRepository userRepository, FriendsRepository friendsRepository, StepsRepository stepsRepository, FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.friendsRepository = friendsRepository;
        this.stepsRepository = stepsRepository;
        this.friendRequestRepository =friendRequestRepository;
    }

    public List<String> getFriends(String userName) throws Exception{
        User user = this.userRepository.findUserByName(userName);
        List<Friends> friends;
        List<String> names = new ArrayList<>();
        Optional<List<Friends>> repoUsers = this.friendsRepository.getFriends(((com.ubb.mihail.license.domain.User) user).getUserId());
        if (repoUsers.isPresent()){
            friends = repoUsers.get();
            names = getAllFriendsUsingAnId(user.getUserId(),friends);
        }
        return names;
    }

    public String findNewFriend(String currentUserName, String friend) throws Exception {
        Optional<Friends> friends;
        Optional<FriendRequest> existRequest;
        User currentUser = this.userRepository.findUserByName(currentUserName);
        User newFriend = this.userRepository.findUserByName(friend);
        if (newFriend == null){
            throw new Exception("This user does not exist !");
        }
        friends = this.friendsRepository.checkFriends(currentUser.getUserId(),newFriend.getUserId());
        if (!friends.isPresent()){
            existRequest = this.friendRequestRepository.existThisRequest(currentUserName, newFriend.getUserId());
            if (!existRequest.isPresent()){
                return newFriend.getUserName();
            }
        }
        return "A request is sending or you are already friends !";
    }

    public Integer getStepsForMyFriend(String userName) throws Exception{
        Integer totalSum;
        User user = this.userRepository.findUserByName(userName);
        Optional<List<Steps>> optionalSteps = this.stepsRepository.getStepsForFriend(user.getUserId());
        if (optionalSteps.isPresent()){
            System.out.println(optionalSteps.get());
            List<Steps> steps =  optionalSteps.get();
            totalSum = sumSteps(steps);
            return totalSum;
        }
        return -1;
    }


    public List<String> searchForNewFriends(String userName) throws Exception {
        User user = this.userRepository.findUserByName(userName);
        List<Friends> newFriends;
        List<String> names = new ArrayList<>();
        Optional<List<Friends>> repoUsers = this.friendsRepository.getFriends(user.getUserId());
        if (repoUsers.isPresent()){
            newFriends = repoUsers.get();
            names = getAllFriendsUsingAnId(user.getUserId(),newFriends);
            return getAllNewFriends(names,user.getUserName());
        }
        else{
            names = getAllFriendsName(userName);
        }
        return names;
    }

    private List<String> getAllFriendsUsingAnId(Integer id, List<Friends> repoUserList){
        List<String> names = new ArrayList<>();
        repoUserList.forEach(friends->{
            if (friends.getFirstUser().getUserId() != id){
                names.add(friends.getFirstUser().getUserName());
            }
            else{
                names.add(friends.getSecondUser().getUserName());
            }
        });
        return names;
    }

    private boolean findName(List<String> currentFriends, String name){
        for (String userName: currentFriends){
            if (userName.equals(name)){
                return true;
            }
        }
        return false;
    }

    private List<String> getAllNewFriends(List<String> currentFriends, String currentUserName){
        List<User> repoUserList = (List<User>) this.userRepository.findAll();
        List<String> possibleFriends = new ArrayList<>();
        for (User user : repoUserList){
            if (!findName(currentFriends,user.getUserName()) && !user.getUserName().equals(currentUserName) && !(user.getUserRole() == 2)){
                possibleFriends.add(user.getUserName());
            }
        }
        return possibleFriends;
    }

    private List<String> getAllFriendsName(String currentName){
        List<String> names = new ArrayList<>();
        List<User> repoUserList = (List<User>) this.userRepository.findAll();
        repoUserList.forEach(x->{
            if ( !x.getUserName().equals(currentName) && !(x.getUserRole() == 2)){
                names.add(x.getUserName());
            }
        });
        return names;
    }

    private Integer sumSteps(List<Steps> list){
        Long sum = 0L;
        for (Steps item : list){
            sum += item.getNumberOfSteps();
        }
        return sum.intValue();
    }


}
