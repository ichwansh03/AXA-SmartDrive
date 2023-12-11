package com.smartdrive.masterservice.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public static <E, D> D mapEntityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <E, D> List<D> mapEntityListToDtoList(List<E> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public static <E, D> E mapDtoToEntity(D dto, E entity) {
        modelMapper.map(dto, entity);
        return entity;
    }

    public static <E, D> List<E> mapListDtoToListEntity(List<D> dtoList, Class<E> entityClass) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, entityClass))
                .collect(Collectors.toList());
    }

    public static <E, D> D mapEntityToDto(E entity, Class<D> dtoClass, ModelMapper customMapper) {
        return customMapper.map(entity, dtoClass);
    }

    public static <E, D> E mapDtoToEntity(D dto, E entity, ModelMapper customMapper) {
        customMapper.map(dto, entity);
        return entity;
    }
}
