package com.taxi.model.dao;


import com.taxi.model.entity.CarCategory;
import com.taxi.model.entity.Tariff;

import java.time.LocalDate;

public interface TariffDao extends GenericDao<Tariff> {
    Tariff getTariffOnDate(LocalDate localDate, CarCategory carCategory);

}
