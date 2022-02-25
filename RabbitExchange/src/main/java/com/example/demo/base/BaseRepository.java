package com.example.demo.base;

import org.springframework.data.jpa.repository.JpaRepository;

//@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
