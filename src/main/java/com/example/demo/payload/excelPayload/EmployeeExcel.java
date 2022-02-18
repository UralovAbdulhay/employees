package com.example.demo.payload.excelPayload;

import com.example.demo.entity.Position;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class EmployeeExcel {


    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String sureName;

    @Column(nullable = false)
    LocalDate birthDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    Position position;


}
