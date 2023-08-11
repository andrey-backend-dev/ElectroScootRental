package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.maps.OrderMap;
import com.example.electroscoot.utils.maps.ScooterStateMap;
import com.example.electroscoot.utils.maps.SortMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/scooters")
public class ScooterController {
    @Autowired
    private IScooterService scooterService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO findById(@PathVariable("id") int id) {
        logger.info("The <findById> method is called from Scooter Controller.");
        return scooterService.findById(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getList(@RequestParam(value = "sort", required = false) String sortMethod,
                                    @RequestParam(value = "ordering", required = false) String ordering,
                                    @RequestParam(value = "state-filter", required = false) String state) {
        logger.info("The <getList> method is called from Scooter Controller. " +
                "Sort: " + sortMethod + ", ordering: " + ordering + ", state-filter: " + state);
        SortMethod sort = sortMethod == null || SortMap.getSortByName(sortMethod) == SortMethod.NULL
                ? SortMethod.NULL
                : SortMap.getSortByName(sortMethod);
        OrderEnum order = ordering == null || OrderMap.getOrderingByName(ordering) == OrderEnum.NULL
                ? OrderEnum.NULL
                : OrderMap.getOrderingByName(ordering);
        ScooterStateEnum scooterState = state == null || ScooterStateMap.getScooterStateByName(state) == ScooterStateEnum.NULL
                ? ScooterStateEnum.NULL
                : ScooterStateMap.getScooterStateByName(state);
        return scooterService.getList(sort, order, scooterState);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO create(@RequestBody CreateScooterDTO createData) {
        logger.info("The <create> method is called from Scooter Controller.");
        return scooterService.create(createData);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteById(@PathVariable("id") int id) {
        logger.info("The <deleteById> method is called from Scooter Controller.");
        return scooterService.deleteById(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO updateById(@PathVariable("id") int id, @RequestBody UpdateScooterDTO updateData) {
        logger.info("The <updateById> method is called from Scooter Controller.");
        return scooterService.updateById(id, updateData);
    }
}
