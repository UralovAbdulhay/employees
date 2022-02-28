package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.elasticRepo.AttendanceElasticRepository;
import com.example.demo.entity.Attendance;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.requests.AttendanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl {

    private final AttendanceElasticRepository attendanceElasticRepository;
    private final Validator validator;
    private final ObjectParser objectParser;


    public Attendance save(AttendanceRequest request) {

        Attendance attendance = new Attendance();

        objectParser.copyFieldsIgnoreNulls(attendance, request, true);
        attendance.setEmployeeId(request.getEmployeeId());
        attendance.setId(UUID.randomUUID());
        return attendanceElasticRepository.save(attendance);


    }


    public Attendance findById(@Valid @NotNull String id) {
        return attendanceElasticRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Attendance", "id", id));
    }


    public List<Attendance> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attendanceElasticRepository.findAll(pageable).getContent();
    }


    public boolean isValidForSave(AttendanceRequest request) {
        return validator.validate(request, SaveValidation.class).size() == 0;
    }

//    @Override
//    public Attendance update(AttendanceRequest request) {
//
//        if (isValidForUpdate(request)) {
//            Attendance attendance = findById((request.getId()));
//            objectParser.copyFieldsIgnoreNulls(attendance, request, true);
//            attendance.setEmployeeId(request.getEmployeeId());
//            return attendanceRepository.save(attendance);
//        } else {
//            throw BadRequest.get("AttendanceRequest not available for update ");
//        }
//
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        attendanceRepository.deleteById(id);
//        return isDeleted(id);
//    }
//
//    @Override
//    public boolean isDeleted(Long id) {
//        return !attendanceRepository.existsById(id);
//    }
//
//    @Override
//    public boolean existById(Long id) {
//        return attendanceRepository.existsById(id);
//    }
//
//    @Override
//    public boolean isValidForUpdate(AttendanceRequest request) {
//        return validator.validate(request, UpdateValidation.class).size() == 0;
//    }
//

//
//    @Override
//    public AttendanceRequest convertToPayload(Attendance entity) {
//        return AttendanceRequest.getInstance(entity);
//    }
//
//    @Override
//    public String exportAll() {
//        return null;
//    }
//
//
//    public List<Attendance> findAllByEmployeeId(Long employeeId) {
//        return attendanceRepository.findAllByEmployeeId(employeeId);
//    }

}
