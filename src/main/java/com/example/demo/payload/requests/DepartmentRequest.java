package com.example.demo.payload.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseRequest;
import com.example.demo.entity.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)

public class DepartmentRequest extends BaseRequest implements Serializable {

    @NotBlank(groups = {SaveValidation.class, UpdateValidation.class})
    String name;


    public static DepartmentRequest getInstance(Department department) {
        DepartmentRequest request = new DepartmentRequest(department.getName());
        request.setId(department.getId());
        request.setRemote_id(department.getRemote_id());
        return request;
    }

    @Override
    public String toString() {
        return "DepartmentRequest{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", remote_id=" + remote_id +
                '}';
    }
}
