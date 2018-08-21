package com.company.extractor.api;

import com.company.extractor.api.dto.Controllers;
import com.company.extractor.api.dto.DtoData;
import com.company.extractor.api.dto.EnumData;
import com.company.extractor.service.DtoExtractor;
import com.company.extractor.service.ControllerExtractor;
import com.company.extractor.service.EnumExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.List;


@RestController
public class ExtractController {

    private final Logger LOGGER = LoggerFactory.getLogger(ExtractController.class);

    private DtoExtractor dtoExtractor;
    private ControllerExtractor controllerExtractor;
    private EnumExtractor enumExtractor;

    public ExtractController(DtoExtractor dtoExtractor,
                             ControllerExtractor controllerExtractor,
                             EnumExtractor enumExtractor) {
        this.dtoExtractor = dtoExtractor;
        this.controllerExtractor = controllerExtractor;
        this.enumExtractor = enumExtractor;
    }

    @GetMapping(path = "/api-data/dto")
    public List<DtoData> getDtos() {
        LOGGER.info("Запрос получения списка дто");
        return dtoExtractor.extractDtos();
    }

    @GetMapping(path = "/api-data/ctrl/{method}")
    public List<Controllers> getControllers(@Nullable @PathVariable String method) {
        LOGGER.info("Запрос получения списка контроллеров: {}", method);
        return controllerExtractor.getByType(method);
    }

    @GetMapping(path = "/api-data/enums")
    public List<EnumData> getEnums() {
        LOGGER.info("Запрос получения списка перечислений");
        return enumExtractor.extractEnums();
    }
}

