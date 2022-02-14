package com.example.demo.service;

import java.util.Collection;
import java.util.List;

public interface BaseService<T> {

    T save(T t);

    List<T> save(Collection<T> ts);

    T findById(Long id);

    List<T> findAll(int page, int size);

    T update(T t, Long id);

    boolean delete(Long id);

    boolean existById(Long id);

}
