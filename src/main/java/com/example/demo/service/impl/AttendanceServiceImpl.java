package com.example.demo.service.impl;

import com.example.demo.entity.Attendance;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;


    @Override
    public Attendance saveOrUpdate(Attendance attendance) {
//        attendanceRepository.save();
        return null;
    }

    @Override
    public List<Attendance> saveOrUpdate(Collection<Attendance> attendances) {
        return null;
    }

    @Override
    public Attendance findById(Long id) {
        return null;
    }

    @Override
    public List<Attendance> findAll(int page, int size) {
        return null;
    }


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }
}
