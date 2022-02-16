package com.example.demo.service.impl;

import com.example.demo.entity.Position;
import com.example.demo.payload.requests.PositionRequest;
import com.example.demo.service.PositionService;

import java.util.Collection;
import java.util.List;

public class PositionServiceImpl implements PositionService {

    @Override
    public Position saveOrUpdate(PositionRequest t) {
        return null;
    }

    @Override
    public List<Position> saveOrUpdate(Collection<PositionRequest> ts) {
        return null;
    }

    @Override
    public Position findById(Long id) {
        return null;
    }

    @Override
    public List<Position> findAll(int page, int size) {
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
