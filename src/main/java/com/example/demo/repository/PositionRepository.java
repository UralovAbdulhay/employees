package com.example.demo.repository;

import com.example.demo.base.BaseRepository;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Position;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("position")
public interface PositionRepository extends BaseRepository<Position> {

}
