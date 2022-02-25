package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position extends BaseEntity {

    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    List<Employee> employees;

    @ManyToOne
    @JoinColumn(nullable = false)
    Department department;

}
