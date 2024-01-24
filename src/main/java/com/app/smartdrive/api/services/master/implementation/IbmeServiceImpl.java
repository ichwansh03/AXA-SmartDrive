package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.IbmeReq;
import com.app.smartdrive.api.dto.master.response.IbmeRes;
import com.app.smartdrive.api.entities.master.InboxMessaging;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.IbmeRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("ibmeServiceImpl")
public class IbmeServiceImpl implements MasterService<IbmeRes, IbmeReq, Long> {
    private final IbmeRepository repository;

    @Override
    public IbmeRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Inbox Messagging ID : " + aLong + " Not Found")), IbmeRes.class);
    }

    @Override
    public Page<IbmeRes> getAll(Pageable pageable) {
        Page<InboxMessaging> inboxMessaging = repository.findAll(pageable);
        List<IbmeRes> ibmeRes = inboxMessaging.getContent().stream().map(
                inbox -> TransactionMapper.mapEntityToDto(inbox, IbmeRes.class)
        ).toList();
        return new PageImpl<>(ibmeRes, pageable, inboxMessaging.getTotalElements());
    }

    @Override
    @Transactional
    public IbmeRes save(IbmeReq entity) {
        InboxMessaging inboxMessaging = repository.save(TransactionMapper.mapDtoToEntity(entity, new InboxMessaging()));
        return TransactionMapper.mapEntityToDto(inboxMessaging, IbmeRes.class);
    }

    @Override
    public IbmeRes update(Long aLong, IbmeReq ibmeReq) {
        InboxMessaging inboxMessaging = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Inbox Messagging ID : " + aLong + " Not Found"));
        inboxMessaging = repository.save(TransactionMapper.mapDtoToEntity(ibmeReq, inboxMessaging));
        return TransactionMapper.mapEntityToDto(inboxMessaging, IbmeRes.class);
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
