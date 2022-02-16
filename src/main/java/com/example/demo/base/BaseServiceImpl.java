package com.example.demo.base;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.exceptions.BadRequest;
import com.google.gson.internal.$Gson$Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public abstract class BaseServiceImpl {

    private final Validator validator;

    protected <D, S> D validate(S request) {

        Set validationResult = validator.validate(request, SaveValidation.class);

        if (validationResult.size() != 0) {
            validationResult = validator.validate(request, UpdateValidation.class);

            if (validationResult.size() == 0) {
                return null;
            }
        }

        throw BadRequest.get(validationResult.toString());
    }

}
