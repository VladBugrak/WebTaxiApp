package com.taxi.model.dao.order;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.taxi.model.entity.Order;

import java.sql.Connection;
import java.util.List;

public class OrderDaoImp implements OrderDao{

    private Connection connection;

    protected OrderDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean update(Order order) {
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
