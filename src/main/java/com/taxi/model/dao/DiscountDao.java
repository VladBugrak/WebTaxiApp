package com.taxi.model.dao;

import com.taxi.model.entity.Discount;

import java.time.LocalDate;
import java.util.List;

public interface DiscountDao extends GenericDao<Discount>{

    List<Discount> getValidDiscountsOnDate(LocalDate localDate);

    Discount getValidDiscountOnDateAndSum(LocalDate localDate,double sum);

}
