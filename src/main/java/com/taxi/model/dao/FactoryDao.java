package com.taxi.model.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class FactoryDao extends DaoFactoryAbst {

    private  DataSource dataSource = ConnectionPoolHolder.getDataSource();


    private  Connection getConnection(){
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
    public GeoPointDao createGeoPointDao() {
        return new GeoPointDaoImp(getConnection());
    }

    @Override
    public RoleDao createRoleDao() {return new RoleDaoImp(getConnection());  }

    @Override
    public CarCategoryDao createCarCategoryDao() {return new CarCategoryDaoImp(getConnection());}


    @Override
    public UserRoleDao createUserRoleDao() { return new UserRoleDaoImp(getConnection());}

}
