package com.taxi.model.entity;

import java.util.Objects;

public class Color {
    private int id;
    private String nameEng;
    private String nameUa;

    public Color() {
    }

    public Color(int id, String nameEng, String nameUa) {
        this.id = id;
        this.nameEng = nameEng;
        this.nameUa = nameUa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", nameEng='" + nameEng + '\'' +
                ", nameUa='" + nameUa + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return id == color.id
                && nameEng.equals(color.nameEng)
                && nameUa.equals(color.nameUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameEng, nameUa);
    }
}
