package com.restapi.users.repository;

import com.restapi.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByUuid(String uuid);

    User findByName(String name);

}