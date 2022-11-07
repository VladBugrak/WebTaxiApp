package com.a_test;

import com.taxi.model.dao.*;
import com.taxi.model.entity.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FactoryDao factoryDao = new FactoryDao();
        RoleDao roleDao = factoryDao.createRoleDao();
        UserDao userDao = factoryDao.createUserDao();
        UserRoleDao userRoleDao = factoryDao.createUserRoleDao();
        CarCategoryDao carCategoryDao = factoryDao.createCarCategoryDao();
        CarDao carDao = factoryDao.createCarDao();

        TariffDao tariffDao = factoryDao.createTariffDao();




//        //Create
//        Tariff tariff = new Tariff();
//        CarCategory standardCarCategory = carCategoryDao.findById(1);
//        CarCategory luxCarCategory = carCategoryDao.findById(2);
//        CarCategory microBusCarCategory = carCategoryDao.findById(3);
//        LocalDate localDate = LocalDate.now().plusDays(1);
//
//        tariff.setEffectiveData(localDate);
//        tariff.setCarCategory(microBusCarCategory);
//        tariff.setFareForCall(80);
//        tariff.setFarePerKm(8);
//
//        System.out.println(tariff);
//        tariff = tariffDao.create(tariff);
//        System.out.println(tariff);

//        //findByID
//        Tariff tariff = tariffDao.findById(40);
//        System.out.println(tariff);

//        //findALL
//        List<Tariff> tariffList = new ArrayList<>();
//        tariffList = tariffDao.findAll();
//        for(Tariff tariff:tariffList){
//            System.out.println(tariff);
//        }


