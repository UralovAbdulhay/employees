package com.example.demo.payload.requests;

import com.example.demo.entity.basicEntity.AttendanceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest {

    Long employeeId;


    @NotNull
    AttendanceType type;


    @NotNull
    @PastOrPresent
    LocalDateTime eventTime;


}
