package com.taxi.model.entity;

import com.taxi.util.MyMath;

import java.time.LocalDate;
import java.util.Objects;

public class Tariff {

    private int id;
    private LocalDate effectiveDate;
    private CarCategory carCategory;
    private double fareForCall;
    private double farePerKm;

    public Tariff() {
    }

    public Tariff(int id, LocalDate effectiveDate, CarCategory carCategory, double fareForCall, double farePerKm) {
        this.id = id;
        this.effectiveDate = effectiveDate;
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

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
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
        this.fareForCall = MyMath.roundToTwoDecimalPlaces(fareForCall);
    }

    public double getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(double farePerKm) {
        this.farePerKm = MyMath.roundToTwoDecimalPlaces(farePerKm);
    }


    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", effectiveData=" + effectiveDate +
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
        return id == tariff.id && Double.compare(tariff.fareForCall, fareForCall) == 0 && Double.compare(tariff.farePerKm, farePerKm) == 0 && Objects.equals(effectiveDate, tariff.effectiveDate) && Objects.equals(carCategory, tariff.carCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, effectiveDate, carCategory, fareForCall, farePerKm);
    }
}