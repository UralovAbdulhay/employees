package com.example.demo.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseRequest;
import com.example.demo.entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @PastOrPresent(groups = {SaveValidation.class, UpdateValidation.class})
    LocalDateTime birthDate;

    @NotNull(groups = SaveValidation.class, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = {SaveValidation.class, UpdateValidation.class}, message = "Id 1 dan kichik bo'lishi mumkin emas")
    Long positionId;


    public static EmployeeRequest getInstance(Employee employee) {
//        String imgId = employee.getImage() != null ? employee.getImage().getHashId() : null;
//
        EmployeeRequest request = new EmployeeRequest(employee.getName(), employee.getSureName(), employee.getBirthDate(), employee.getPosition().getId());
        request.setId(employee.getId());
        request.setRemote_id(employee.getRemote_id());
        return null;
    }


    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "name='" + name + '\'' +
                ", sureName='" + sureName + '\'' +
                ", birthDate=" + birthDate +
                ", positionId=" + positionId +
                ", id=" + id +
                '}';
    }
}
