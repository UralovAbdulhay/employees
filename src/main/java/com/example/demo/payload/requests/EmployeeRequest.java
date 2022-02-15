package com.example.demo.payload.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest extends BaseRequest {


    @NotBlank(groups = SaveValidation.class)
    String name;

    @NotBlank(groups = SaveValidation.class)
    String sureName;

    @NotNull(groups = SaveValidation.class)
    @PastOrPresent(groups = SaveValidation.class)
    LocalDate birthDate;

    @NotNull(groups = UpdateValidation.class, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = UpdateValidation.class, message = "Id 1 dan kichik bo'lishi mumkin emas")
    Long positionId;


}
