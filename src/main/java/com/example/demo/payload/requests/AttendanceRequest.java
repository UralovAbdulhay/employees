package com.example.demo.payload.requests;

import com.example.demo.entity.basicEntity.AttendanceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest extends BaseRequest {

    Long employeeId;


    @NotNull
    AttendanceType type;


    @NotNull
    @PastOrPresent
    LocalDateTime eventTime;


}

