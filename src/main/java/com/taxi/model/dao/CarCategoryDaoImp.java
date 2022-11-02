package com.taxi.model.dao;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.entity.CarCategory;

import java.sql.*;
import java.util.ArrayList;
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
                    .append("Either car category  with name = \"")
                    .append(carCategory.getName())
                    .append("\" already exist")
                    .append(" or car category  with nameUA = \"")
                    .append(carCategory.getNameUA())
                    .append("\" already exist")
                    ;


            throw new NonUniqueObjectException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CarCategory extractCarCategoryFromResultSet(ResultSet resultSet) throws SQLException {

        CarCategory carCategory = new CarCategory();
        carCategory.setId(resultSet.getInt("id"));
        carCategory.setName(resultSet.getString("name"));
        carCategory.setNameUA(resultSet.getString("name_ua"));


        return carCategory;
    }

    @Override
    public CarCategory findById(int id) {
        String query = """
                SELECT * from car_category where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CarCategory carCategory = extractCarCategoryFromResultSet(resultSet);
                return carCategory;
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer
                        .append("The CarCategory object with id =")
                        .append(id)
                        .append(" does not exist in the data base.");
                throw  new ObjectNotFoundException(stringBuffer.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarCategory> findAll() {
        String query = """
                SELECT * from car_category;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<CarCategory> carCategoryList = new ArrayList<>();
            CarCategory carCategory;
            while (resultSet.next()){
                carCategory = extractCarCategoryFromResultSet(resultSet);
                carCategoryList.add(carCategory);
            }
            return carCategoryList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CarCategory carCategory) {

        if(findById(carCategory.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The CarCategory object with id =")
                    .append(carCategory.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """
                UPDATE car_category 
                SET 
                name=?,
                name_ua=?                   
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){




            preparedStatement.setString(1,carCategory.getName());
            preparedStatement.setString(2,carCategory.getNameUA());
            preparedStatement.setInt(3,carCategory.getId());


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
                    .append("The CarCategory object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """           
               DELETE from car_category 
               where id=?;
                """;



        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

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
