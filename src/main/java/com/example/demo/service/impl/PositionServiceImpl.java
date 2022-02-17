package com.example.demo.service.impl;

import com.example.demo.base.BaseServiceImpl;
import com.example.demo.entity.Position;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.payload.requests.PositionRequest;
import com.example.demo.repository.PositionRepository;
import com.example.demo.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PositionServiceImpl extends BaseServiceImpl<Position, PositionRequest> implements PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(Validator validator, PositionRepository positionRepository) {
        super(validator, positionRepository);
        this.positionRepository = positionRepository;
    }

    @Override
    public Position saveOrUpdate(PositionRequest request) {
        return validate(new Position(), request);
    }

    @Override
    public List<Position> saveOrUpdate(Collection<PositionRequest> requests) {
        return requests.stream().map(this::saveOrUpdate).collect(Collectors.toList());
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

}
