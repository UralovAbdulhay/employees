package com.example.demo.payload.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest extends BaseRequest {


    @NotBlank(groups = SaveValidation.class)
    String name;

    @NotBlank(groups = SaveValidation.class)
    String sureName;

    @NotNull(groups = SaveValidation.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @PastOrPresent(groups = {SaveValidation.class, UpdateValidation.class})
    LocalDateTime birthDate;

    @NotNull(groups = SaveValidation.class, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = {SaveValidation.class, UpdateValidation.class}, message = "Id 1 dan kichik bo'lishi mumkin emas")
    Long positionId;


}
