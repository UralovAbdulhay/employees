package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends BaseEntity {

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
