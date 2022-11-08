package com.taxi.model.dao.discount;

import com.taxi.model.dao.GenericDao;
import com.taxi.model.entity.Discount;

import java.time.LocalDate;
import java.util.List;

public interface DiscountDao extends GenericDao<Discount> {

    List<Discount> getValidDiscountsOnDate(LocalDate localDate);

    Discount getValidDiscountOnDateAndSum(LocalDate localDate,double sum);

}
