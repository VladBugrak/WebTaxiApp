package com.taxi.model.entity;

import com.taxi.model.dao.ObjectExistenceCheckIn;

import java.util.Objects;

public class GeoPoint implements ObjectExistenceCheckIn {

    private int id;
    private String name;
    private Double latitude;
    private Double longitude;

    private final static String DATA_BASE_TABLE_NAME = "geolocation_point";

    public GeoPoint() {
    }

    public GeoPoint(int id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "GeolocationPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPoint that = (GeoPoint) o;
        return id == that.id
                && Objects.equals(name, that.name)
                && Objects.equals(latitude, that.latitude)
                && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude);
    }
}
