package com.taxi.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tariff {

    private CarCategory category;
    private int fareForCall;
    private int farePerKm;

    public Tariff() {
    }

    public Tariff(CarCategory category, int fareForCall, int farePerKm) {
        this.category = category;
        this.fareForCall = fareForCall;
        this.farePerKm = farePerKm;
    }

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }

    public int getFareForCall() {
        return fareForCall;
    }

    public void setFareForCall(int fareForCall) {
        this.fareForCall = fareForCall;
    }

    public int getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(int farePerKm) {
        this.farePerKm = farePerKm;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "category=" + category +
                ", fareForCall=" + fareForCall +
                ", farePerKm=" + farePerKm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return fareForCall == tariff.fareForCall && farePerKm == tariff.farePerKm && Objects.equals(category, tariff.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, fareForCall, farePerKm);
    }
}