package com.taxi.model.entity;

import com.taxi.model.dao.ObjectExistenceCheckIn;

import java.time.LocalDateTime;

public class Order implements ObjectExistenceCheckIn {

    private int id;
    private LocalDateTime orderDate;
    private User user;
    private GeoPoint departurePoint;
    private GeoPoint destinationPoint;
    private double distance;
    private double cost;
    private String costCalculation;
    private CarCategory category;
    private int numberOfPassengers;
    private Discount discount;
    private Car car;
    private Tariff tariff;
    private final static String DATA_BASE_TABLE_NAME = "orders";

    public Order() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataBaseTableName(){
        return  DATA_BASE_TABLE_NAME;
    }
}
