package com.kevinchamorro.bancopichincha.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("customers")
@Getter
@Setter
public class CustomerEntity extends Person {

    @Id
    private Long id;

    @Column("client_id")
    private String clientId;

    private String password;

    private String state;

    public CustomerEntity(String name, String gender, LocalDate dateBirth, String documentNumber, String address, String phone, String clientId, String password, String state) {
        super(name, gender, dateBirth, documentNumber, address, phone);
        this.clientId = clientId;
        this.password = password;
        this.state = state;
    }
}
