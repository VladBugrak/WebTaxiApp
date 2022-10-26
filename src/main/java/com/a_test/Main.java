package com.a_test;

import com.taxi.model.dao.FactoryDao;
import com.taxi.model.dao.UserDao;
import com.taxi.model.dao.UserDaoImpl;
import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        FactoryDao factoryDao = new FactoryDao();
        UserDao userDaoImp = factoryDao.createUserDao();

        User user;

//        //public User create(User user) {
//        User user = new User();
//        user.setLogin("user8");
//        user.setPassword("1111");
//        user.setFirstName("Ivan");
//        user.setLastName("Susanin");
//        user.setEmail("i_susanin@mail.ru");
//        System.out.println(user.toString());
//        user = userDaoImp.create(user);
//        System.out.println(user.toString());



//        user = userDaoImp.findById(17);
//        System.out.println(user.toString());
//        user.setEmail("new email");
//        userDaoImp.update(user);
//        user = userDaoImp.findById(17);
//        System.out.println(user.toString());



//        //userDaoImp.findAll()
//        List<User>  userList = userDaoImp.findAll();
//        for(User user1:userList){
//            System.out.println(user1.toString());
//        }


//        userDaoImp.delete(14);


//        user = userDaoImp.findByLoginPassword("admin","123456");
//        System.out.println(user.toString());

        user = userDaoImp.findById(1);

        List<Role> roleList = new ArrayList<>();
        roleList = userDaoImp.obtainUserRoles(user);

        for(Role role:roleList){
            System.out.println(role.toString());
        }







    }

}
