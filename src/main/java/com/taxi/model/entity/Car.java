package com.taxi.model.entity;

import java.util.Objects;

public class Car {

    private int id;
    private String plateNumber;
    private String model;
    private String color;
    private CarCategory carCategory;
    private int capacity;

    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && capacity == car.capacity && Objects.equals(plateNumber, car.plateNumber) && Objects.equals(model, car.model) && Objects.equals(color, car.color) && Objects.equals(carCategory, car.carCategory) && Objects.equals(image, car.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plateNumber, model, color, carCategory, capacity, image);
    }

    public Car() {
    }

    public Car(int id, String plateNumber, String model, String color, CarCategory carCategory, int capacity, String image) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.model = model;
        this.color = color;
        this.carCategory = carCategory;
        this.capacity = capacity;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", plateNumber='" + plateNumber + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", carCategory=" + carCategory +
                ", capacity=" + capacity +
                ", image='" + image + '\'' +
                '}';
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
