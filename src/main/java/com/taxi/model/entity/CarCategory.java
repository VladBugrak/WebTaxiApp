package com.taxi.model.entity;

import java.util.Objects;

public class CarCategory {
    private int id;
    private String name;
    private String nameUA;

    public CarCategory() {
    }

    public CarCategory(int id, String name, String nameUA) {
        this.id = id;
        this.name = name;
        this.nameUA = nameUA;
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
        return "CarCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameUA='" + nameUA + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarCategory that = (CarCategory) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(nameUA, that.nameUA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameUA);
    }
}
