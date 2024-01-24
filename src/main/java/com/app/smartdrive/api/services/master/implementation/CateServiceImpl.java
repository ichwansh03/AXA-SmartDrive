package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.CateReq;
import com.app.smartdrive.api.dto.master.response.CateRes;
import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CateRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("cateServiceImpl")
public class CateServiceImpl implements MasterService<CateRes, CateReq, Long> {
    private final CateRepository repository;

    @Override
    public CateRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Category ID : " + aLong + " Not Found !")), CateRes.class);
    }

    @Override
    public Page<CateRes> getAll(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        List<CateRes> cateRes = categories.getContent().stream().map(
                category -> TransactionMapper.mapEntityToDto(category, CateRes.class)
        ).toList();
        return new PageImpl<>(cateRes, pageable, categories.getTotalElements());
    }

    @Transactional
    @Override
    public CateRes save(CateReq entity) {
        Category category = repository.save(TransactionMapper.mapDtoToEntity(entity, new Category()));
        return TransactionMapper.mapEntityToDto(category, CateRes.class);
    }

    @Override
    public CateRes update(Long aLong, CateReq cateReq) {
        Category category = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Category ID : " + aLong + " Not Found !"));
        category = repository.save(TransactionMapper.mapDtoToEntity(cateReq, category));
        return TransactionMapper.mapEntityToDto(category, CateRes.class);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
