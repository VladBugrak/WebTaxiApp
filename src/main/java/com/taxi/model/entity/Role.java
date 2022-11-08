package com.taxi.model.entity;

import com.taxi.model.dao.ObjectExistenceCheckIn;

import java.util.Objects;

public class Role implements ObjectExistenceCheckIn {

    private int id;
    private String name;
    private String nameUA;

    private final static String DATA_BASE_TABLE_NAME = "roles";

    public Role() {
    }

    public Role(int id, String name, String nameUA) {
        this.id = id;
        this.name = name;
        this.nameUA = nameUA;
    }
    public String getDataBaseTableName(){
        return  DATA_BASE_TABLE_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUA() {
        return nameUA;
    }

    public void setNameUA(String nameUA) {
        this.nameUA = nameUA;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameUA='" + nameUA + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(name, role.name) && Objects.equals(nameUA, role.nameUA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameUA);
    }

    public void print(){
        System.out.println(toString());
    }

}
