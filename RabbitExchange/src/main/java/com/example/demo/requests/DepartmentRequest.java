package com.example.demo.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseRequest;
import com.example.demo.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)

public class DepartmentRequest extends BaseRequest {

    @NotBlank(groups = {SaveValidation.class, UpdateValidation.class})
    String name;


    public static DepartmentRequest getInstance(Department department) {
        DepartmentRequest request = new DepartmentRequest(department.getName());
        request.setId(department.getId());
        return request;
    }


}
