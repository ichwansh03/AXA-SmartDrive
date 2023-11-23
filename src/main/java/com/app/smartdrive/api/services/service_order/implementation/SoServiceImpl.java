package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.service_order.SoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;
    private final UserRepository userRepository;

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

    @Override
    public CustomerRequest findCreqById(Long id) {

        return soRepository.findByCreqEntityid(id);
    }

    @Override
    public Services addServices(Services services) {

        services = new Services();
        services.setServStatus(EnumModuleServiceOrders.ServStatus.ACTIVE);
        //..more data
        Services save = soRepository.save(services);

        Long userId = userRepository.findById(services.getServId()).get().getUserEntityId();
        services.setServCustEntityid(userId);
        soRepository.flush();

        return save;
    }
}
