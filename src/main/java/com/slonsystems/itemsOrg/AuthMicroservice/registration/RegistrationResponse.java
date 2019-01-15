package com.slonsystems.itemsOrg.AuthMicroservice.registration;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationResponse {
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public boolean isValid(){
        return validationErrors.isEmpty();
    }

    private boolean success;
    private User user;
    private List<String> validationErrors = new ArrayList<>();
}
