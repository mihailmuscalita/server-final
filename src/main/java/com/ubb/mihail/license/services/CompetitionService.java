package com.ubb.mihail.license.services;

import com.ubb.mihail.license.converter.ConvertData;
import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.domain.Steps;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.CompetitionDTO;
import com.ubb.mihail.license.model.UserModel;
import com.ubb.mihail.license.model.UserSteps;
import com.ubb.mihail.license.repository.CompetitionsRepository;
import com.ubb.mihail.license.repository.StepsRepository;
import com.ubb.mihail.license.repository.UserRepository;
import com.ubb.mihail.license.services.servicesint.EmailService;
import com.ubb.mihail.license.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

@Service
public class CompetitionService {

    private final CompetitionsRepository competitionsRepository;
    private final UserRepository userRepository;
    private final StepsRepository stepsRepository;

    private final EmailService emailService;

    @Autowired
    public CompetitionService(CompetitionsRepository competitionsRepository,UserRepository userRepository,
                              StepsRepository stepsRepository, EmailService emailService) {
        this.competitionsRepository = competitionsRepository;
        this.userRepository=userRepository;
        this.stepsRepository=stepsRepository;
        this.emailService = emailService;
    }


    public List<Competition> findCompetitionsUsingAdminName(){
        return this.competitionsRepository.findAll();
    }

    public Competition addCompetition(CompetitionDTO competitionDTO,String userTokenName){
        User user = this.userRepository.findUserByName(userTokenName);
        Competition competition = ConvertData.convertCompetitionDTOtoCompetition(competitionDTO,user);
        return this.competitionsRepository.save(competition);
    }

    public CompetitionDTO getActiveCompetition(String userName){
        CompetitionDTO competitionDTO;
        Competition competition = this.competitionsRepository.getActiveCompetition();
        if (competition!=null){
            competitionDTO = ConvertData.convertCompetitionToCompetitionDTO(competition);
            User user = this.userRepository.findUserByName(userName);
            Optional<Steps> steps = this.stepsRepository.findUserIsRegistered(user.getUserId());
            if (steps.isPresent()) {
                Utils.setStateForCompetition(competitionDTO, steps.get());
            }
            else{
                Utils.setStateForCompetition(competitionDTO,null);
            }
            return competitionDTO;
        }
        return null;
    }

    public List<UserSteps> countStepsForEachUser(){
        List<UserSteps> arrayList = new ArrayList<>();
        List<Steps> steps = this.stepsRepository.findAll();
        if (steps.size() != 0){
           fillUserSteps(arrayList,steps);
            arrayList.sort(Comparator.comparingInt(UserSteps::getSteps).reversed());
        }
        return arrayList;
    }

//    public void stopCompetition(){
//        this.stepsRepository.deleteAll();
//        Competition competition = this.competitionsRepository.getActiveCompetition();
//        if (competition != null){
//            this.competitionsRepository.deleteById(competition.getIdCompetition());
//        }
//    }

    @Transactional
    public boolean stopCompetition(){
        try{
            this.competitionsRepository.stopCompetition();
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean sendMailsToUsers(){
        try{
            List<UserSteps> users = countStepsForEachUser();
            for( int i =0; i< users.size(); i++){
                UserModel userModel = users.get(i).getUser();
                if (userModel.getEmail() != null){
                    sendMailToUser(userModel , users.get(i).getSteps() , i);
                }
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    private void sendMailToUser(UserModel userModel, Integer steps , int positionInCompetition){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userModel.getEmail());
        message.setSubject("Step application ! See your status !");
        message.setText(Utils.formatMessageForUsers(userModel,steps,positionInCompetition));
        emailService.sendEmail(message);
    }

    @Transactional
    public CompetitionDTO startCompetition(String competitionId){
        Competition checkCompetition = this.competitionsRepository.getActiveCompetition();
        this.competitionsRepository.startCompetition(competitionId);
        Competition competition = this.competitionsRepository.findById(Integer.parseInt(competitionId)).get();
        if (checkCompetition != null) {
            return null;
        }
        return ConvertData.convertCompetitionToCompetitionDTO(competition);
    }

    private void fillUserSteps(List<UserSteps> shouldBeFilled , List<Steps> steps){
        if (steps.size() != 0){
            for (Steps firstBlock : steps){
                if (!findUserId(firstBlock.getAdmin().getUserId(),shouldBeFilled)){
                    int count = 0;
                    for (Steps secondBlock : steps){
                        if (secondBlock.getAdmin().getUserId() == firstBlock.getAdmin().getUserId()){
                            count+=secondBlock.getNumberOfSteps().intValue();
                        }
                    }
                    shouldBeFilled.add(new UserSteps(ConvertData.convertUserToUserModelWithoutId(firstBlock.getAdmin()),count));
                }
            }
        }
    }

    private boolean findUserId(Integer userId, List<UserSteps> userSteps){
        for (UserSteps data : userSteps){
            if (data.getUser().getId() == userId){
                return true;
            }
        }
        return false;
    }

}
