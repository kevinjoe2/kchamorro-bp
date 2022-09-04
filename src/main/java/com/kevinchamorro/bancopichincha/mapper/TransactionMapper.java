package com.kevinchamorro.bancopichincha.mapper;

import com.kevinchamorro.bancopichincha.dto.TransactionRequestDto;
import com.kevinchamorro.bancopichincha.dto.TransactionResponseDto;
import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionResponseDto toTransactionResponseDto(TransactionEntity entity);
    TransactionEntity toCustomerEntity(TransactionRequestDto customerRequestDto);
    void putTransactionEntityFromDto(TransactionRequestDto requestDto, @MappingTarget TransactionEntity entity);
}
