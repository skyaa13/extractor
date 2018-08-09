package com.company.extractor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoAnnotation {
    private String name;
    private Long min;
    private Long max;
    private Boolean required;

    public void setName(String name) {
        this.name = name;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
