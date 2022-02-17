package com.example.demo.base;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service

public interface BaseService<E, R> {

    E save(R t);

    E update(R t);

    List<E> saveAll(Collection<R> ts);

    List<E> updateAll(Collection<R> ts);

    E findById(Long id);

    List<E> findAll(int page, int size);

    boolean delete(Long id);

    boolean existById(Long id);

    boolean isValidForUpdate(R r);

    boolean isValidForSave(R r);

}
