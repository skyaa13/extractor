package com.company.extractor.api.dto;

import java.util.List;

public class ApiData {
    private List<Class<?>> controllers;
    private List<Class<?>> dtos;
    private List<Class<?>> enums;

    public ApiData(List<Class<?>> controllers, List<Class<?>> dtos, List<Class<?>> enums) {
        this.controllers = controllers;
        this.dtos = dtos;
        this.enums = enums;
    }

    public List<Class<?>> getControllers() {
        return controllers;
    }

    public List<Class<?>> getDtos() {
        return dtos;
    }

    public List<Class<?>> getEnums() { return enums; }
}
