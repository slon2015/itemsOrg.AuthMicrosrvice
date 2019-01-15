package com.slonsystems.itemsOrg.AuthMicroservice.utils;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import com.slonsystems.itemsOrg.AuthMicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserTemplates {
    private UserRepository userRepository;

    @Autowired
    public UserTemplates(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void appendToken(User user, String token){
        user.setToken(token);
        user.setTokenBirthTime(new Date().getTime());
        user.setTokenLifeTime(UserManagmentUtilits.tokenLifetime);

        userRepository.save(user);
    }
}
