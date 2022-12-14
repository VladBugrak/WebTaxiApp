package com.taxi.model.dao.user;

import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.model.dao.ObjectExistenceCheckIn;
import com.taxi.model.entity.Car;
import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;
import com.taxi.util.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private Connection connection;

    protected UserDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public  User create(User user) {

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

            throw new NonUniqueObjectException( stringBuffer.toString());
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
            preparedStatement.setString( 2, PasswordEncoder.encode(password));

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

    public List<Role> obtainUserRoles(User user) {

         String query = """
            SELECT
             r.id as r_id,
             r.name as r_name,
             r.name_ua as r_name_ua
             --
             from users_roles as ur       
             inner join  roles as r
             on ur.role_id = r.id  
             where ur.user_id=?
            """;


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Role> roleList = new ArrayList<>();
            Role role;
            while (resultSet.next()){
                role = new Role();
                role.setId(resultSet.getInt("r_id"));
                role.setName(resultSet.getString("r_name"));
                role.setNameUA(resultSet.getString("r_name_ua"));
                roleList.add(role);
            }
            return roleList;
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

