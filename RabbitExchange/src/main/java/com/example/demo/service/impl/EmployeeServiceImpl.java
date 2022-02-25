package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Employee;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.requests.EmployeeRequest;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final PositionServiceImpl positionService;
    private final Validator validator;
    private final ObjectParser objectParser;


    @Override
    public Employee save(EmployeeRequest request) {
        System.out.println("**************************************");
        System.out.println(request);
        Employee employee = new Employee();
        if (isValidForSave(request)) {
            objectParser.copyFieldsIgnoreNulls(employee, request, true);
            employee.setPosition(positionService.findById((request.getPositionId())));
            return employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public Employee update(EmployeeRequest request) {
        if (isValidForUpdate(request)) {
            Employee employee = findById((request.getId()));
            objectParser.copyFieldsIgnoreNulls(employee, request, true);
            employee.setPosition(positionService.findById((request.getPositionId())));
            return employeeRepository.save(employee);
        } else {
            throw BadRequest.get("EmployeeRequest not available for update ");
        }
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
        return isDeleted(id);
    }

    @Override
    public boolean isDeleted(Long id) {
        return !employeeRepository.existsById(id);
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
        System.out.println("isValidForSave ########################");
        System.out.println(validator.validate(request, SaveValidation.class));
        return validator.validate(request, SaveValidation.class).size() == 0;
    }

    @Override
    public EmployeeRequest convertToPayload(Employee entity) {
        System.out.println(entity);
        return EmployeeRequest.getInstance(entity);
    }

    @Override
    public String exportAll() {
        return null;
    }


}
