package com.example.demo.payload.requests;

import com.example.demo.entity.basicEntity.AttendanceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest extends BaseRequest {

    Long employeeId;

    AttendanceType type;

    LocalDateTime eventTime;


}

