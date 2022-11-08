package com.taxi.model.dao.role;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImp implements RoleDao {

    private Connection connection;

    protected RoleDaoImp(Connection connection) {

        this.connection = connection;
    }

    @Override
    public Role create(Role role) {
        String query = """                    
                            INSERT into roles 
                            ( 
                            name,
                            name_ua                    
                            ) VALUES(?,?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getNameUA());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                role.setId(resultSet.getInt(1));

                return role;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Either user role category  with name = \"")
                    .append(role.getName())
                    .append("\" already exist")
                    .append(" or user role  with nameUA = \"")
                    .append(role.getNameUA())
                    .append("\" already exist")
            ;


            throw new NonUniqueObjectException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Role extractUserRoleFromResultSet(ResultSet resultSet) throws SQLException {

        Role role = new Role();
        role.setId(resultSet.getInt("id"));
        role.setName(resultSet.getString("name"));
        role.setNameUA(resultSet.getString("name_ua"));


        return role;
    }
    @Override
    public Role findById(int id) {
        return findById(id,true);
    }


    public Role findById(int id, boolean throwException) {
        String query = """
                SELECT * from roles where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Role role = extractUserRoleFromResultSet(resultSet);
                return role;
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
    public List<Role> findAll() {
        String query = """
                SELECT * from roles;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Role> roleList = new ArrayList<>();
            Role role;
            while (resultSet.next()){
                role = extractUserRoleFromResultSet(resultSet);
                roleList.add(role);
            }
            return roleList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Role role) {
        if(findById(role.getId(),false) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The UserRole object with id =")
                    .append(role.getId())
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




            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getNameUA());
            preparedStatement.setInt(3, role.getId());


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
