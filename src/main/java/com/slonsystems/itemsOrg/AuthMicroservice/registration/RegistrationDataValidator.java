package com.slonsystems.itemsOrg.AuthMicroservice.registration;

public interface RegistrationDataValidator {
    void validate(String login, String password) throws RegistrartionDataInvalidException;
}
