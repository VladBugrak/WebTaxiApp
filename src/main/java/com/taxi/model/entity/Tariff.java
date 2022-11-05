package com.taxi.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tariff {

    private int id;
    private LocalDate effectiveData;
    private CarCategory carCategory;
    private double fareForCall;
    private double farePerKm;

    public Tariff() {
    }

    public Tariff(int id, LocalDate effectiveData, CarCategory carCategory, double fareForCall, double farePerKm) {
        this.id = id;
        this.effectiveData = effectiveData;
        this.carCategory = carCategory;
        this.fareForCall = fareForCall;
        this.farePerKm = farePerKm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getEffectiveData() {
        return effectiveData;
    }

    public void setEffectiveData(LocalDate effectiveData) {
        this.effectiveData = effectiveData;
    }

    public CarCategory getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public double getFareForCall() {
        return fareForCall;
    }

    public void setFareForCall(double fareForCall) {
        this.fareForCall = fareForCall;
    }

    public double getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(double farePerKm) {
        this.farePerKm = farePerKm;
    }


    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", effectiveData=" + effectiveData +
                ", carCategory=" + carCategory +
                ", fareForCall=" + fareForCall +
                ", farePerKm=" + farePerKm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return id == tariff.id && Double.compare(tariff.fareForCall, fareForCall) == 0 && Double.compare(tariff.farePerKm, farePerKm) == 0 && Objects.equals(effectiveData, tariff.effectiveData) && Objects.equals(carCategory, tariff.carCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, effectiveData, carCategory, fareForCall, farePerKm);
    }
}