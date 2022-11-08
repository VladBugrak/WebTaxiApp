package com.taxi.model.dao.tariff;


import com.taxi.model.dao.GenericDao;
import com.taxi.model.entity.CarCategory;
import com.taxi.model.entity.Tariff;

import java.time.LocalDate;

public interface TariffDao extends GenericDao<Tariff> {
    Tariff getTariffOnDate(LocalDate localDate, CarCategory carCategory);

}
