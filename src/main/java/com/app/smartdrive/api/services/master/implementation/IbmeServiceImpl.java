package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.InboxMessaging;
import com.app.smartdrive.api.repositories.master.IbmeRepository;
import com.app.smartdrive.api.services.master.IbmeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IbmeServiceImpl implements IbmeService {
    private final IbmeRepository repository;

    @Override
    public InboxMessaging getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Inbox Messagging ID : " + aLong + " Not Found"));
    }

    @Override
    public List<InboxMessaging> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public InboxMessaging save(InboxMessaging entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
