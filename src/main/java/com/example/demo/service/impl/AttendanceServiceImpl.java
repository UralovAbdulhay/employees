package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Attendance;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final Validator validator;
    private final EmployeeServiceImpl employeeService;
    private final ObjectParser<Attendance, AttendanceRequest> objectParser;


    @Override
    public Attendance save(AttendanceRequest request) {

        if (isValidForSave(request)) {
            Attendance attendance = new Attendance();
            objectParser.copyFieldsIgnoreNulls(attendance, request, true);
            attendance.setEmployee(employeeService.findById(request.getEmployeeId()));
            return attendanceRepository.save(attendance);
        } else {
            throw BadRequest.get("AttendanceRequest not available for saving ");
        }
    }

    @Override
    public Attendance update(AttendanceRequest request) {

        if (isValidForUpdate(request)) {
            Attendance attendance = findById(request.getId());
            objectParser.copyFieldsIgnoreNulls(attendance, request, true);
            attendance.setEmployee(employeeService.findById(request.getEmployeeId()));
            return attendanceRepository.save(attendance);
        } else {
            throw BadRequest.get("AttendanceRequest not available for update ");
        }

    }

    @Override
    public List<Attendance> saveAll(Collection<AttendanceRequest> requests) {
        return requests.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Attendance> updateAll(Collection<AttendanceRequest> requests) {
        return requests.stream().map(this::update).collect(Collectors.toList());
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

    @Override
    public boolean isValidForUpdate(AttendanceRequest request) {
        return validator.validate(request, UpdateValidation.class).size() == 0;
    }

    @Override
    public boolean isValidForSave(AttendanceRequest request) {
        return validator.validate(request, SaveValidation.class).size() == 0;
    }


}
