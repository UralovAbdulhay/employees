package com.example.demo.base;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service

public interface BaseService<E, R> {

    E save(R request);

    E update(R request);

    default List<E> saveAll(Collection<R> requests) {
        return requests.stream().map(this::save).collect(Collectors.toList());
    }

    default List<E> updateAll(Collection<R> requests) {
        return requests.stream().map(this::update).collect(Collectors.toList());
    }

    E findById(Long id);

    List<E> findAll(int page, int size);

    boolean delete(Long id);

    boolean isDeleted(Long id);

    boolean existById(Long id);

    boolean isValidForUpdate(R request);

    boolean isValidForSave(R request);

    R convertToPayload(E entity);

    default List<R> convertToPayload(List<E> entities) {
        return entities
                .stream()
                .map(this::convertToPayload)
                .collect(Collectors.toList());
    }


}
