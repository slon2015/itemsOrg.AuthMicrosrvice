package com.slonsystems.itemsOrg.AuthMicroservice.tokens.resolving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class TokenResolveController {

    private TokensService service;

    @Autowired
    public TokenResolveController(TokensService service){
        this.service = service;
    }

    @GetMapping("/tokenResolve")
    public ResponseEntity<Map<String,Object>> resolve(@RequestParam("Token") String token){
        Map<String, Object> responseMap = new TreeMap<>();
        ResponseEntity<Map<String, Object>> response = null;

        TokenInfo info = service.resolve(token);
        if( info.getUID() != null && info.isAlive() ){
            responseMap.put("UID", info.getUID());
            response = new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
