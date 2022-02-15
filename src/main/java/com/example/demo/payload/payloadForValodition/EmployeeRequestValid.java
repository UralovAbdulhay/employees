package com.example.demo.payload.payloadForValodition;

import com.example.demo.payload.requests.BaseRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequestValid extends BaseRequest{


    String name;

    String sureName;

    LocalDate birthDate;

    Long positionId;


}
