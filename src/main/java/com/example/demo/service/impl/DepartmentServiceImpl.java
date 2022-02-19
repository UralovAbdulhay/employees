package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Department;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.DepartmentRequest;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final ObjectParser objectParser;

    private final Validator validator;


    @Override
    public Department save(DepartmentRequest request) {
        if (isValidForSave(request)) {
            Department department = new Department();
            objectParser.copyFieldsIgnoreNulls(department, request, true);
            return departmentRepository.save(department);
        } else {
            throw BadRequest.get("DepartmentRequest not available for saving ");
        }
    }

    @Override
    public Department update(DepartmentRequest request) {
        if (isValidForUpdate(request)) {
            Department department = findById(request.getId());
            objectParser.copyFieldsIgnoreNulls(department, request, true);
            return departmentRepository.save(department);
        } else {
            throw BadRequest.get("DepartmentRequest not available for update ");
        }
    }


    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Department", "id", id));
    }

    @Override
    public List<Department> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return departmentRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        departmentRepository.deleteById(id);
        return isDeleted(id);
    }

    @Override
    public boolean isDeleted(Long id) {
        return !departmentRepository.existsById(id);
    }

    @Override
    public boolean existById(Long id) {
        return departmentRepository.existsById(id);
    }

    @Override
    public boolean isValidForUpdate(DepartmentRequest request) {
        return validator.validate(request, UpdateValidation.class).size() == 0;
    }

    @Override
    public boolean isValidForSave(DepartmentRequest request) {
        return validator.validate(request, SaveValidation.class).size() == 0;
    }

    @Override
    public DepartmentRequest convertToPayload(Department entity) {
        DepartmentRequest request = new DepartmentRequest();
        objectParser.copyFieldsIgnoreNulls(request, entity, true);
        return request;
    }


    public String exportAll() {
        return exportToExcel(convertToPayload(departmentRepository.findAll()));
    }


}
