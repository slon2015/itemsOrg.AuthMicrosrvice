package com.slonsystems.itemsOrg.AuthMicroservice.repositories;

import com.slonsystems.itemsOrg.AuthMicroservice.pojos.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByToken(String token);
}
