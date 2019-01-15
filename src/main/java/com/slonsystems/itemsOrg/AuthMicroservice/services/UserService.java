package com.slonsystems.itemsOrg.AuthMicroservice.services;

import com.slonsystems.itemsOrg.AuthMicroservice.auth.AuthResponse;
import com.slonsystems.itemsOrg.AuthMicroservice.auth.AuthStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import com.slonsystems.itemsOrg.AuthMicroservice.registration.RegistrationResponse;
import com.slonsystems.itemsOrg.AuthMicroservice.registration.UserRegistrator;
import com.slonsystems.itemsOrg.AuthMicroservice.repositories.UserRepository;
import com.slonsystems.itemsOrg.AuthMicroservice.utils.UserManagmentUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    private AuthStrategy authStrategy;
    private UserRegistrator registrator;

    @Autowired
    public UserService(AuthStrategy authStrategy, UserRegistrator registrator){
        this.authStrategy = authStrategy;
        this.registrator = registrator;
    }

    public AuthResponse auth(String login, String password){
        return authStrategy.auth(login,password);
    }

    public RegistrationResponse registration(String login, String password){
        return registrator.register(login,password);
    }
}
