package com.taxi.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Order {

    private LocalDateTime orderDate;
    private User user;
    private GeoPoint departurePoint;
    private GeoPoint destinationPoint;
    private double distance;
    private int cost;
    private String costCalculation;
    private CarCategory category;
    private int numberOfPassengers;
    private Car car;

//    public enum Status{CAR_SEARCHING,CAR_ASSIGNED,CAR_FILED,,
//
//    }

}
