package com.taxi.model.dao;

import com.taxi.model.entity.Tariff;

import java.sql.Connection;
import java.util.List;

public class TariffDaoImp implements TariffDao{

    private Connection connection;

    TariffDaoImp(Connection connection) {

        this.connection = connection;
    }

    @Override
    public Tariff create(Tariff entity) {
        return null;
    }

    @Override
    public Tariff findById(int id) {
        return null;
    }

    @Override
    public List<Tariff> findAll() {
        return null;
    }

    @Override
    public boolean update(Tariff entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public void close() {

    }
}
