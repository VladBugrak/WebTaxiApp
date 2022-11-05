package com.taxi.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateTimeUtil {

    public static Timestamp now(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return Timestamp.valueOf(localDateTime);
    }

//    public static Date getStartOfDayDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DATE);
//        calendar.set(year, month, day, 0, 0, 0);
//        return calendar.getTime();
//
//    }


    LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime);
////        LocalDate localDate = localDateTime.toLocalDate();
////        LocalTime localTime = localDateTime.toLocalTime();
////
////
////        java.sql.Date date = java.sql.Date.valueOf(localDate);
////        java.sql.Time time = java.sql.Time.valueOf(localTime);
//
//
//        Timestamp timestamp = Timestamp.valueOf(localDateTime);
//        System.out.println(timestamp);
//
//        LocalDateTime localDateTime1 = timestamp.toLocalDateTime();
//
//        System.out.println(localDateTime1);




}
