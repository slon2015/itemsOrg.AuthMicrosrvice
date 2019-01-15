package com.slonsystems.itemsOrg.AuthMicroservice.auth;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;

public class AuthResponse {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private User user;

    private String status;
}
