package com.example.demo.entity.other;

import com.example.demo.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExportedFile extends BaseEntity {

    @Column(nullable = false)
    String filePath;


}
