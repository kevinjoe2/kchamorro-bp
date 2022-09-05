package com.kevinchamorro.bancopichincha.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ReportTransactionResponseDto {
    private LocalDateTime Fecha;
    private String Cliente;
    private String NumeroCuenta;
    private String Tipo;
    private BigDecimal SaldoInicial;
    private String Estado;
    private BigDecimal Movimiento;
    private BigDecimal SaldoDisponible;
}
