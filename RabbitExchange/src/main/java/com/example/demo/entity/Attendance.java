package com.example.demo.entity;

import com.example.demo.entity.types.AttendanceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Document(indexName = "attendance")
//@Setting(settingPath = "static/")
public class Attendance {

    @Id
    @Field(type = FieldType.Keyword)
    UUID id;

    @Field(type = FieldType.Text)
    AttendanceType type;

    @Field(type = FieldType.Date)
    LocalDateTime eventTime;

    @Field(type = FieldType.Long)
    Long employeeId;


}
