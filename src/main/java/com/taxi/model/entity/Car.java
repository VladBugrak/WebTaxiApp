package com.taxi.model.entity;

import com.taxi.model.dao.ObjectExistenceCheckIn;

import java.util.Objects;

public class Car implements ObjectExistenceCheckIn {


    private int id;
    private String plateNumber;
    private String model;
    private String color;
    private String colorUA;
    private CarCategory carCategory;
    private int capacity;

    private CarStatus carStatus;

    private String imageLink;

    private final static String DATA_BASE_TABLE_NAME = "cars";



    public Car() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDataBaseTableName(){
        return  DATA_BASE_TABLE_NAME;
    }



    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorUA() {
        return colorUA;
    }

    public void setColorUA(String colorUA) {
        this.colorUA = colorUA;
    }

    public CarCategory getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", plateNumber='" + plateNumber + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", colorUA='" + colorUA + '\'' +
                ", carCategory=" + carCategory +
                ", capacity=" + capacity +
                ", carStatus=" + carStatus +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && capacity == car.capacity && plateNumber.equals(car.plateNumber) && model.equals(car.model) && color.equals(car.color) && colorUA.equals(car.colorUA) && carCategory.equals(car.carCategory) && carStatus.equals(car.carStatus) && Objects.equals(imageLink, car.imageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plateNumber, model, color, colorUA, carCategory, capacity, carStatus, imageLink);
    }
}