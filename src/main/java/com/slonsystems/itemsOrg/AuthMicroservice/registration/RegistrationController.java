package com.slonsystems.itemsOrg.AuthMicroservice.registration;

import com.slonsystems.itemsOrg.AuthMicroservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class RegistrationController {
    private UserService service;

    @Autowired
    public RegistrationController(UserService service){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@RequestParam("Login") String login,
                                                       @RequestParam("Password") String password){

        RegistrationResponse result = service.registration(login,password);

        Map<String, Object> responseMap = new TreeMap<>();
        ResponseEntity<Map<String, Object>> response = null;
        if(result.isSuccess()){
            responseMap.put("status", "success");
            responseMap.put("token", result.getUser().getToken());
            responseMap.put("UID", result.getUser().getId());
            response = new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        else {
            responseMap.put("status", "failed");
            responseMap.put("cause", result.getValidationErrors());
            response = new ResponseEntity<>(responseMap, HttpStatus.CONFLICT);
        }

        return response;
    }
}
