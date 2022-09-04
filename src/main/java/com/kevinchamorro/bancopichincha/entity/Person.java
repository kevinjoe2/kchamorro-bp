package com.kevinchamorro.bancopichincha.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Getter
@Setter
public class Person {

    private String name;

    private String gender;

    @Column("date_birth")
    private LocalDate dateBirth;

    @Column("document_number")
    private String documentNumber;

    private String address;

    private String phone;

}
