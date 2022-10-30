package com.taxi.model.dao;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;
import com.taxi.model.entity.UserRole;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDaoImp implements UserRoleDao{

    private Connection connection;

    public UserRoleDaoImp(Connection connection) {
        this.connection = connection;
    }

    private String mainQuery = """
            SELECT
             ur.user_id,
             ur.role_id,
             --
             u.id as u_id,
             u.login as u_login,
             u.email as u_email,
             u.password as u_password,
             u.firstname as u_firstname,
             u.lastname as u_lastname,
             --
             r.id as r_id,
             r.name as r_name,
             r.name_ua as r_name_ua
             --
             from users_roles as ur
             inner join  users as u
             on  ur.user_id = u.id
             inner join  roles as r
             on ur.role_id = r.id  
            """;

    private Role extractRole(ResultSet resultSet) throws SQLException {

        Role role = new Role();
        role.setId(resultSet.getInt("r_id"));
        role.setName(resultSet.getString("r_name"));
        role.setNameUA(resultSet.getString("r_name_ua"));
        return role;

    }

    private User extractUser(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setId(resultSet.getInt("u_id"));
        user.setLogin(resultSet.getString("u_login"));
        user.setEmail(resultSet.getString("u_email"));
        user.setPassword(resultSet.getString("u_password"));
        user.setFirstName(resultSet.getString("u_firstname"));
        user.setLastName(resultSet.getString("u_lastname"));
        return  user;

    }

    private UserRole extractUserRole(ResultSet resultSet) throws SQLException {

        User user = extractUser(resultSet);
        Role role = extractRole(resultSet);
        UserRole userRole = new UserRole(user,role);
        return userRole;

    }


    @Override
    public  List<Role> getAllUserRoles(User user) {

        String query = mainQuery + "where user_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Role> roleList = new ArrayList<>();
            Role role;
            while (resultSet.next()){
                role = extractRole(resultSet);
                roleList.add(role);
            }
            return roleList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsersWithRole(Role role) {

        String query = mainQuery + "where ur.role_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, role.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> userList = new ArrayList<>();
            User user;
            while (resultSet.next()){
                user = extractUser(resultSet);
                userList.add(user);
            }
            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRole> getAll() {

        String query = mainQuery;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserRole> userRoleList = new ArrayList<>();
            UserRole userRole;
            while (resultSet.next()){
                userRole = extractUserRole(resultSet);
                userRoleList.add(userRole);
            }
            return userRoleList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean assignRole(UserRole userRole) {
        return assignRole(userRole.getUser(),userRole.getRole());
    }

    @Override
    public boolean assignRole(User user, Role role) {

        boolean userExists = userExists(user.getId());
        boolean roleExists = roleExists(role.getId());
        if(!roleExists || !userExists){
            StringBuffer stringBuffer = new StringBuffer()
                    .append("The attempt to assign the role to the user is failed: ");
            if(!userExists){
                stringBuffer
                        .append(" The user with id = ")
                        .append(user.getId())
                        .append(" doesn't exist in the database.");

            }
            if(!roleExists){
                stringBuffer
                        .append(" The role with id = ")
                        .append(role.getId())
                        .append(" doesn't exist in the database.");
            }

            throw new InvalidParameterException( stringBuffer.toString());

        }

        String query = """                    
                            INSERT into users_roles 
                            ( 
                            user_id, 
                            role_id
                            ) VALUES(?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The UserRole object with user_id = ")
                    .append(user.getId())
                    .append(" and role_id = ")
                    .append(role.getId())
                    .append(" already exists")
                    ;
            throw new NonUniqueObjectException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeRole(UserRole userRole) {
        return removeRole(userRole.getUser(),userRole.getRole());
    }

    @Override
    public boolean removeRole(User user, Role role) {

        String query = """           
               DELETE from users_roles 
               where 
               user_id =? and role_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());
            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean roleExists(int roleID){

        String query = "select * from roles where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roleID);
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

    public boolean userExists(int userID){

        String query = "select * from users where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userID);
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


    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
