package com.example.demo.tests.others;

import com.google.gson.Gson;

import javax.validation.Validation;
import javax.validation.Validator;


public class ValidationTest {


    public static void main(String[] args) {
        ValidationPayload payload1 = new ValidationPayload("Abdulhay", "Java", 23);
        ValidationPayload payload2 = new ValidationPayload();

        Gson gson = new Gson();

        System.out.println(gson.toJson(payload1));
        System.out.println(gson.toJson(payload2));

        ValidationPayload payload = gson.fromJson(gson.toJson(payload2), ValidationPayload.class);

        System.out.println(payload);

        long l = System.currentTimeMillis();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        System.out.println(System.currentTimeMillis() - l);

        System.out.println(validator.validate(payload1));

    }

}
