package com.slonsystems.itemsOrg.AuthMicroservice.auth;

import com.slonsystems.itemsOrg.AuthMicroservice.auth.AuthResponse;
import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import com.slonsystems.itemsOrg.AuthMicroservice.services.UserService;
import com.slonsystems.itemsOrg.AuthMicroservice.tokens.TokenGenerationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class AuthController {

    private UserService service;
    private TokenGenerationStrategy tokenGen;

    @Autowired
    public AuthController(UserService userService,TokenGenerationStrategy tokenGen){
        this.service = userService;
        this.tokenGen = tokenGen;
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth(@RequestParam(value = "Login") String login, @RequestParam(value = "Password") String password){
        AuthResponse authResult = service.auth(login,password);

        Map<String, Object> responseMap = new TreeMap<>();
        ResponseEntity<Map<String, Object>> response = null;
        if( "success".equals(authResult.getStatus()) ){

            responseMap.put("status",authResult.getStatus());
            responseMap.put("token",authResult.getUser().getToken());
            responseMap.put("UID",authResult.getUser().getId());
            response = new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        else{
            responseMap.put("status",authResult.getStatus());
            response = new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
