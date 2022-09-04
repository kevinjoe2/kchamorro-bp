package com.kevinchamorro.bancopichincha.mapper;

import com.kevinchamorro.bancopichincha.dto.AccountRequestDto;
import com.kevinchamorro.bancopichincha.dto.AccountResponseDto;
import com.kevinchamorro.bancopichincha.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountResponseDto toAccountResponseDto(AccountEntity entity);
    AccountEntity toAccountEntity(AccountRequestDto requestDto);
    void putAccountEntityFromDto(AccountRequestDto requestDto, @MappingTarget AccountEntity entity);
}
