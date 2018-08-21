package com.company.extractor.service;

import com.company.extractor.api.dto.EnumData;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnumExtractor {

    private ApiExtractor extractor;

    public EnumExtractor(ApiExtractor extractor) {
        this.extractor = extractor;
    }

    public List<EnumData> extractEnums() {
        return getEnums().stream()
                .map(this::buildEnumsData)
                .collect(Collectors.toList());
    }

    private List<Class<?>> getEnums() {
        return extractor.extract().getEnums();
    }

    private EnumData buildEnumsData(Class<?> enumClass) {
        List<Object> fieldsName = Arrays.stream(enumClass.getFields())
                .map(Field::getName)
                .collect(Collectors.toList());
        return new EnumData(enumClass.getName(), fieldsName);
    }
}
