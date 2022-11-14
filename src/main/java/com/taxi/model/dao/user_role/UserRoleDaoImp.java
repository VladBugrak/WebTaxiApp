package com.taxi.model.dao.user_role;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.model.dao.ObjectExistenceCheckIn;
import com.taxi.model.entity.CarCategory;
import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;
import com.taxi.model.entity.UserRole;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDaoImp implements UserRoleDao{

    private Connection connection;

    protected UserRoleDaoImp(Connection connection) {
        this.connection = connection;
    }

    private final String MAIN_QUERY = """
            SELECT
             -- UserRole's field
             ur.id ,
             -- User's fields
             u.id as u_id,
             u.login as u_login,
             u.email as u_email,
             u.password as u_password,
             u.firstname as u_firstname,
             u.lastname as u_lastname,
             -- Role's fields
             r.id as r_id,
             r.name as r_name,
             r.name_ua as r_name_ua
             --
             from taxi_db.users_roles as ur
             inner join  users as u
             on  ur.user_id = u.id
             inner join  roles as r
             on ur.role_id = r.id  
             -- below is the space for adding a selection condition
             
            """;

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

    private Role extractRole(ResultSet resultSet) throws SQLException {

        Role role = new Role();
        role.setId(resultSet.getInt("r_id"));
        role.setName(resultSet.getString("r_name"));
        role.setNameUA(resultSet.getString("r_name_ua"));
        return role;

    }
    private UserRole extractUserRole(ResultSet resultSet) throws SQLException {

        User user = extractUser(resultSet);
        Role role = extractRole(resultSet);
        int id = resultSet.getInt("id");
        UserRole userRole = new UserRole(id,user,role);
        return userRole;

    }
    private void parameterCheckIn(UserRole userRole) throws InvalidParameterException {
        parameterCheckIn(userRole,false,null);
    }

    private void parameterCheckIn(UserRole userRole, boolean checkUserRoleExistence,String operationName) throws InvalidParameterException {

        if(userRole == null){
            throw new InvalidParameterException("You've passed null as a UserRole parameter.");
        }

        User user = userRole.getUser();
        Role role = userRole.getRole();

        if (user == null || role == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" You've passed UserRole parameter with");
            if(user == null){
                stringBuilder.append(" user = null value ");
            }
            if(role == null){
                stringBuilder.append(" role = null value ");
            }
            throw new InvalidParameterException(stringBuilder.toString());
        }

        boolean isUserExist = ObjectExistenceCheckIn.isExist(user, connection);
        boolean isRoleExist = ObjectExistenceCheckIn.isExist(role, connection);

        if (isUserExist && isRoleExist) {

            if(checkUserRoleExistence) {
                boolean isUserRoleExist = ObjectExistenceCheckIn.isExist(userRole, connection);

                if (!isUserRoleExist) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer
                            .append("The UserRole object with id =")
                            .append(userRole.getId())
                            .append(" does not exist in the data base.");
                    if(operationName != null){
                        stringBuffer
                                .append(" So you can't ")
                                .append(operationName)
                                .append(" it")
                                ;
                    }
                    throw new ObjectNotFoundException(stringBuffer.toString());
                }
            }
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You've passed wrong UserRole parameter: ");
        if (!isUserExist) {
            stringBuilder
                    .append(" the User object with id = ")
                    .append(user.getId())
                    .append(" doesn't exist in the database. ");
        }
        if (!isRoleExist) {
            stringBuilder
                    .append(" the Role object with id = ")
                    .append(role.getId())
                    .append(" doesn't exist in the database. ");
        }

        throw new InvalidParameterException(stringBuilder.toString());
    }
    @Override
    public UserRole create(UserRole userRole) {


        parameterCheckIn(userRole);

        String query = """                    
                            INSERT into users_roles 
                            ( 
                            user_id,
                            role_id                    
                            ) VALUES(?,?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
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
                    .append("UserRole object with user = \"")
                    .append(userRole.getUser())
                    .append("\" already exist")
                    .append(" and role = \"")
                    .append(userRole.getRole())
                    .append("\" already exist")
            ;
            throw new NonUniqueObjectException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public UserRole findById(int id) {
        final String QUERY = MAIN_QUERY + " where ur.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserRole userRole = extractUserRole(resultSet);
                return userRole;
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer
                        .append("The UserRole object with id =")
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
    public List<UserRole> findAll() {
        String query = MAIN_QUERY;

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
    public boolean update(UserRole userRole) {

        parameterCheckIn(userRole,true,"update");
        String query = """
                UPDATE users_roles 
                SET 
                user_id=?,
                role_id=?                   
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1,userRole.getUser().getId());
            preparedStatement.setInt(2,userRole.getRole().getId());
            preparedStatement.setInt(3,userRole.getId());


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
                    .append("The UserRole object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """           
               DELETE from users_roles 
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
    public  List<Role> getAllUserRoles(User user) {

        String query = MAIN_QUERY + "where ur.user_id=?";

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

        String query = MAIN_QUERY + "where ur.role_id=?";

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
    public boolean assignRole(UserRole userRole) {

        parameterCheckIn(userRole);

        String query = """                    
                            INSERT into users_roles 
                            ( 
                            user_id, 
                            role_id
                            ) VALUES(?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
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
                    .append(userRole.getUser().getId())
                    .append(" and role_id = ")
                    .append(userRole.getRole().getId())
                    .append(" already exists")
            ;
            throw new NonUniqueObjectException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean assignRole(User user, Role role) {

        UserRole userRole = new UserRole(user,role);
        return assignRole(userRole);

    }
    @Override
    public boolean removeRole(UserRole userRole) {

        parameterCheckIn(userRole,true,"remove");
        String query = """           
               DELETE from users_roles 
               where 
               user_id = ? and role_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean removeRole(User user, Role role) {

        UserRole userRole = new UserRole(user,role);
        return removeRole(userRole);


    }
    public boolean removeInvalidRecords(){

        final String QUERY = """           
             delete
                                         from users_roles as ur
                                         where (ur.user_id,ur.role_id) in
                                         (
                                         SELECT
                                         ur.user_id,
                                         ur.role_id
                                         FROM taxi_db.users_roles as ur
                                         left join users as u
                                         on ur.user_id = u.id
                                         left join roles as r
                                         on ur.role_id = r.id
                                         where isnull(u.id) or isnull(r.id)
                                         )
                                         
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(QUERY)){
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
