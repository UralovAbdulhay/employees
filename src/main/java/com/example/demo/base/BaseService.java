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

    E saveOrUpdate(R t);

    List<E> saveOrUpdate(Collection<R> ts);

    E findById(Long id);

    List<E> findAll(int page, int size);

    boolean delete(Long id);

    boolean existById(Long id);

//    @Autowired
//    Validator validator;

//    default <D, S> D validate(S request) {
//
//        Set validationResult = validator.validate(request, SaveValidation.class);
//
//        if (validationResult.size() != 0) {
//            validationResult = validator.validate(request, UpdateValidation.class);
//
//            if (validationResult.size() == 0) {
//                return null;
//            }
//        }
//
//        throw BadRequest.get(validationResult.toString());
//    }

}
