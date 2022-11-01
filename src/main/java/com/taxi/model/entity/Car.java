package com.taxi.model.entity;

import java.util.Objects;

public class Car {

    private int id;
    private String plateNumber;
    private String model;
    private String color;
    private String colorUA;
    private CarCategory carCategory;
    private int capacity;

    private String imageLink;

    public Car() {
    }

    public Car(int id, String plateNumber, String model, String color, String colorUA, CarCategory carCategory, int capacity, String imageLink) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.model = model;
        this.color = color;
        this.colorUA = colorUA;
        this.carCategory = carCategory;
        this.capacity = capacity;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", imageLink='" + imageLink + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id
                && capacity == car.capacity
                && Objects.equals(plateNumber, car.plateNumber)
                && Objects.equals(model, car.model)
                && Objects.equals(color, car.color)
                && Objects.equals(colorUA, car.colorUA)
                && Objects.equals(carCategory, car.carCategory)
                && Objects.equals(imageLink, car.imageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plateNumber, model, color, colorUA, carCategory, capacity, imageLink);
    }
}
