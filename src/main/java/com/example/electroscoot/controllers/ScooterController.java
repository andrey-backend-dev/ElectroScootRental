package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.mappers.OrderEnumMapper;
import com.example.electroscoot.utils.mappers.ScooterStateEnumMapper;
import com.example.electroscoot.utils.mappers.SortEnumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scooters")
public class ScooterController {
    private final IScooterService scooterService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO findById(@PathVariable("id") int id) {
        return scooterService.findById(id);
    }

    // methods below require authorities

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> findAllScooters(@RequestParam(value = "sort", required = false) String sortMethod,
                                            @RequestParam(value = "ordering", required = false) String ordering,
                                            @RequestParam(value = "state-filter", required = false) String state) {
        SortMethod sort = sortMethod == null || SortEnumMapper.getSortByName(sortMethod) == SortMethod.NULL
                ? SortMethod.NULL
                : SortEnumMapper.getSortByName(sortMethod);
        OrderEnum order = ordering == null || OrderEnumMapper.getOrderingByName(ordering) == OrderEnum.NULL
                ? OrderEnum.NULL
                : OrderEnumMapper.getOrderingByName(ordering);
        ScooterStateEnum scooterState = state == null || ScooterStateEnumMapper.getScooterStateByName(state) == ScooterStateEnum.NULL
                ? ScooterStateEnum.NULL
                : ScooterStateEnumMapper.getScooterStateByName(state);
        return scooterService.findAll(sort, order, scooterState);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO createScooter(@RequestBody CreateScooterDTO createData) {
        return scooterService.create(createData);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteScooterById(@PathVariable("id") int id) {
        return scooterService.deleteById(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO updateScooterById(@PathVariable("id") int id, @RequestBody UpdateScooterDTO updateData) {
        return scooterService.updateById(id, updateData);
    }
}
