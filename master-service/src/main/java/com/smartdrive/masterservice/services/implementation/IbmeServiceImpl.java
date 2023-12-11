package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.InboxMessaging;
import com.smartdrive.masterservice.repositories.IbmeRepository;
import com.smartdrive.masterservice.services.IbmeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public InboxMessaging save(InboxMessaging entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
