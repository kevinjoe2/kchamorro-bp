package com.kevinchamorro.bancopichincha.mapper;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.CustomerResponseDto;
import com.kevinchamorro.bancopichincha.dto.ParameterRequestDto;
import com.kevinchamorro.bancopichincha.dto.TransactionRequestDto;
import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import com.kevinchamorro.bancopichincha.entity.CustomerEntity;
import com.kevinchamorro.bancopichincha.entity.ParameterEntity;
import com.kevinchamorro.bancopichincha.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface PatchGeneralMapper {
    void patchCustomerEntityFromFto(CustomerResponseDto requestDto, @MappingTarget CustomerEntity entity);
    void patchAccountEntityFromFto(AccountRequestDto requestDto, @MappingTarget AccountEntity entity);
    void patchParameterEntityFromFto(ParameterRequestDto requestDto, @MappingTarget ParameterEntity entity);
    void patchTransactionEntityFromFto(TransactionRequestDto requestDto, @MappingTarget TransactionEntity entity);
}
