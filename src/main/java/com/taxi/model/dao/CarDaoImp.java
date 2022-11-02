package com.taxi.model.dao;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.entity.Car;
import com.taxi.model.entity.CarCategory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImp implements CarDao {

    private Connection connection;

    CarDaoImp(Connection connection) {

        this.connection = connection;
    }

    final String SEARCHING_QUERY = """
            Select
            -- Car fields:
            c.id,
            c.plate_number,
            c.model,
            c.color,
            c.color_ua,
            c.car_category_id,
            c.capacity,
            c.image_link,
            -- CarCategory fields
            cc.id as cc_id,
            cc.name as cc_name,
            cc.name_ua as cc_name_ua
            --
            from cars as c
            inner join
            car_category as cc
            on c.car_category_id = cc.id
            """;


    @Override
    public Car create(Car car) {
        String query = """                    
                            INSERT into cars 
                            ( 
                            plate_number, 
                            model, 
                            color, 
                            color_ua,
                            car_category_id,
                            capacity,
                            image_link
                            ) VALUES(?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, car.getPlateNumber());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getColor());
            preparedStatement.setString(4, car.getColorUA());
            preparedStatement.setInt(5, car.getCarCategory().getId());
            preparedStatement.setInt(6, car.getCapacity());
            preparedStatement.setString(7, car.getImageLink());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                car.setId(resultSet.getInt(1));
                return car;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("car with plate number \"")
                    .append(car.getPlateNumber())
                    .append("\" already exist");

            throw new NonUniqueObjectException(stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CarCategory extractCarCategoryFromResultSet(ResultSet resultSet) throws SQLException{

        CarCategory carCategory = new CarCategory();
        carCategory.setId(resultSet.getInt("cc_id"));
        carCategory.setName(resultSet.getString("cc_name"));
        carCategory.setNameUA(resultSet.getString("cc_name_ua"));
        return carCategory;



    }

    private Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {

        CarCategory carCategory = extractCarCategoryFromResultSet(resultSet);

        Car car = new Car();
        car.setId(resultSet.getInt("id"));
        car.setPlateNumber(resultSet.getString("plate_number"));
        car.setModel(resultSet.getString("model"));
        car.setColor(resultSet.getString("color"));
        car.setColorUA(resultSet.getString("color_ua"));
        car.setCarCategory(carCategory);
        car.setCapacity(resultSet.getInt("capacity"));
        car.setImageLink(resultSet.getString("image_link"));

        return car;

    }



    @Override
    public Car findById(int id) {

       final String QUERY  = SEARCHING_QUERY + "where c.id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Car car = extractCarFromResultSet(resultSet);
                return car;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findAll() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCHING_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Car> carList = new ArrayList<>();
            Car car;
            while (resultSet.next()){
                car = extractCarFromResultSet(resultSet);
                carList.add(car);
            }
            return carList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Car car) {
        if(findById(car.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The Car object with id =")
                    .append(car.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }


        final String UPDATE_QUERY = """
                UPDATE cars 
                SET 
                plate_number=?,
                model=?, 
                color=?, 
                color_ua=?, 
                car_category_id=?, 
                capacity =?,
                image_link=?         
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)){

            preparedStatement.setString(1, car.getPlateNumber());
            preparedStatement.setString( 2, car.getModel());
            preparedStatement.setString( 3, car.getColor());
            preparedStatement.setString( 4, car.getColorUA());
            preparedStatement.setInt( 5, car.getCarCategory().getId());
            preparedStatement.setInt(6, car.getCapacity());
            preparedStatement.setString(7,car.getImageLink());
            preparedStatement.setInt(8,car.getId());

            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        if(findById(id) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The car object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        final String QUERY = """           
               DELETE from cars 
               where id=?;
                """;



        try(PreparedStatement preparedStatement = connection.prepareStatement(QUERY)){

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
