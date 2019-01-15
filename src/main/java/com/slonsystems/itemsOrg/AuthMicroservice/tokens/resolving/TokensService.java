package com.slonsystems.itemsOrg.AuthMicroservice.tokens.resolving;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import com.slonsystems.itemsOrg.AuthMicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokensService {

    private UserRepository userRepository;

    @Autowired
    public TokensService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public TokenInfo resolve(String token){
        TokenInfo response = new TokenInfo();
        User user = userRepository.findByToken(token).orElse(null);

        response.setToken(token);
        if(user != null){
            response.setUID(user.getId());
            response.setBirth(user.getTokenBirthTime());
            response.setLifetime(user.getTokenLifeTime());
        }

        return response;
    }
}
