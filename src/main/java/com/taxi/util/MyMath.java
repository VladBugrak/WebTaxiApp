package com.taxi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyMath {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double roundToTwoDecimalPlaces(double value){
        return round(value,2);
    }

}
