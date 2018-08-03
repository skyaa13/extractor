package com.company.extractor.api.dto;

import com.company.extractor.entity.FieldData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class DtoData {
    public final String className;
    public final List<FieldData> fields;

    public DtoData(String className,
                   List<FieldData> fields) {
        this.className = className;
        this.fields = fields;
    }
}
