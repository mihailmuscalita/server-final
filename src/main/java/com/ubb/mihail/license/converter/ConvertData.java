package com.ubb.mihail.license.converter;

import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.domain.FriendRequest;
import com.ubb.mihail.license.domain.Steps;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.CompetitionDTO;
import com.ubb.mihail.license.model.FriendRequestModel;
import com.ubb.mihail.license.model.RegisterCompetitionModel;
import com.ubb.mihail.license.model.UserModel;

import java.time.LocalDate;

public class ConvertData {

    public static UserModel convertUserToUserModel(User user){
        if(user == null) return null;
        UserModel userModel=new UserModel(user.getUserId(),user.getUserName(),user.getName(),user.getUserRole());
        return userModel;
    }

    public static UserModel convertUserToUserModelWithoutId(User user){
        if(user == null) return null;
        UserModel userModel=new UserModel();
        userModel.setUserName(user.getUserName());
        userModel.setName(user.getName());
        userModel.setUserRole(user.getUserRole());
        userModel.setEmail(user.getEmail());
        return userModel;
    }

    public static Competition convertCompetitionDTOtoCompetition(CompetitionDTO competitionDTO,User admin){
        Competition competition = new Competition();
        competition.setCompetitionTitle(competitionDTO.getCompetitionTitle());
        competition.setCompetitionReward(competitionDTO.getCompetitionReward());
        competition.setCompetitionStatus(0);
        competition.setAdmin(admin);
        return competition;
    }

    public static CompetitionDTO convertCompetitionToCompetitionDTO(Competition competition){
        CompetitionDTO competitionDTO = new CompetitionDTO(competition.getIdCompetition(),competition.getCompetitionTitle(),competition.getCompetitionReward());
        return competitionDTO;
    }

    public static Steps buildSteps(User user, Competition competition){
        Steps steps = new Steps();
        steps.setCompetition(competition);
        steps.setAdmin(user);
        steps.setDate(LocalDate.now().toString());
        return steps;
    }

    public static FriendRequestModel convertFriendsRequestToFriendsRequestModel(FriendRequest friendsRequest){
        FriendRequestModel friendsRequestModel = new FriendRequestModel();
        friendsRequestModel.setFriendsRequest(friendsRequest.getSendByUser());
        return friendsRequestModel;
    }

}
