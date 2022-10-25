package com.taxi.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
    T create (T entity);
    T findById(int id);
    List<T> findAll();
    boolean update(T entity);
    boolean delete(int id);
    void close();
}
