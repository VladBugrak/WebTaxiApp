package com.taxi.model.dao;

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
