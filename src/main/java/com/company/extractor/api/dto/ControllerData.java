package com.company.extractor.api.dto;

import com.company.extractor.entity.ControllerAnnotation;
import com.company.extractor.entity.MethodParameter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ControllerData {
    public Class<?> declaredClass;
    public final String controllerPath;
    public final String methodName;
    public final Class<?> returnType;
    public final List<MethodParameter> parameters;
    public final ControllerAnnotation annotation;

    public ControllerData(Class<?> declaredClass,
                          String controllerPath,
                          String methodName,
                          Class<?> returnType,
                          List<MethodParameter> parameters,
                          ControllerAnnotation annotation) {
        this.declaredClass = declaredClass;
        this.controllerPath = controllerPath;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameters = parameters;
        this.annotation = annotation;
    }

    public void setDeclaredClass(Class<?> declaredClass) {
        this.declaredClass = declaredClass;
    }
}
