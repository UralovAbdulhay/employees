package com.example.demo.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    protected Long remote_id;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    protected LocalDateTime createAt;


    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updateAt;


    @JsonIgnore
    @Column(name = "is_active", nullable = false)
    protected boolean isActive = true;


}
