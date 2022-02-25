package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Attendance;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.AttendanceRequest;
import com.example.demo.rabbit.RabbitSender;
import com.example.demo.rabbit.Urls;
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
import java.util.List;

import static com.example.demo.rabbit.Urls.*;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final Validator validator;
    private final EmployeeServiceImpl employeeService;
    private final ObjectParser objectParser;
    private final RabbitSender<AttendanceRequest> rabbitSender;


    @Override
    public Attendance save(AttendanceRequest request) {

        Attendance attendance = new Attendance();
        if (isValidForSave(request)) {
            objectParser.copyFieldsIgnoreNulls(attendance, request, true);
            attendance.setEmployee(employeeService.findById(request.getEmployeeId()));


            Attendance newAttendance = attendanceRepository.save(attendance);
            rabbitSender.sendObject(convertToPayload(newAttendance), TOPIC_EXCHANGE, ATTENDANCE_SAVE);
            return newAttendance;
        }
        return attendance;
    }

    @Override
    public Attendance update(AttendanceRequest request) {

        if (isValidForUpdate(request)) {
            Attendance attendance = findById((request.getId()));
            objectParser.copyFieldsIgnoreNulls(attendance, request, true);
            attendance.setEmployee(employeeService.findById(request.getEmployeeId()));

            Attendance newAttendance = attendanceRepository.save(attendance);
            rabbitSender.sendObject(convertToPayload(newAttendance), TOPIC_EXCHANGE, ATTENDANCE_UPDATE);
            return newAttendance;
        } else {
            throw BadRequest.get("AttendanceRequest not available for update ");
        }

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
        return isDeleted(id);
    }

    @Override
    public boolean isDeleted(Long id) {
        return !attendanceRepository.existsById(id);
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

    @Override
    public AttendanceRequest convertToPayload(Attendance entity) {
        return AttendanceRequest.getInstance(entity);
    }

    @Override
    public String exportAll() {
        return exportToExcel(convertToPayload(attendanceRepository.findAll()));
    }

    public List<Attendance> findAllByEmployeeId(Long employeeId) {
        return attendanceRepository.findAllByEmployeeId(employeeId);
    }

    public boolean save(Attendance attendance) {
        return attendanceRepository.save(attendance) != null;
    }

}
