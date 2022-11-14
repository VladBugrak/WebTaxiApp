package com.taxi.model.dao.user_role;

import com.taxi.model.dao.GenericDao;
import com.taxi.model.entity.Role;
import com.taxi.model.entity.User;
import com.taxi.model.entity.UserRole;

import java.util.List;

public interface UserRoleDao extends GenericDao<UserRole> {

  List<Role> getAllUserRoles(User user);
  List<User> getAllUsersWithRole(Role role);
  boolean assignRole(UserRole userRole);
  boolean assignRole(User user,Role role);
  boolean removeRole(UserRole userRole);
  boolean removeRole(User user, Role role);
  void close();
  public boolean removeInvalidRecords();

}
