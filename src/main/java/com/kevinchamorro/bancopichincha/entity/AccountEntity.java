package com.kevinchamorro.bancopichincha.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("accounts")
@Builder
@Getter
@Setter
public class AccountEntity {

    @Id
    private Long id;

    @Column("account_number")
    private String accountNumber;

    @Column("account_type")
    private String accountType;

    @Column("balance")
    private BigDecimal balance;

    private String state;

    @Column("customer_id")
    private Long customerId;
}
