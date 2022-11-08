package com.taxi.model.dao.car_status;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.dao.car_status.CarStatusDoa;
import com.taxi.model.entity.CarStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarStatusDoaImp implements CarStatusDoa {

    private Connection connection;

    protected CarStatusDoaImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CarStatus create(CarStatus carStatus) {
        String query = """                    
                            INSERT into car_status 
                            ( 
                            name,
                            name_ua                    
                            ) VALUES(?,?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, carStatus.getName());
            preparedStatement.setString(2, carStatus.getNameUA());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                carStatus.setId(resultSet.getInt(1));

                return carStatus;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Either car status  with name = \"")
                    .append(carStatus.getName())
                    .append("\" already exist")
                    .append(" or car status  with nameUA = \"")
                    .append(carStatus.getNameUA())
                    .append("\" already exist")
            ;


            throw new NonUniqueObjectException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CarStatus extractCarStatusFromResultSet(ResultSet resultSet) throws SQLException {

        CarStatus carStatus = new CarStatus();
        carStatus.setId(resultSet.getInt("id"));
        carStatus.setName(resultSet.getString("name"));
        carStatus.setNameUA(resultSet.getString("name_ua"));


        return carStatus;
    }

    @Override
    public CarStatus findById(int id) {
        String query = """
                SELECT * from car_status where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CarStatus carStatus = extractCarStatusFromResultSet(resultSet);
                return carStatus;
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
    public List<CarStatus> findAll() {
        String query = """
                SELECT * from car_status;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<CarStatus> carStatusList = new ArrayList<>();
            CarStatus carStatus;
            while (resultSet.next()){
                carStatus = extractCarStatusFromResultSet(resultSet);
                carStatusList.add(carStatus);
            }
            return carStatusList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(CarStatus carStatus) {
        if(findById(carStatus.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The CarStatus object with id =")
                    .append(carStatus.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """
                UPDATE car_status 
                SET 
                name=?,
                name_ua=?                   
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){




            preparedStatement.setString(1,carStatus.getName());
            preparedStatement.setString(2,carStatus.getNameUA());
            preparedStatement.setInt(3,carStatus.getId());


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
                    .append("The CarStatus object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """           
               DELETE from car_status 
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
