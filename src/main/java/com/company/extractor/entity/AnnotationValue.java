package com.company.extractor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnnotationValue {
    public final String name;
    public final Object value;

    public AnnotationValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
