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

@Service
@RequiredArgsConstructor
public abstract class BaseServiceImpl<D extends BaseEntity, S extends BaseRequest> implements BaseService<D, S> {

    private final Validator validator;

    private final BaseRepository<D> repository;

    protected D validate(D entity, S request) {
        // Save qilish uchun validate qilish
        Set saveValidationResult = validator.validate(request, SaveValidation.class);
        // Update qilish uchun validate qilish
        Set updateValidationResult = validator.validate(request, UpdateValidation.class);


        if (saveValidationResult.size() == 0) {
            ObjectParser.copyFieldsIgnoreNulls(entity, request, true);
            repository.save(entity);
            return entity;
        }
        if (updateValidationResult.size() == 0) {
            final BaseEntity entity1 = entity;
            entity = repository.findById(request.getId()).orElseThrow(() -> ResourceNotFound.get(entity1.getClass().getName(), "id", entity1.getId()));
            ObjectParser.copyFieldsIgnoreNulls(entity, request, true);
            repository.save(entity);
            return entity;
        }

        throw BadRequest.get(updateValidationResult.toString() + "/n" + saveValidationResult.toString());


    }


}
