package com.haulmont.testtask.dao;

import java.util.List;

public interface DaoServices<E> {

    void save(E e);

    void update(E e);

    void delete(E e);

    E getById (Long id);

    List<E> findAll();
}
