package com.taxi.model.dao;

import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.controller.exceptions.NotUniqUserException;
import com.taxi.model.entity.UserRole;
import com.taxi.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private Connection connection;

    UserDaoImpl(Connection connection) {

        this.connection = connection;
    }


    @Override
    public User create(User user) {

        String query = """                    
                            INSERT into users 
                            ( 
                            login, 
                            password, 
                            firstName, 
                            lastName, 
                            email
                            ) VALUES(?, ?, ?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setRoleList(obtainUserRoles(user));
                return user;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("User with login \"")
                    .append(user.getLogin())
                    .append("\" already exist");

            throw new NotUniqUserException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));

        return user;

    }

    public User findById(int id) {


        String query = """
                SELECT * from users where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
               User user = extractUserFromResultSet(resultSet);
               user.setRoleList(obtainUserRoles(user));

                return user;

            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {

        String query = """
                SELECT * from users;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> userList = new ArrayList<>();
            User user;
            while (resultSet.next()){
               user = extractUserFromResultSet(resultSet);
               user.setRoleList(obtainUserRoles(user));
               userList.add(user);
            }
            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean update(User user) {

        if(findById(user.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The User object with id =")
                    .append(user.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """
                UPDATE users 
                SET 
                login=?,
                password=?, 
                firstName=?, 
                lastName=?, 
                email=?          
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString( 2, user.getPassword());
            preparedStatement.setString( 3, user.getFirstName());
            preparedStatement.setString( 4, user.getLastName());
            preparedStatement.setString( 5, user.getEmail());
            preparedStatement.setLong(6, user.getId());


            user.setRoleList(obtainUserRoles(user));
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
                    .append("The User object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """           
               DELETE from users 
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
    public User findByLoginPassword(String login, String password) {


        String query = """
              SELECT * 
              FROM users 
              WHERE login = ? and password = ?;
                """;
        User user;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString( 1, login);
            preparedStatement.setString( 2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = extractUserFromResultSet(resultSet);
                user.setRoleList(obtainUserRoles(user));

                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<UserRole> obtainUserRoles(User user){

        String query = """    
                select
                roles.id,
                roles.name
                from
                taxi_db.roles as roles
                inner join
                taxi_db.users_roles as user_roles
                on
                user_roles.role_id =  roles.id
                where
                user_roles.user_id = ?
                """;
        UserRole userRole;
        List<UserRole> userRoleList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1,user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                userRole = new UserRole(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
                userRoleList.add(userRole);
            }

            return userRoleList;

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

