package com.company.extractor.api;

import com.company.extractor.api.dto.Controllers;
import com.company.extractor.api.dto.DtoData;
import com.company.extractor.service.DtoSorter;
import com.company.extractor.service.ControllerSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ExtractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractController.class);

    private DtoSorter dtoSorter;
    private ControllerSorter controllerSorter;

    public ExtractController(DtoSorter dtoSorter,
                             ControllerSorter controllerSorter) {
        this.dtoSorter = dtoSorter;
        this.controllerSorter = controllerSorter;
    }

    @GetMapping(path = "/api-data/dto")
    public List<DtoData> getDtos() {
        LOGGER.info("Запрос получения списка дто");
        return dtoSorter.sortDtos();
    }

    @GetMapping(path = "/api-data/ctrl/{method}")
    public List<Controllers> getControllers(@PathVariable String method) {
        LOGGER.info("Запрос получения списка контроллеров: {}", method);
        return controllerSorter.dynamicSort(method);
    }
}

