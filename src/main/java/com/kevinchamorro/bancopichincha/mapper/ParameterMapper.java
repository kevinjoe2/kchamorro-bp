package com.kevinchamorro.bancopichincha.mapper;

import com.kevinchamorro.bancopichincha.dto.ParameterRequestDto;
import com.kevinchamorro.bancopichincha.dto.ParameterResponseDto;
import com.kevinchamorro.bancopichincha.entity.ParameterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParameterMapper {

    ParameterResponseDto toParameterResponseDto(ParameterEntity entity);
    ParameterEntity toCustomerEntity(ParameterRequestDto customerRequestDto);
    void putParameterEntityFromDto(ParameterRequestDto requestDto, @MappingTarget ParameterEntity entity);
}
