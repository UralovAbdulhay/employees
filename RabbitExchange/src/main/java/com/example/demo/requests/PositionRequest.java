package com.example.demo.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseRequest;
import com.example.demo.entity.Position;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionRequest extends BaseRequest {

    @NotBlank(groups = SaveValidation.class)
    String name;

    @NotNull(groups = {UpdateValidation.class, SaveValidation.class}, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, message = "Id 1 dan kichik bo'lishi mumkin emas")
    Long departmentId;

    public static PositionRequest getInstance(Position position) {
        PositionRequest request = new PositionRequest(position.getName(), position.getDepartment().getId());
        request.setId(request.getId());
        request.setRemote_id(request.getRemote_id());
        return request;
    }

}
