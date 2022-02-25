package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Getter
@Setter
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDateTime birthDate;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JsonIgnore
//    InStorageFile image;


    @ManyToOne
    @JoinColumn(nullable = false)
    Position position;


    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", sureName='" + sureName + '\'' +
                ", birthDate=" + birthDate +
                ", position=" + position +
                ", id=" + id +
                '}';
    }
}
