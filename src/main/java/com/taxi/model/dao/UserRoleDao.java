package com.taxi.model.dao;

import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;
import com.taxi.model.entity.UserRole;

import java.util.List;

public interface UserRoleDao extends AutoCloseable {

  List<Role> getAllUserRoles(User user);
  List<User> getAllUsersWithRole(Role role);
  List<UserRole> getAll();
  boolean assignRole(UserRole userRole);

  boolean assignRole(User user,Role role);
  boolean removeRole(UserRole userRole);

  boolean removeRole(User user, Role role);

  void close();


  boolean roleExists(int roleID);

  boolean userExists(int userID);




}
