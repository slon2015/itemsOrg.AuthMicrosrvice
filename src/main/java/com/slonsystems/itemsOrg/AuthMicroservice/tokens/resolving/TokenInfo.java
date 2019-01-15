package com.slonsystems.itemsOrg.AuthMicroservice.tokens.resolving;

import java.time.Instant;

public class TokenInfo {
    private Instant birth;
    private Long lifetime;
    private String token;
    private Long UID;

    public boolean isAlive(){
        Instant now = Instant.now();
        if(now.getEpochSecond() < birth.getEpochSecond() + lifetime){
            return true;
        }
        else {
            return false;
        }
    }

    public Instant getBirth() {
        return birth;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public void setBirth(Long birth){
        this.birth = Instant.ofEpochMilli(birth);
    }

    public Long getLifetime() {
        return lifetime;
    }

    public void setLifetime(Long lifetime) {
        this.lifetime = lifetime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long UID) {
        this.UID = UID;
    }
}
