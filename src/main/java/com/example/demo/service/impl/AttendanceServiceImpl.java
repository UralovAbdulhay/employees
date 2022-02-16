package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceImpl;
import com.example.demo.entity.Attendance;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, AttendanceRequest> implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(Validator validator, AttendanceRepository attendanceRepository) {
        super(validator, attendanceRepository);
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Attendance saveOrUpdate(AttendanceRequest request) {
        return validate(new Attendance(), request);
    }

    @Override
    public List<Attendance> saveOrUpdate(Collection<AttendanceRequest> requests) {
        return requests.stream().map(this::saveOrUpdate).collect(Collectors.toList());
    }

    @Override
    public Attendance findById(@Valid @NotNull @Min(1) Long id) {
        return attendanceRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Attendance", "id", id));
    }

    @Override
    public List<Attendance> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attendanceRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        attendanceRepository.deleteById(id);
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return attendanceRepository.existsById(id);
    }


}
