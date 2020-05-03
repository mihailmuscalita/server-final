package com.ubb.mihail.license.restcontrollers;


import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.model.CompetitionDTO;
import com.ubb.mihail.license.model.UserSteps;
import com.ubb.mihail.license.restcontrollers.websockets.WebSocketHandler;
import com.ubb.mihail.license.services.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = {"*"}
)
@RequestMapping(value = "/license/competitions")
public class CompetitionRestController {

    private final CompetitionService competitionService;
    private final WebSocketHandler webSocketHandler;

    @Autowired
    public CompetitionRestController(CompetitionService competitionService,WebSocketHandler webSocketHandler){
        this.competitionService = competitionService;
        this.webSocketHandler = webSocketHandler;
    }

    @GetMapping()
    public ResponseEntity<List<Competition>> findCompetitionsForAnAdmin(){
        List<Competition> competitions = this.competitionService.findCompetitionsUsingAdminName();
        return new ResponseEntity<>(competitions, HttpStatus.OK);
    }

    @PostMapping(value = "/emails")
    public ResponseEntity<?> sendMails(){
        boolean ok = this.competitionService.sendMailsToUsers();
        return new ResponseEntity<>(ok,HttpStatus.OK);
    }

    @PostMapping(value = "/active")
    public ResponseEntity<?> getActiveCompetition(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        CompetitionDTO competitionDTO = this.competitionService.getActiveCompetition(userTokenName);
        System.out.println("A intrat dar competitia e="+competitionDTO);
        return new ResponseEntity<>(competitionDTO,HttpStatus.OK);
    }

    @PostMapping(value = "/done")
    public ResponseEntity<?> countStepsForEachUser(){
        List<UserSteps> userSteps = this.competitionService.countStepsForEachUser();
        return new ResponseEntity<>(userSteps,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Competition> addCompetition(@RequestBody CompetitionDTO competition){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        Competition competitionReturned = this.competitionService.addCompetition(competition,userTokenName);
        return new ResponseEntity<>(competitionReturned,HttpStatus.OK);
    }

    @PostMapping({"/finished"})
    public ResponseEntity<?> stopCompetition(){
        boolean ok = this.competitionService.stopCompetition();
        return new ResponseEntity<>(ok,HttpStatus.OK);
    }

    @PostMapping(value = "/competition/{competitionId}")
    public ResponseEntity<CompetitionDTO> startCompetition(@PathVariable String competitionId){
        CompetitionDTO competition = this.competitionService.startCompetition(competitionId);
        if (competition != null){
            this.webSocketHandler.notifyUsers(competition.toString());
        }
        System.out.println(competition);
        return new ResponseEntity<>(competition,HttpStatus.OK);
    }

}
