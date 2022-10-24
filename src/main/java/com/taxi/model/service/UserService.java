package com.taxi.model.service;

import com.taxi.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getById(long id);

    Optional<User> login(String email, String pass);


    User create(String pass, String firstName, String lastName, String email);


}
