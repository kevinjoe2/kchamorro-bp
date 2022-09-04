package com.kevinchamorro.bancopichincha.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("parameters")
@Getter
public class ParameterEntity {
    @Id
    private Long id;
    private String name;
    private String value;
}
