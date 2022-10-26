package com.taxi.model.dao;

import com.taxi.model.entity.GeolocationPoint;

public abstract class DaoFactoryAbst {
    private static DaoFactoryAbst daoFactoryAbst;

    public abstract UserDao createUserDao();
    public abstract GeolocationPointDao createGeolocationPointDao();



    public static DaoFactoryAbst getInstance(){
        if( daoFactoryAbst == null ){
            synchronized (DaoFactoryAbst.class){
                if(daoFactoryAbst ==null){
                    daoFactoryAbst = new FactoryDao();
                }
            }
        }
        return daoFactoryAbst;
    }
}
