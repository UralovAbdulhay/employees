package com.example.demo.entity;

import com.example.demo.entity.basicEntity.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department extends BaseEntity {

    @Column(nullable = false)
    String name;

    @OneToMany(mappedBy = "department")
    List<Position> positions;

}
