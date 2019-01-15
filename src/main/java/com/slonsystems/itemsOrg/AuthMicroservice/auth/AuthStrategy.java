package com.slonsystems.itemsOrg.AuthMicroservice.auth;

public interface AuthStrategy {
    AuthResponse auth(String login, String password);
}
