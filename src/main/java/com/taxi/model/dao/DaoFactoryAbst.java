package com.taxi.model.dao;

public abstract class DaoFactoryAbst {
    private static DaoFactoryAbst daoFactoryAbst;

    public abstract UserDao createUserDao();
    public abstract GeoPointDao createGeoPointDao();



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
