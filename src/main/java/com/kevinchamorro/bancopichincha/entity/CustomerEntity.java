package com.kevinchamorro.bancopichincha.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("customers")
@Builder
@Getter
@Setter
public class CustomerEntity extends Person {

    @Id
    private Long id;

    @Column("client_id")
    private String clientId;

    private String password;

    private String state;
}
