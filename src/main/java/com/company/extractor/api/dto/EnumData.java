package com.company.extractor.api.dto;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class EnumData {
    public final String enumName;
    public final List<Object> values;

    public EnumData(String enumName, List<Object> values) {
        this.enumName = enumName;
        this.values = values;
    }
}
