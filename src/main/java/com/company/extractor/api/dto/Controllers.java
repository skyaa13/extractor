package com.company.extractor.api.dto;

import java.util.List;

public class Controllers {
    public final String declaredClass;
    public final List<ControllerData> controllerData;

    public Controllers(String declaredClass, List<ControllerData> controllerData) {
        this.declaredClass = declaredClass;
        this.controllerData = controllerData;
    }
}
