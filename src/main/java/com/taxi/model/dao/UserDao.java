package com.taxi.model.dao;

import com.taxi.model.entity.User;

public interface UserDao {
    User findByLoginPassword(String name, String password);
}
