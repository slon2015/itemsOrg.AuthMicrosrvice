package com.slonsystems.itemsOrg.AuthMicroservice.registration;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import com.slonsystems.itemsOrg.AuthMicroservice.repositories.UserRepository;
import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TokenGenerationStrategy;
import com.slonsystems.itemsOrg.AuthMicroservice.utils.UserTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRegistrator {

    private UserRepository userRepository;
    private List<RegistrationDataValidator> validators;
    private TokenGenerationStrategy tokenGen;
    private UserTemplates userTemplates;

    @Autowired
    public UserRegistrator(UserRepository repository, List<RegistrationDataValidator> validators
            , TokenGenerationStrategy tokenGen, UserTemplates userTemplates){
        this.userRepository = repository;
        this.validators = validators;
        this.tokenGen = tokenGen;
        this.userTemplates = userTemplates;
    }

    public RegistrationResponse register(String login, String password){
        RegistrationResponse response = new RegistrationResponse();
        for( RegistrationDataValidator validator : validators ){
            try{
                validator.validate(login,password);
            }
            catch (RegistrartionDataInvalidException e){
                response.getValidationErrors().add(e.getMessage());
            }
        }

        if(response.isValid()){
            if(!userRepository.findByLogin(login).isPresent()){
                response.setSuccess(true);
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                userTemplates.appendToken(user,tokenGen.getUniqToken());
                userRepository.save(user);
                response.setUser(user);
            }
            else {
                response.getValidationErrors().add("User already exists");
                response.setSuccess(false);
            }
        }
        return response;
    }
}
