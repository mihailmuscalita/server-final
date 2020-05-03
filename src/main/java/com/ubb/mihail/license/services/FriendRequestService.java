package com.ubb.mihail.license.services;

import com.ubb.mihail.license.converter.ConvertData;
import com.ubb.mihail.license.domain.FriendRequest;
import com.ubb.mihail.license.domain.Friends;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.FriendRequestModel;
import com.ubb.mihail.license.repository.FriendRequestRepository;
import com.ubb.mihail.license.repository.FriendsRepository;
import com.ubb.mihail.license.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FriendRequestService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendsRequestRepository;
    private final FriendsRepository friendsRepository;

    @Autowired
    public FriendRequestService(UserRepository userRepository, FriendRequestRepository friendsRequestRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.friendsRequestRepository = friendsRequestRepository;
        this.friendsRepository = friendsRepository;
    }


    public FriendRequest sendRequest(String sendByUser , String userName) throws Exception{
        User repoUser = this.userRepository.findUserByName(userName);
        Optional<FriendRequest> optionalFriendsRequest = this.friendsRequestRepository.existThisRequest(sendByUser,repoUser.getUserId());
        if (!optionalFriendsRequest.isPresent()){
            FriendRequest friendsRequest = new FriendRequest();
            friendsRequest.setSendByUser(sendByUser);
            friendsRequest.setUser(repoUser);
            this.friendsRequestRepository.save(friendsRequest);
            FriendRequest requestModel = new FriendRequest();
            requestModel.setSendByUser(sendByUser);
            return requestModel;
        }
        throw new Exception("They are already friends!");
    }

    @Transactional
    public boolean acceptRequest(String currentNameUser, String user) throws Exception{
        User firstUser = this.userRepository.findUserByName(currentNameUser);
        User secondUser = this.userRepository.findUserByName(user);
        Optional<Friends> optionalFriends = this.friendsRepository.checkFriends(firstUser.getUserId(),secondUser.getUserId());
        if (!optionalFriends.isPresent()) {
            if (firstUser != null && secondUser != null) {
                Friends friends = new Friends();
                friends.setFirstUser(firstUser);
                friends.setSecondUser(secondUser);
                this.friendsRepository.save(friends);
                this.friendsRequestRepository.deleteRequest(user, firstUser.getUserId());
                return true;
            }
        }
        else{
            return false;
        }
        throw new Exception("A user was deleted!");
    }

    @Transactional
    public boolean cancelRequest(String currentNameUser, String user) throws Exception {
        User repoUser = this.userRepository.findUserByName(currentNameUser);
        User sendBy = this.userRepository.findUserByName(user);
        if (repoUser!=null && sendBy!=null){
            this.friendsRequestRepository.deleteRequest(user,repoUser.getUserId());
            return true;
        }
        throw new Exception("A user was deleted!");
    }

    public List<FriendRequestModel> getAllFriendsRequest(String name) throws Exception {
        List<FriendRequestModel> friendsRequestModels = new ArrayList<>();
        User repoUser = this.userRepository.findUserByName(name);
        Optional<List<FriendRequest>> optionalFriendsRequest = this.friendsRequestRepository.getRequestByAUser(repoUser.getUserId());
        if (optionalFriendsRequest.isPresent()){
            List<FriendRequest> friendsRequests = optionalFriendsRequest.get();
            friendsRequests.forEach(x->{friendsRequestModels.add(ConvertData.convertFriendsRequestToFriendsRequestModel(x));});
        }
        return friendsRequestModels;
    }
}
