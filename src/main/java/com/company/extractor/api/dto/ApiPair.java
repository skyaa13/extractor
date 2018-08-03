package com.company.extractor.api.dto;

import java.util.List;

public class ApiPair {
    private List<Class<?>> controllers;
    private List<Class<?>> dtos;

    public ApiPair(List<Class<?>> controllers, List<Class<?>> dtos) {
        this.controllers = controllers;
        this.dtos = dtos;
    }

    public List<Class<?>> getControllers() {
        return controllers;
    }

    public List<Class<?>> getDtos() {
        return dtos;
    }

    public void setControllers(List<Class<?>> controllers) {
        this.controllers = controllers;
    }

    public void setDtos(List<Class<?>> dtos) {
        this.dtos = dtos;
    }
}
