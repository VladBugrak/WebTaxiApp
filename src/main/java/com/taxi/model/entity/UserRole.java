package com.taxi.model.entity;

import java.util.Objects;

public class UserRole {

    private User user;
    private Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(user, userRole.user) && Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "user=" + user +
                ", role=" + role +
                '}';
    }
}
