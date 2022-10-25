package com.taxi.model.dao;

import com.taxi.controller.exceptions.NotUniqUserException;
import com.taxi.model.entity.User;

import java.sql.*;
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
                            ) VALUES(?, ?, ?, ?, ?)
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
                return user;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NotUniqUserException("User with such login already exist");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public User findById(int id) {


        String query = """
                SELECT * from users where id_user=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));

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
        return null;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public User findByLoginPassword(String name, String password) {
        return null;
    }
}

