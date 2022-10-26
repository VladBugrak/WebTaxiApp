package com.a_test;

import com.taxi.model.dao.FactoryDao;
import com.taxi.model.dao.UserDao;
import com.taxi.model.dao.UserDaoImpl;
import com.taxi.model.entity.User;

public class Main {

    public static void main(String[] args) {

        FactoryDao factoryDao = new FactoryDao();
        UserDao userDaoImp = factoryDao.createUserDao();

//        User user = new User();
//        user.setLogin("user6");
//        user.setPassword("1111");
//        user.setFirstName("Ivan");
//        user.setLastName("Susanin");
//        user.setEmail("i_susanin@mail.ru");
//        System.out.println(user.toString());

        User user1 = userDaoImp.findById(14);
        System.out.println(user1.toString());





    }

}
