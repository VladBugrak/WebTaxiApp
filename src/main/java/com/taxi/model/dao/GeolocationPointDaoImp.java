package com.taxi.model.dao;

import java.sql.Connection;
import java.util.List;

public class GeolocationPointDaoImp implements GeolocationPointDao{

    private Connection connection;

    GeolocationPointDaoImp(Connection connection) {
        this.connection = connection;
    }



    @Override
    public Object create(Object entity) {
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public boolean update(Object entity) {
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
