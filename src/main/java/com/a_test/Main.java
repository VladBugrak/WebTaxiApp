package com.a_test;

import com.taxi.model.dao.FactoryDao;
import com.taxi.model.dao.GeoPointDao;
import com.taxi.model.dao.UserDao;
import com.taxi.model.entity.GeoPoint;
import com.taxi.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


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
