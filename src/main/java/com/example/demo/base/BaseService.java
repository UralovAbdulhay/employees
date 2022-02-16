package com.example.demo.base;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface BaseService<T> {

    T saveOrUpdate(T t);

    List<T> saveOrUpdate(Collection<T> ts);

    T findById(Long id);

    List<T> findAll(int page, int size);

    boolean delete(Long id);

    boolean existById(Long id);

}
