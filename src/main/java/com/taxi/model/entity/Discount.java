package com.taxi.model.entity;

import java.time.LocalDate;

public class Discount {

    //редактувати можна лише дані з датою в майбутньому часі. Новий дані мають вступати в
    //силу не раніше як через годину з їх дати створення
    //У всіх розрахунках сума округляється до гривень.

    private LocalDate effectiveDate;
    private int sumFrom;
    private int grade;
    private int discountPercent;

}
