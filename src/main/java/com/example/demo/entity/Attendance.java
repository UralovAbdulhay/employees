package com.example.demo.entity;

import com.example.demo.entity.basicEntity.AttendanceType;
import com.example.demo.entity.basicEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance extends BaseEntity {

    @OneToOne
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    AttendanceType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(nullable = false, name = "event_time")
    LocalDateTime eventTime;

    @OneToOne
    @Column(nullable = false)
    Employee employee;

    @Column(name = "active", nullable = false)
    boolean isActive = true;

}
