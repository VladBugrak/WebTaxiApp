package com.taxi.model.dao;

import com.taxi.model.dao.order.OrderDao;
import com.taxi.model.dao.order.OrderDaoImp;
import com.taxi.model.dao.tariff.TariffDao;
import com.taxi.model.dao.tariff.TariffDaoImp;
import com.taxi.model.dao.car.CarDao;
import com.taxi.model.dao.car.CarDaoImp;
import com.taxi.model.dao.car_category.CarCategoryDao;
import com.taxi.model.dao.car_category.CarCategoryDaoImp;
import com.taxi.model.dao.car_status.CarStatusDoa;
import com.taxi.model.dao.car_status.CarStatusDoaImp;
import com.taxi.model.dao.discount.DiscountDao;
import com.taxi.model.dao.discount.DiscountDaoImp;
import com.taxi.model.dao.geo_point.GeoPointDao;
import com.taxi.model.dao.geo_point.GeoPointDaoImp;
import com.taxi.model.dao.role.RoleDao;
import com.taxi.model.dao.role.RoleDaoImp;
import com.taxi.model.dao.user.UserDao;
import com.taxi.model.dao.user.UserDaoImpl;
import com.taxi.model.dao.user_role.UserRoleDao;
import com.taxi.model.dao.user_role.UserRoleDaoImp;
import com.taxi.model.entity.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DaoFactory extends DaoFactoryAbstract {

    private  DataSource dataSource = ConnectionPoolHolder.getDataSource();



    private  Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private class InnerUserDaoImp extends UserDaoImpl {
        InnerUserDaoImp(Connection connection){
            super(connection);
        }

    }

    private class InnerGeoPointDaoImp extends GeoPointDaoImp {
        InnerGeoPointDaoImp(Connection connection){
            super(connection);
        }

    }

    private class InnerRoleDaoImp extends RoleDaoImp {
        InnerRoleDaoImp(Connection connection){
            super(connection);
        }

    }

    private class InnerCarCategoryDaoImp extends CarCategoryDaoImp {
        InnerCarCategoryDaoImp(Connection connection){
            super(connection);
        }
    }

    private class InnerCarDaoImp extends CarDaoImp {
        InnerCarDaoImp(Connection connection){
            super(connection);
        }
    }

    private class InnerUserRoleDaoImp extends UserRoleDaoImp {
        InnerUserRoleDaoImp(Connection connection){
            super(connection);
        }
    }

    private class InnerDiscountDaoImp extends DiscountDaoImp {
        InnerDiscountDaoImp(Connection connection){
            super(connection);
        }
    }

    private class InnerTariffDaoImp extends TariffDaoImp {
        InnerTariffDaoImp(Connection connection){
            super(connection);
        }
    }

    private class InnerCarStatusDoaImp extends CarStatusDoaImp {
        InnerCarStatusDoaImp(Connection connection){
            super(connection);
        }
    }

    private class InnerOrderDaoImp extends OrderDaoImp {
        InnerOrderDaoImp(Connection connection) {
            super(connection);
        }
    }






    @Override
    public UserDao createUserDao() {
        return new InnerUserDaoImp(getConnection());
    }

    @Override
    public GeoPointDao createGeoPointDao() {
        return new InnerGeoPointDaoImp(getConnection());
    }

    @Override
    public RoleDao createRoleDao() {return new InnerRoleDaoImp(getConnection());  }

    @Override
    public CarCategoryDao createCarCategoryDao() {return new InnerCarCategoryDaoImp(getConnection());}


    @Override
    public CarDao createCarDao() {
        return new InnerCarDaoImp(getConnection());
    }

    @Override
    public UserRoleDao createUserRoleDao() { return new InnerUserRoleDaoImp(getConnection());}

    @Override
    public DiscountDao createDiscountDao() {
        return new InnerDiscountDaoImp(getConnection());
    }

    @Override
    public TariffDao createTariffDao() {
        return new InnerTariffDaoImp(getConnection());
    }

    @Override
    public CarStatusDoa createCarStatusDao() {
        return new InnerCarStatusDoaImp(getConnection());
    }

    public OrderDao createOrderDao(){
       return new InnerOrderDaoImp(getConnection());
    }

}
