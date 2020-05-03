package com.ubb.mihail.license.restcontrollers;

import com.ubb.mihail.license.model.Message;
import com.ubb.mihail.license.model.RegisterCompetitionModel;
import com.ubb.mihail.license.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = {"*"}
)
@RequestMapping(value = "/license/steps")
public class StepRestController {

    private final StepService stepService;

    @Autowired
    public StepRestController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping()
    public ResponseEntity<?> registerUserToCompetition(@RequestBody RegisterCompetitionModel registerCompetitionModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        RegisterCompetitionModel registerCompetitionModelFind = this.stepService.registerUserToCompetition(registerCompetitionModel,userTokenName);
        if (registerCompetitionModelFind!=null){
            return new ResponseEntity<>(registerCompetitionModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("You are already registered!"),HttpStatus.OK);
    }

    @PutMapping(value="/{steps}")
    public ResponseEntity<?> updateStepsToUser(@PathVariable int steps){
        System.out.println("Se face update la pasi aici !="+steps);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userTokenName = userDetails.getUsername();
        int result = this.stepService.updateStepsToUser(steps,userTokenName);
        if (result != -1){
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not registered to current competition!",HttpStatus.BAD_REQUEST);
    }
}