//        //update
//        Tariff tariff = tariffDao.findById(12);
//        System.out.println(tariff);
////        tariff.setFareForCall(45);
////        tariff.setFarePerKm(4.5);
//        tariff.setFarePerKm(tariff.getFarePerKm() * 1.33);
//        tariff.setFareForCall(tariff.getFareForCall() * 1.33);
////        CarCategory carCategory = carCategoryDao.findById(2);
////        System.out.println(carCategory);
////        tariff.setCarCategory(carCategory);
//
//        tariffDao.update(tariff);
//        System.out.println(tariff);

    // getTariffOnDate

        LocalDate localDate =  LocalDate.of(2022,11,4);
        CarCategory standardCarCategory = carCategoryDao.findById(1);
        CarCategory luxCarCategory = carCategoryDao.findById(2);
        CarCategory microBusCarCategory = carCategoryDao.findById(3);


        Tariff tariff = tariffDao.getTariffOnDate(localDate,luxCarCategory);
        System.out.println(tariff);




    }

    public void test_CarDaoImp(){

        FactoryDao factoryDao = new FactoryDao();
        RoleDao roleDao = factoryDao.createRoleDao();
        UserDao userDao = factoryDao.createUserDao();
        UserRoleDao userRoleDao = factoryDao.createUserRoleDao();
        CarCategoryDao carCategoryDao = factoryDao.createCarCategoryDao();
        CarDao carDao = factoryDao.createCarDao();


        CarCategory standard = carCategoryDao.findById(1);
        CarCategory lux = carCategoryDao.findById(2);
        CarCategory miniBus = carCategoryDao.findById(3);


//        //create
//        Car car = new Car();
//        car.setPlateNumber("TAXI-0008");
//        car.setModel("Volkswagen Microbus");
//        car.setColor("white-green");
//        car.setColorUA("біло-зелений");
//        car.setCarCategory(miniBus);
//        car.setCapacity(10);
//        car.toString();
//
//        carDao.create(car);
//        car.toString();

//        //findById
//        Car car = carDao.findById(10);
//        System.out.println(car.toString());

//        //findALl
//        List<Car> carList = carDao.findAll();
//        for(Car car:carList){
//            System.out.println(car);
//        }

//        //update
//        Car car = carDao.findById(7);
//        System.out.println(car);
//        car.setCapacity(22);
//        carDao.update(car);
//        System.out.println(car);

        //delete
        carDao.delete(7);









//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime);
////        LocalDate localDate = localDateTime.toLocalDate();
////        LocalTime localTime = localDateTime.toLocalTime();
////
////
////        java.sql.Date date = java.sql.Date.valueOf(localDate);
////        java.sql.Time time = java.sql.Time.valueOf(localTime);
//
//
//        Timestamp timestamp = Timestamp.valueOf(localDateTime);
//        System.out.println(timestamp);
//
//        LocalDateTime localDateTime1 = timestamp.toLocalDateTime();
//
//        System.out.println(localDateTime1);




    }



    public void test_UserRoleDaoImp(){
        FactoryDao factoryDao = new FactoryDao();
        RoleDao roleDao = factoryDao.createRoleDao();
        UserDao userDaoImp = factoryDao.createUserDao();
        UserRoleDao userRoleDao = factoryDao.createUserRoleDao();

//        for (int i = 0; i < 40 ; i++) {
//            if(userRoleDao.userExists(i)){
//                System.out.println(i);
//            }
//        }

//        for (int i = 0; i < 40; i++) {
//            if (userRoleDao.roleExists(i)) {
//                System.out.println(i);
//            }
//        }

//        List<UserRole> userRoleList = userRoleDao.getAll();
//        for(UserRole userRole: userRoleList){
//            System.out.println(userRole.toString());
//        }

//        Role role = roleDao.findById(3);
//        System.out.println(role.toString());
//        List<User> userList = userRoleDao.getAllUsersWithRole(role);
//        for(User user:userList){
//            System.out.println(user.toString());
//        }

//        User user = userDaoImp.findById(23);
//        System.out.println(user.toString());
//        List<Role> roleList = new ArrayList<>();
//        roleList = userRoleDao.getAllUserRoles(user);
//        for(Role role:roleList){
//            System.out.println(role.toString());
//        }


//        //assignRole
//        User user = userDaoImp.findById(15);
//        System.out.println(user.toString());
//        Role role = roleDao.findById(6);
//        System.out.println(role.toString());
//        userRoleDao.assignRole(user,role);
//        List<UserRole> userRoleList = userRoleDao.getAll();
//        for(UserRole userRole: userRoleList){
//            System.out.println(userRole.toString());
//        }


//        //removeRole
//        User user = userDaoImp.findById(10);
//        System.out.println(user.toString());
//        Role role = roleDao.findById(6);
//        System.out.println(role.toString());
//        boolean isRemoved = userRoleDao.removeRole(user,role);
//        System.out.println(isRemoved);
//
//        List<UserRole> userRoleList = userRoleDao.getAll();
//        for(UserRole userRole: userRoleList){
//            System.out.println(userRole.toString());
//        }













    }

    public void test_RoleDaoImp(){
        FactoryDao factoryDao = new FactoryDao();
        RoleDao roleDao = factoryDao.createRoleDao();

        Role role;

//        //create
//        userRole = new UserRole();
//        userRole.setName("accountant");
//        userRole.setNameUA("бухгалтер");
//        System.out.println(userRole.toString());
//        userRoleDao.create(userRole);
//        System.out.println(userRole.toString());

//        //findById
//        userRole = userRoleDao.findById(8);
//        userRole.print();


//        //findAll
//        List<UserRole> userRoleList = new ArrayList<>();
//        userRoleList = userRoleDao.findAll();
//        for(UserRole userRole1:userRoleList){
//            userRole1.print();
//        }


//        //update
//        userRole = userRoleDao.findById(1);
//        userRole.print();
//        userRole.setName("superadmin");
//        userRole.setNameUA("суперадмін");
//        userRoleDao.update(userRole);
//        userRole.print();

//        //update nonexistence role
//        userRole = new UserRole();
//        userRole.setId(99);
//        userRole.setName("99 role");
//        userRole.setNameUA("99 роль");
//        userRole.print();
//        userRole.setName("superadmin");
//        userRole.setNameUA("суперадмін");
//        userRoleDao.update(userRole);
//        userRole.print();


        //delete
        roleDao.delete(5);
    }

    public void test_CarCategoryImp(){
        FactoryDao factoryDao = new FactoryDao();
        CarCategoryDao carCategoryDaoImp = factoryDao.createCarCategoryDao();

        CarCategory carCategory = new CarCategory();


//        //create
//        carCategory.setName("made in ussr2");
//        carCategory.setNameUA("зроблено СРСР");
//
//        System.out.println(carCategory.toString());
//        carCategoryDaoImp.create(carCategory);
//        System.out.println(carCategory.toString());

//
//        //findById
//        carCategory = carCategoryDaoImp.findById(1);
//        System.out.println(carCategory.toString());

//        //findAll()
//        List<CarCategory> carCategoryList = new ArrayList<>();
//        carCategoryList = carCategoryDaoImp.findAll();
//        for(CarCategory carCategory1:carCategoryList){
//            System.out.println(carCategory1.toString());
//        }


//        //update
//        carCategory = carCategoryDaoImp.findById(5);
//        System.out.println(carCategory.toString());
//        carCategory.setNameUA("Зроблено в СРСР");
//        carCategoryDaoImp.update(carCategory);
//        System.out.println(carCategory.toString());


        carCategoryDaoImp.delete(5);





    }

    public void test_GeoPointDaoImp(){
        FactoryDao factoryDao = new FactoryDao();
        GeoPointDao geoPointDaoImp = factoryDao.createGeoPointDao();

        GeoPoint geoPoint = new GeoPoint();


//        //create
//        geoPoint.setName("Test");
//        geoPoint.setLatitude(0.0);
//        geoPoint.setLongitude(0.0);
//        System.out.println(geoPoint.toString());
//        geoPointDaoImp.create(geoPoint);
//        System.out.println(geoPoint.toString());


//        //findById
//        geoPoint = geoPointDaoImp.findById(3);
//        System.out.println(geoPoint.toString());

//        List<GeoPoint> geoPointList = new ArrayList<>();
//        geoPointList = geoPointDaoImp.findAll();
//        for(GeoPoint geoPoint1:geoPointList){
//            System.out.println(geoPoint1.toString());
//        }

//        //update(GeoPoint geoPoint)
//        geoPoint =geoPointDaoImp.findById(1);
//        geoPoint.setLatitude(3.0);
//        geoPoint.setLongitude(3.0);
//        System.out.println(geoPoint.toString());
//        geoPoint.setName("geoPoint 1");
//        geoPointDaoImp.update(geoPoint);
//        System.out.println(geoPoint.toString());

        geoPointDaoImp.delete(3);
    }

    public void test_UserDaoImpl(){
        FactoryDao factoryDao = new FactoryDao();
        UserDao userDaoImp = factoryDao.createUserDao();

        User user;

//        //public User create(User user) {
//        user = new User();
//        user.setLogin("user12");
//        user.setPassword("1111");
//        user.setFirstName("Ivan");
//        user.setLastName("Susanin");
//        user.setEmail("i_susanin@mail.ru");
//        System.out.println(user.toString());
//        user = userDaoImp.create(user);
//        System.out.println(user.toString());
//        for(Role role:user.getRoleList()){
//            System.out.println(role.toString());
//        }


//        user = userDaoImp.findById(23);
//        System.out.println(user.toString());
//        for (Role role : user.getRoleList()) {
//            System.out.println(role.toString());
//        }



//        //userDaoImp.findAll()
//        List<User>  userList = userDaoImp.findAll();
//        for(User user1:userList){
//            System.out.println(user1.toString());
//            for (Role role : user1.getRoleList()) {
//                System.out.println(role.toString());
//            }
//        }

//        //userDaoImp.update()
//        user = userDaoImp.findById(23);
//        System.out.println(user.toString());
//        user.setEmail("new email 2");
//        userDaoImp.update(user);
//        System.out.println(user.toString());
//        for (Role role : user.getRoleList()) {
//            System.out.println(role.toString());
//        }




//        userDaoImp.delete(6);


//        user = userDaoImp.findByLoginPassword("admin","123456");
//        System.out.println(user.toString());
//        for (Role role : user.getRoleList()) {
//            System.out.println(role.toString());
//        }



//        user = userDaoImp.findById(1);
//        List<Role> roleList = new ArrayList<>();
//        roleList = userDaoImp.obtainUserRoles(user);
//        for(Role role:roleList){
//            System.out.println(role.toString());
//        }

    }


}
