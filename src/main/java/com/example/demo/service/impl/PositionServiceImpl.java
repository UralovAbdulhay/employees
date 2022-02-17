package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.entity.Position;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.PositionRequest;
import com.example.demo.repository.PositionRepository;
import com.example.demo.service.PositionService;
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
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final DepartmentServiceImpl departmentService;
    private final ObjectParser<Position, PositionRequest> objectParser;
    private final Validator validator;


    @Override
    public Position save(PositionRequest request) {
        if (isValidForSave(request)) {
            Position position = new Position();
            objectParser.copyFieldsIgnoreNulls(position, request, true);
            position.setDepartment(departmentService.findById(request.getDepartmentId()));
            return positionRepository.save(position);
        } else {
            throw BadRequest.get("PositionRequest not available for saving ");
        }
    }

    @Override
    public Position update(PositionRequest request) {
        if (isValidForUpdate(request)) {
            Position position = findById(request.getId());
            objectParser.copyFieldsIgnoreNulls(position, request, true);
            position.setDepartment(departmentService.findById(request.getDepartmentId()));
            return positionRepository.save(position);
        } else {
            throw BadRequest.get("PositionRequest not available for update ");
        }
    }

    @Override
    public List<Position> saveAll(Collection<PositionRequest> requests) {
        return requests.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Position> updateAll(Collection<PositionRequest> requests) {
        return requests.stream().map(this::update).collect(Collectors.toList());
    }

    @Override
    public Position findById(@Valid @NotNull @Min(1) Long id) {
        return positionRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Position", "id", id));
    }

    @Override
    public List<Position> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return positionRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        positionRepository.deleteById(id);
        return !existById(id);
    }

    @Override
    public boolean existById(Long id) {
        return positionRepository.existsById(id);
    }


    @Override
    public boolean isValidForUpdate(PositionRequest request) {
        return validator.validate(request, UpdateValidation.class).size() == 0;
    }

    @Override
    public boolean isValidForSave(PositionRequest request) {
        return validator.validate(request, SaveValidation.class).size() == 0;
    }

}
