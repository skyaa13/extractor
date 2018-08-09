package com.company.extractor.service;

import com.company.extractor.api.dto.ApiPair;
import com.company.extractor.api.dto.Controllers;
import com.company.extractor.entity.ControllerAnnotation;
import com.company.extractor.api.dto.ControllerData;
import com.company.extractor.entity.ControllerMethods;
import com.company.extractor.entity.MethodParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.company.extractor.entity.ControllerType.*;
import static com.company.extractor.utils.StringUtils.getAnnotationName;
import static com.company.extractor.utils.StringUtils.removeBrackets;

@Service
public class ControllerExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExtractor.class);
    
    private ApiExtractor extractor;

    public ControllerExtractor(ApiExtractor extractor) {
        this.extractor = extractor;
    }

    public List<Controllers> extractByType(String controllerType) {
        switch (controllerType) {
            case "post":
                return collectByType(getSeparatedControllers().getPostMethods());
            case "get":
                return collectByType(getSeparatedControllers().getGetMethods());
            case "patch":
                return collectByType(getSeparatedControllers().getPatchMethods());
            case "put":
                return collectByType(getSeparatedControllers().getPutMethods());
            default:
                LOGGER.error("Тип контроллера не опознан: {}", controllerType);
                return null;
        }
    }

    private List<Controllers> collectByType(List<Method> methodList) {
        return methodList.stream()
                .map(this::buildControllerMethodData)
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(i -> i.declaredClass.getName()))
                .entrySet().stream()
                .map(entry -> {
                    for (ControllerData cd : entry.getValue()) {
                        cd.setDeclaredClass(null);
                    }
                    return new Controllers(entry.getKey(), entry.getValue());
                })
                .collect(Collectors.toList());
    }

    private ControllerData buildControllerMethodData(Method method) {
        String name = "";
        String path = "";
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof PostMapping) {
                name = annotation.toString();
                path = Arrays.toString(((PostMapping) annotation).path());
            }
            if (annotation instanceof GetMapping) {
                name = annotation.toString();
                path = Arrays.toString(((GetMapping) annotation).path());
            }
            if (annotation instanceof PatchMapping) {
                name = annotation.toString();
                path = Arrays.toString(((PatchMapping) annotation).path());
            }
            if (annotation instanceof PutMapping) {
                name = annotation.toString();
                path = Arrays.toString(((PutMapping) annotation).path());
            }
        }

        String requestMappingPath = Arrays.toString(method.getDeclaringClass().getAnnotation(RequestMapping.class).value());

        return new ControllerData(
                method.getDeclaringClass(),
                removeBrackets(requestMappingPath),
                method.getName(),
                method.getReturnType(),
                parameterTypeBuilder(method),
                new ControllerAnnotation(getAnnotationName(name), removeBrackets(path))
        );
    }

    private List<MethodParameter> parameterTypeBuilder(Method method) {
        List<MethodParameter> methodParameters = new ArrayList<>();

        LocalVariableTableParameterNameDiscoverer lvtpnd = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = lvtpnd.getParameterNames(method);
        Parameter[] paramTypes = method.getParameters();

        for (int i = 0; i < Objects.requireNonNull(parameterNames).length; i++) {
            methodParameters.add(new MethodParameter(parameterNames[i], paramTypes[i].getType()));
        }
        return methodParameters;
    }

    private ControllerMethods getSeparatedControllers() {
        ApiPair apiPair = extractor.extract();
        ControllerMethods controllers = new ControllerMethods();
        controllers.setPostMethods(sortByMethodType(POST_MAPPING.getName()).apply(apiPair));
        controllers.setGetMethods(sortByMethodType(GET_MAPPING.getName()).apply(apiPair));
        controllers.setPatchMethods(sortByMethodType(PATCH_MAPPING.getName()).apply(apiPair));
        controllers.setPutMethods(sortByMethodType(PUT_MAPPING.getName()).apply(apiPair));
        return controllers;
    }

    private Function<ApiPair, List<Method>> sortByMethodType(String methodType) {
        return apiPair -> {
            List<Method> list = new ArrayList<>();
            apiPair.getControllers()
                    .forEach(c -> Arrays.stream(c.getMethods())
                            .forEach(method -> {
                                List<Annotation> annotations = Arrays.asList(method.getAnnotations());
                                if (annotations.toString().contains(methodType)) {
                                    list.add(method);
                                }
                            }));
            return list;
        };
    }
}
