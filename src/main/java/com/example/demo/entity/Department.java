package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    List<Position> positions;

}
