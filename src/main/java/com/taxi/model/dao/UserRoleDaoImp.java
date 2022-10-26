package com.taxi.model.dao;

import com.taxi.model.entity.UserRole;

import java.sql.Connection;
import java.util.List;

public class UserRoleDaoImp implements UserRoleDao{

    private Connection connection;

    UserRoleDaoImp(Connection connection) {

        this.connection = connection;
    }

    @Override
    public UserRole create(UserRole entity) {
        return null;
    }

    @Override
    public UserRole findById(int id) {
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        return null;
    }

    @Override
    public boolean update(UserRole entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {

    }
}
