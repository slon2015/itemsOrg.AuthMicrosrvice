package com.slonsystems.itemsOrg.AuthMicroservice.auth;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import com.slonsystems.itemsOrg.AuthMicroservice.repositories.UserRepository;
import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TokenGenerationStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.utils.UserManagmentUtilits;
import com.slonsystems.itemsOrg.AuthMicroservice.utils.UserTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class defaultAuthStrategy implements AuthStrategy {

    private UserRepository userRepository;
    private TokenGenerationStrategy tokenGen;
    private UserTemplates userTemplates;

    @Autowired
    public defaultAuthStrategy(UserRepository userRepository, TokenGenerationStrategy tokenGen
            ,UserTemplates userTemplates){
        this.userRepository = userRepository;
        this.tokenGen = tokenGen;
        this.userTemplates = userTemplates;
    }

    @Override
    public AuthResponse auth(String login, String password) {
        User target = userRepository.findByLogin(login).orElse(null);

        AuthResponse response = new AuthResponse();

        if( target != null && target.getPassword().equals(password) ){
            String token = tokenGen.getUniqToken();
            userTemplates.appendToken(target,token);

            response.setStatus("success");
            response.setUser(target);
        }
        else{
            response.setStatus("failed");
        }
        return response;
    }
}
