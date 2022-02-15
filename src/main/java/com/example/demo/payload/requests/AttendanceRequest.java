package com.example.demo.payload.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.basicEntity.AttendanceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest extends BaseRequest {

    @NotNull(groups = UpdateValidation.class, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = UpdateValidation.class, message = "Id 1 dan kichik bo'lishi mumkin emas")
    Long employeeId;

    @NotNull(groups = SaveValidation.class)
    AttendanceType type;

    @NotNull(groups = SaveValidation.class)
    @PastOrPresent(groups = SaveValidation.class)
    LocalDateTime eventTime;

}

