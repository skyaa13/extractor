package com.company.extractor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MethodParameter {
    public final String name;
    public final Class<?> type;

    public MethodParameter(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }
}