package com.company.extractor.service;

import com.company.extractor.entity.DtoAnnotation;
import com.company.extractor.api.dto.DtoData;
import com.company.extractor.entity.FieldData;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoExtractor {

    private ApiExtractor extractor;

    public DtoExtractor(ApiExtractor extractor) {
        this.extractor = extractor;
    }

    public List<DtoData> extractDtos() {
        return getDtos().stream()
                .map(this::buildDtoData)
                .collect(Collectors.toList());
    }

    private List<Class<?>> getDtos() {
        return extractor.extract().getDtos();
    }

    private DtoData buildDtoData(Class<?> dtoClass) {
        List<FieldData> fieldDataList = Arrays.stream(dtoClass.getFields())
                .map(field -> {
                    Annotation[] annotations = field.getAnnotations();
                    List<DtoAnnotation> annotationList = new ArrayList<>();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Size) {
                            DtoAnnotation dtoAnnotation = new DtoAnnotation();
                            dtoAnnotation.setName(Size.class.getName());
                            dtoAnnotation.setMin((long) ((Size) annotation).min());
                            dtoAnnotation.setMax((long) ((Size) annotation).max());
                            annotationList.add(dtoAnnotation);
                        }
                        if (annotation instanceof Min) {
                            DtoAnnotation dtoAnnotation = new DtoAnnotation();
                            dtoAnnotation.setName(Min.class.getName());
                            dtoAnnotation.setMin(((Min) annotation).value());
                            annotationList.add(dtoAnnotation);
                        }
                        if (annotation instanceof ApiModelProperty) {
                            DtoAnnotation dtoAnnotation = new DtoAnnotation();
                            dtoAnnotation.setName(ApiModelProperty.class.getName());
                            dtoAnnotation.setRequired(((ApiModelProperty) annotation).required());
                            annotationList.add(dtoAnnotation);
                        }
                    }

                    return new FieldData(field.getName(), field.getType(), annotationList);
                }).collect(Collectors.toList());

        return new DtoData(dtoClass.getName(), fieldDataList);
    }
}
