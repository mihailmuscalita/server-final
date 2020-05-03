package com.ubb.mihail.license.services;

import com.ubb.mihail.license.converter.ConvertData;
import com.ubb.mihail.license.domain.Competition;
import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.UserModel;
import com.ubb.mihail.license.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserModel findUser(String name,String password){
        User user = this.userRepository.findUser(name,password);
        UserModel userModel = ConvertData.convertUserToUserModel(user);
        return userModel;
    }

    public List<User> findAllUsers(){
        return this.userRepository.findAll();
    }

}
