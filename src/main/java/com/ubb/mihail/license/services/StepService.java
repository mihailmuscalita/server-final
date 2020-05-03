package com.ubb.mihail.license.services;

import com.ubb.mihail.license.converter.ConvertData;
import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.domain.Steps;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.RegisterCompetitionModel;
import com.ubb.mihail.license.repository.CompetitionsRepository;
import com.ubb.mihail.license.repository.StepsRepository;
import com.ubb.mihail.license.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class StepService {

    private final StepsRepository stepsRepository;
    private final CompetitionsRepository competitionsRepository;
    private final UserRepository userRepository;

    @Autowired
    public StepService(StepsRepository stepsRepository, CompetitionsRepository competitionsRepository, UserRepository userRepository) {
        this.stepsRepository = stepsRepository;
        this.competitionsRepository = competitionsRepository;
        this.userRepository = userRepository;
    }


    public RegisterCompetitionModel registerUserToCompetition(RegisterCompetitionModel registerCompetitionModel,String userTokenName){
        User user = this.userRepository.findUserByName(userTokenName);
        boolean existUser = this.stepsRepository.findUserIsRegistered(user.getUserId()).isPresent();
        if (!existUser){
            Steps steps = new Steps();
            Competition competition = this.competitionsRepository.findById(registerCompetitionModel.getCompetitionId()).get();
            steps.setNumberOfSteps(0L);
            steps.setAdmin(user);
            steps.setCompetition(competition);
            steps.setDate(LocalDate.now().toString());
            this.stepsRepository.save(steps);
            return registerCompetitionModel;
        }
        return null;
    }

    @Transactional
    public int updateStepsToUser(int steps, String userName){
        System.out.println("A intrat aici !");
        User user = this.userRepository.findUserByName(userName);
        Competition competition = this.competitionsRepository.getActiveCompetition();
        if (competition != null){
            System.out.println("A intrat si aici !");
            Optional<Steps> stepsOptional = this.stepsRepository.findSteps(user.getUserId());
            Steps currentStep;
            if (stepsOptional.isPresent()) {
                //this.stepsRepository.updateSteps(competition.getIdCompetition(), user.getUserId(), steps);
                currentStep = stepsOptional.get();
                if (currentStep.getDate().equals(LocalDate.now().toString())){
                    this.stepsRepository.updateSteps(competition.getIdCompetition(),user.getUserId(),steps,LocalDate.now().toString());
                    return steps;
                }
            }
            Steps dbSteps = ConvertData.buildSteps(user,competition);
            System.out.println(dbSteps);
            this.stepsRepository.save(dbSteps);
            return steps;
        }
        return -1;
    }

}
