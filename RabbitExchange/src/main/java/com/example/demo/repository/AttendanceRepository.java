package com.example.demo.repository;

import com.example.demo.base.BaseRepository;
import com.example.demo.entity.Attendance;

import java.util.List;


public interface AttendanceRepository extends BaseRepository<Attendance> {


    List<Attendance> findAllByEmployeeId(Long employee_id);
}
