package com.ubb.mihail.license.services;

import com.ubb.mihail.license.domain.User;
import com.ubb.mihail.license.model.UserDTO;
import com.ubb.mihail.license.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    //it is the code for the userRole = user; 2 is for admin
    public static final String USER_ROLE = "1";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
                new ArrayList<>());
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public User save(UserDTO user) {
        User newUser = new User();
        newUser.setUserName(user.getUsername());
        newUser.setName(user.getName());
        newUser.setUserPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setUserRole(Integer.parseInt(USER_ROLE));
        newUser.setEmail(user.getEmail());
        return userRepository.save(newUser);
    }

}