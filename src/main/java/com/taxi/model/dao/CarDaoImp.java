package com.taxi.model.dao;

import com.taxi.model.entity.Car;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CarDaoImp implements CarDao{

    private Connection connection;

    CarDaoImp(Connection connection) {

        this.connection = connection;
    }


    @Override
    public Car create(Car car) {
        return null;
    }

    @Override
    public Car findById(int id) {
        return null;
    }

    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    public boolean update(Car car) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
