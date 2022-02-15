package com.example.demo.payload.payloadForValodition;

import com.example.demo.entity.basicEntity.AttendanceType;
import com.example.demo.payload.requests.BaseRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequestValid extends BaseRequest {

    @NotNull
    @Min(0)
    Long employeeId;

    @NotNull
    AttendanceType type;

    LocalDateTime eventTime;

}

