package com.taxi.model.dao;

import com.taxi.controller.exceptions.NonUniqGeoPointException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.entity.CarCategory;
import com.taxi.model.entity.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDaoImp implements UserRoleDao{

    private Connection connection;

    UserRoleDaoImp(Connection connection) {

        this.connection = connection;
    }

    @Override
    public UserRole create(UserRole userRole) {
        String query = """                    
                            INSERT into roles 
                            ( 
                            name,
                            name_ua                    
                            ) VALUES(?,?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, userRole.getName());
            preparedStatement.setString(2, userRole.getNameUA());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                userRole.setId(resultSet.getInt(1));

                return userRole;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Either user role category  with name = \"")
                    .append(userRole.getName())
                    .append("\" already exist")
                    .append(" or user role  with nameUA = \"")
                    .append(userRole.getNameUA())
                    .append("\" already exist")
            ;


            throw new NonUniqGeoPointException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private UserRole extractUserRoleFromResultSet(ResultSet resultSet) throws SQLException {

        UserRole userRole = new UserRole();
        userRole.setId(resultSet.getInt("id"));
        userRole.setName(resultSet.getString("name"));
        userRole.setNameUA(resultSet.getString("name_ua"));


        return userRole;
    }
    @Override
    public UserRole findById(int id) {
        return findById(id,true);
    }


    public UserRole findById(int id,boolean throwException) {
        String query = """
                SELECT * from roles where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserRole userRole = extractUserRoleFromResultSet(resultSet);
                return userRole;
            } else {
                if(throwException) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer
                            .append("The UserRole object with id =")
                            .append(id)
                            .append(" does not exist in the database.");
                    throw new ObjectNotFoundException(stringBuffer.toString());
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRole> findAll() {
        String query = """
                SELECT * from roles;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserRole> userRoleList = new ArrayList<>();
            UserRole userRole;
            while (resultSet.next()){
                userRole = extractUserRoleFromResultSet(resultSet);
                userRoleList.add(userRole);
            }
            return userRoleList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(UserRole userRole) {
        if(findById(userRole.getId(),false) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The UserRole object with id =")
                    .append(userRole.getId())
                    .append(" does not exist in the database. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """
                UPDATE roles 
                SET 
                name=?,
                name_ua=?                   
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){




            preparedStatement.setString(1,userRole.getName());
            preparedStatement.setString(2,userRole.getNameUA());
            preparedStatement.setInt(3,userRole.getId());


            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        if(findById(id,false) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The UserRole object with id =")
                    .append(id)
                    .append(" does not exist in the database. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """           
               DELETE from roles 
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
