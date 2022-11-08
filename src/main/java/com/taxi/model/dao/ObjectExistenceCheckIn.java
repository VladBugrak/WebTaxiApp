package com.taxi.model.dao;

import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.entity.CarStatus;
import com.taxi.model.entity.Role;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectExistenceCheckIn {

    int getId();
    String getDataBaseTableName();

     static boolean isExist(ObjectExistenceCheckIn object, Connection connection){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("select * from ")
                .append(object.getDataBaseTableName())
                .append(" where id = ")
                .append(object.getId());
        final String QUERY = stringBuilder.toString();

         System.out.println(QUERY);

         try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
               return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
