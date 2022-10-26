package com.taxi.model.dao;

import com.taxi.model.entity.GeolocationPoint;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class FactoryDao extends DaoFactoryAbst {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();


    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl(getConnection());
    }

    @Override
    public GeolocationPointDao createGeolocationPointDao() {
        return new GeolocationPointDaoImp(getConnection());
    }




}
