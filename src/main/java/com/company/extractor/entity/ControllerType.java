package com.company.extractor.entity;

public enum ControllerType {
    POST_MAPPING("PostMapping"),
    GET_MAPPING("GetMapping"),
    PATCH_MAPPING("PatchMapping"),
    PUT_MAPPING("PutMapping");

    private final String name;

    ControllerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
