package com.taxi.model.dao;

import com.taxi.model.entity.User;

public interface UserDao extends GenericDao<User> {
    User findByLoginPassword(String name, String password);
}
