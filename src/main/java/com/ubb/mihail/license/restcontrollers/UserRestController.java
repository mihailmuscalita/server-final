package com.ubb.mihail.license.restcontrollers;

import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.UserModel;
import com.ubb.mihail.license.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = {"*"}
)
@RequestMapping(value = "/license/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/{name}/{password}")
    public ResponseEntity<UserModel> findUser(@PathVariable String name, @PathVariable String password){
        UserModel user = this.userService.findUser(name,password);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping()
    public List<User> findAll(){
        return this.userService.findAllUsers();
    }

}
