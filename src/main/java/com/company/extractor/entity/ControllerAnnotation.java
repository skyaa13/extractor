package com.company.extractor.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ControllerAnnotation {
    public final String annotationName;
    public final String annotationPath;

    public ControllerAnnotation(String annotationName, String annotationPath) {
        this.annotationName = annotationName;
        this.annotationPath = annotationPath;
    }
}
