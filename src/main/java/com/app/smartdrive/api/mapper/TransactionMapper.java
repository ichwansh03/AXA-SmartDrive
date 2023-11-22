package com.app.smartdrive.api.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

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

    public static <E, D> E mapDtoToEntity(D dto, E entity) {
        modelMapper.map(dto, entity);
        return entity;
    }

    public static <E, D> D mapEntityToDto(E entity, Class<D> dtoClass, ModelMapper customMapper) {
        return customMapper.map(entity, dtoClass);
    }

    public static <E, D> E mapDtoToEntity(D dto, E entity, ModelMapper customMapper) {
        customMapper.map(dto, entity);
        return entity;
    }
}
