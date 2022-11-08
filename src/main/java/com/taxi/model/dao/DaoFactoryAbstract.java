package com.taxi.model.dao;

import com.taxi.model.dao.tariff.TariffDao;
import com.taxi.model.dao.car.CarDao;
import com.taxi.model.dao.car_category.CarCategoryDao;
import com.taxi.model.dao.car_status.CarStatusDoa;
import com.taxi.model.dao.discount.DiscountDao;
import com.taxi.model.dao.geo_point.GeoPointDao;
import com.taxi.model.dao.role.RoleDao;
import com.taxi.model.dao.user.UserDao;
import com.taxi.model.dao.user_role.UserRoleDao;

import java.sql.Connection;

public abstract class DaoFactoryAbstract {
    private static DaoFactoryAbstract daoFactoryAbstract;



    public abstract UserDao createUserDao();
    public abstract GeoPointDao createGeoPointDao();

    public abstract RoleDao createRoleDao();

    public abstract CarCategoryDao createCarCategoryDao();

    public abstract UserRoleDao createUserRoleDao();

    public abstract CarDao createCarDao();

    public abstract TariffDao createTariffDao();

    public abstract DiscountDao createDiscountDao();

    public abstract CarStatusDoa createCarStatusDao();



    public static DaoFactoryAbstract getInstance(){
        if( daoFactoryAbstract == null ){
            synchronized (DaoFactoryAbstract.class){
                if(daoFactoryAbstract ==null){
                    daoFactoryAbstract = new DaoFactory();
                }
            }
        }
        return daoFactoryAbstract;
    }
}
