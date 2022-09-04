package com.kevinchamorro.bancopichincha.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("parameters")
@Builder
@Getter
@Setter
public class ParameterEntity {
    @Id
    private Long id;
    private String name;
    private String value;
}
