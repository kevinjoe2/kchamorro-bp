package com.kevinchamorro.bancopichincha.mapper;

import com.kevinchamorro.bancopichincha.dto.CustomerRequestDto;
import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import com.kevinchamorro.bancopichincha.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerResponseDto toCustomerDto(CustomerEntity customerEntity);
    CustomerEntity toCustomerEntity(CustomerRequestDto customerRequestDto);
    void putCustomerEntityFromDto(CustomerRequestDto requestDto, @MappingTarget CustomerEntity entity);

}
