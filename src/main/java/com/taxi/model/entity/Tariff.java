package com.taxi.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Tariff {

    private LocalDate effectiveDate;
    private CarCategory category;
    private int fareForCall;
    private int farePerKm;

    public Tariff() {
    }

    public Tariff(LocalDate effectiveDate, CarCategory category, int callFee, int farePerKm) {
        this.effectiveDate = effectiveDate;
        this.category = category;
        this.fareForCall = callFee;
        this.farePerKm = farePerKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return fareForCall == tariff.fareForCall && farePerKm == tariff.farePerKm && Objects.equals(effectiveDate, tariff.effectiveDate) && Objects.equals(category, tariff.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectiveDate, category, fareForCall, farePerKm);
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
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
}
