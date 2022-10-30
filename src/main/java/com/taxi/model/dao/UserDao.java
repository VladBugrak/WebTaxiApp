package com.taxi.model.dao;

import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {
    User findByLoginPassword(String name, String password);

    List<Role> obtainUserRoles(User user);


}
