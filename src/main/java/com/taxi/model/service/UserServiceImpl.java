package com.taxi.model.service;

import com.taxi.model.dao.UserDao;
import com.taxi.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Override
    public List<User> getAllUsers() {
        try (UserDao dao = daoFactoryAbst.createUserDao()) {
            return dao.findAll();
        }
    }

    @Override
    public Optional<User> getById(long id) {
        try (UserDao userDao = daoFactoryAbst.createUserDao()) {
            return Optional.ofNullable(userDao.findById(id));
        }
    }

    @Override
    public Optional<User> login(String email, String pass) {
        try (UserDao userDao = daoFactoryAbst.createUserDao()) {
            return Optional.ofNullable(userDao.findByLoginPassword(email, pass));
        }
    }



    @Override
    public User create(String pass, String firstName, String lastName, String email) {
        try (UserDao userDao = daoFactoryAbst.createUserDao()) {
            return userDao.create(new User(login, pass, firstName, lastName, email, User.Role.USER));
        }
    }
}
