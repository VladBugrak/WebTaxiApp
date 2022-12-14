package com.taxi.model.entity;


import com.taxi.model.dao.ObjectExistenceCheckIn;
import com.taxi.util.MyMath;
import java.time.LocalDate;
import java.util.Objects;

public class Discount implements ObjectExistenceCheckIn {


    private int id;
    private LocalDate effectiveDate;
    private int discountLevel;
    private double totalSumCondition;
    private double discountPercentage;

    final int MAX_DISCOUNT_LEVEL = 10;

    private final static String DATA_BASE_TABLE_NAME = "discounts";

    public Discount() {
    }

    public Discount(LocalDate effectiveDate, int discountLevel, double totalSumCondition, double discountPercentage) {
        this.effectiveDate = effectiveDate;
        this.discountLevel = discountLevel;
        this.totalSumCondition = totalSumCondition;
        this.discountPercentage = discountPercentage;
    }

    public String getDataBaseTableName(){
        return  DATA_BASE_TABLE_NAME;
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
        if(discountLevel < 1 || discountLevel > MAX_DISCOUNT_LEVEL){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("You've tried to set ")
                    .append(discountLevel)
                    .append(" as the discount level. ")
                    .append("The discount level must be in the range of integer values from 1 to ")
                    .append(MAX_DISCOUNT_LEVEL)
                    .append(" inclusive.")
            ;
            throw new IllegalArgumentException(stringBuffer.toString());
        }


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
