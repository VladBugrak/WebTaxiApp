package com.taxi.model.entity;


import com.taxi.util.MyMath;
import java.time.LocalDate;
import java.util.Objects;

public class Discount {

    private int id;
    private LocalDate effectiveDate;
    private int discountLevel;
    private double totalSumCondition;
    private double discountPercentage;

    public Discount() {
    }

    public Discount(LocalDate effectiveDate, int discountLevel, double totalSumCondition, double discountPercentage) {
        this.effectiveDate = effectiveDate;
        this.discountLevel = discountLevel;
        this.totalSumCondition = totalSumCondition;
        this.discountPercentage = discountPercentage;
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

    public int getDiscountLevel() {
        return discountLevel;
    }

    public void setDiscountLevel(int discountLevel) {
        this.discountLevel = discountLevel;
    }

    public double getTotalSumCondition() {
        return totalSumCondition;
    }

    public void setTotalSumCondition(double totalSumCondition) {
        this.totalSumCondition = MyMath.roundToTwoDecimalPlaces(totalSumCondition);
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = MyMath.roundToTwoDecimalPlaces(discountPercentage);
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", effectiveDate=" + effectiveDate +
                ", discountLevel=" + discountLevel +
                ", totalSumCondition=" + totalSumCondition +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return id == discount.id && discountLevel == discount.discountLevel && Double.compare(discount.totalSumCondition, totalSumCondition) == 0 && Double.compare(discount.discountPercentage, discountPercentage) == 0 && Objects.equals(effectiveDate, discount.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, effectiveDate, discountLevel, totalSumCondition, discountPercentage);
    }
}
