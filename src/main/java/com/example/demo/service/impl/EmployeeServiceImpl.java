package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Employee;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final PositionServiceImpl positionService;
    private final Validator validator;
    private final ObjectParser<Employee, EmployeeRequest> objectParser;


    @Override
    public Employee save(EmployeeRequest request) {
        if (isValidForSave(request)) {
            Employee employee = new Employee();
            objectParser.copyFieldsIgnoreNulls(employee, request, true);
            employee.setPosition(positionService.findById(request.getPositionId()));
            return employeeRepository.save(employee);
        } else {
            throw BadRequest.get("EmployeeRequest not available for saving ");
        }
    }

    @Override
    public Employee update(EmployeeRequest request) {
        if (isValidForUpdate(request)) {
            Employee employee = findById(request.getId());
            objectParser.copyFieldsIgnoreNulls(employee, request, true);
            employee.setPosition(positionService.findById(request.getPositionId()));
            return employeeRepository.save(employee);
        } else {
            throw BadRequest.get("EmployeeRequest not available for update ");
        }
    }

    @Override
    public List<Employee> saveAll(Collection<EmployeeRequest> requests) {
        return requests.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Employee> updateAll(Collection<EmployeeRequest> requests) {
        return requests.stream().map(this::update).collect(Collectors.toList());
    }

    @Override
    public Employee findById(@Valid @NotNull @Min(1) Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Employee", "id", id));
    }

    @Override
    public List<Employee> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        employeeRepository.deleteById(id);
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return employeeRepository.existsById(id);
    }


    @Override
    public boolean isValidForUpdate(EmployeeRequest request) {
        return validator.validate(request, UpdateValidation.class).size() == 0;
    }

    @Override
    public boolean isValidForSave(EmployeeRequest request) {
        return validator.validate(request, SaveValidation.class).size() == 0;
    }
}
