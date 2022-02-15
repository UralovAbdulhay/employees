package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position extends BaseEntity {

    String name;

    @OneToMany(mappedBy = "position")
    List<Employee> employees;

    @ManyToOne
    Department department;

}
