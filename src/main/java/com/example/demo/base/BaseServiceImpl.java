package com.example.demo.base;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Attendance;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Set;


@RequiredArgsConstructor
public abstract class BaseServiceImpl<D extends BaseEntity, S extends BaseRequest> implements BaseService<D, S> {

    private final Validator validator;

    private final BaseRepository<D> repository;

    protected D validate(D entity, S request) {
        // Save qilish uchun validate qilish
        Set saveValidationResult = validator.validate((BaseRequest) request, SaveValidation.class);
        // Update qilish uchun validate qilish
        Set updateValidationResult = validator.validate((BaseRequest) request, UpdateValidation.class);

        System.out.println("save validator = " + saveValidationResult);
        System.out.println("update validator = " + updateValidationResult);

        if (saveValidationResult.size() == 0) {

            System.out.println("Validate save = " + request);
            ObjectParser.copyFieldsIgnoreNulls(entity, request, true);
            repository.save(entity);
            System.out.println(entity);
            return entity;
        }
        if (updateValidationResult.size() == 0) {
            System.out.println("Validate Update = " + request);

            final BaseEntity entity1 = entity;
            entity = repository.findById(request.getId()).orElseThrow(() -> ResourceNotFound.get(entity1.getClass().getName(), "id", entity1.getId()));
            ObjectParser.copyFieldsIgnoreNulls(entity, request, true);
            repository.save(entity);
            System.out.println(entity);
            return entity;
        }



        throw BadRequest.get(updateValidationResult.toString() + "/n" + saveValidationResult.toString());


    }


}
