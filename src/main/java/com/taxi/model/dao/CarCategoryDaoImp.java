package com.taxi.model.dao;

import com.taxi.controller.exceptions.NonUniqGeoPointException;
import com.taxi.model.entity.CarCategory;

import java.sql.*;
import java.util.List;

public class CarCategoryDaoImp implements CarCategoryDao{

    private Connection connection;

    CarCategoryDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CarCategory create(CarCategory carCategory) {
        String query = """                    
                            INSERT into car_category 
                            ( 
                            name,
                            name_ua                    
                            ) VALUES(?,?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, carCategory.getName());
            preparedStatement.setString(2, carCategory.getNameUA());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                carCategory.setId(resultSet.getInt(1));

                return carCategory;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Car category  with name = \"")
                    .append(carCategory.getName())
                    .append("\" already exist")
                    ;


            throw new NonUniqGeoPointException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarCategory findById(int id) {
        return null;
    }

    @Override
    public List<CarCategory> findAll() {
        return null;
    }

    @Override
    public boolean update(CarCategory entity) {
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
