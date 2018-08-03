package com.company.extractor.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class FieldData {
    public final String name;
    public final Object type;
    public final List<DtoAnnotation> annotations;

    public FieldData(String name,
                     Object type,
                     List<DtoAnnotation> annotations) {
        this.name = name;
        this.type = type;
        this.annotations = annotations;
    }
}
