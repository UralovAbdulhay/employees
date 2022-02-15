package com.example.demo.base;

import java.util.Collection;
import java.util.List;

public interface BaseService<T> {

    T saveOrUpdate(T t);

    List<T> saveOrUpdate(Collection<T> ts);

    T findById(Long id);

    List<T> findAll(int page, int size);

    boolean delete(Long id);

    boolean existById(Long id);

}
