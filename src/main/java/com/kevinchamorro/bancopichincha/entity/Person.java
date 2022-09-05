package com.kevinchamorro.bancopichincha.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@AllArgsConstructor
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
