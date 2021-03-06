package com.example.demo.requests;

import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseRequest;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.types.AttendanceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest extends BaseRequest {

    @NotNull(groups = {UpdateValidation.class, SaveValidation.class}, message = "Id null bo'lishi mumkin emas")
    @Min(value = 1, groups = {UpdateValidation.class, SaveValidation.class}, message = "Id 1 dan kichik bo'lishi mumkin emas")
    Long employeeId;

    @NotNull(groups = SaveValidation.class)
    AttendanceType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @NotNull(groups = SaveValidation.class)
    @PastOrPresent(groups = {SaveValidation.class, UpdateValidation.class})
    LocalDateTime eventTime;


    public static AttendanceRequest getInstance(Attendance attendance) {
        AttendanceRequest request = new AttendanceRequest(attendance.getEmployeeId(), attendance.getType(), attendance.getEventTime());
        request.setId(attendance.getId());
        request.setRemote_id(attendance.getRemote_id());

        return request;
    }



}

