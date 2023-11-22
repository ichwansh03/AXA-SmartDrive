package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;

    @Override
    public Services getById(Long aLong) {
        return soRepository.findById(aLong).get();
    }

    @Override
    public List<Services> getAll() {
        return soRepository.findAll();
    }

    @Override
    public Services save(Services services) {
        return soRepository.save(services);
    }

    @Override
    public void deleteById(Long aLong) {

    }

}
