package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.*;
import com.example.electroscoot.services.interfaces.*;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.enums.UserStatus;
import com.example.electroscoot.utils.maps.OrderMap;
import com.example.electroscoot.utils.maps.ScooterStateMap;
import com.example.electroscoot.utils.maps.SortMap;
import com.example.electroscoot.utils.maps.UserStatusMap;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private Logger logger;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IScooterModelService scooterModelService;
    @Autowired
    private IRentalPlaceService rentalPlaceService;
    @Autowired
    private IScooterService scooterService;
    @Autowired
    private IScooterRentalService scooterRentalService;

//    roles

    @PostMapping(value = "/roles/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        logger.info("The <createRole> method is called from Admin Controller.");
        return roleService.create(roleDTO);
    }

    @DeleteMapping(value = "/roles/{name}")
    public boolean deleteRoleByName(@PathVariable("name") String name) {
        logger.info("The <deleteRoleByName> method is called from Admin Controller.");
        return roleService.deleteByName(name);
    }

    @GetMapping(value = "/roles/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getRoleList() {
        logger.info("The <getRoleList> method is called from Admin Controller.");
        return roleService.getList();
    }

    @GetMapping(value = "/roles/exists")
    public boolean doesRoleExistByName(@RequestParam("name") String name) {
        logger.info("The <doesRoleExistByName> method is called from Admin Controller.");
        return roleService.doesExistByName(name);
    }

//    users

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getUserList() {
        logger.info("The <getUserList> method is called from Admin Controller.");
        return userService.getList();
    }

    @GetMapping(value = "/users/{username}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getRolesByUsername(@PathVariable("username") String username) {
        logger.info("The <getRolesByUsername> method is called from Admin Controller.");
        return userService.getRolesByUsername(username);
    }

    @PatchMapping(value = "/users/{username}/roles/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleJWTResponseDTO addRoleByUsername(@PathVariable("username") String username, @RequestBody RoleNameDTO roleNameDTO) {
        logger.info("The <addRoleByUsername> method is called from Admin Controller.");
        return userService.addRoleByUsername(username, roleNameDTO);
    }

    @PatchMapping(value = "/users/{username}/roles/remove", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleJWTResponseDTO removeRoleByUsername(@PathVariable("username") String username, @RequestBody RoleNameDTO roleNameDTO) {
        logger.info("The <removeRoleByUsername> method is called from Admin Controller.");
        return userService.removeRoleByUsername(username, roleNameDTO);
    }

    @PatchMapping(value = "/users/{username}/add-money", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addMoneyByUsername(@PathVariable("username") String username, @RequestBody MoneyDTO moneyDTO) {
        logger.info("The <addMoneyByUsername> method is called from Admin Controller.");
        return userService.addMoneyByUsername(username, moneyDTO);
    }

    @GetMapping(value = "/users/{username}/rent-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> getRentHistoryByUsername(@PathVariable("username") String username) {
        logger.info("The <getRentHistoryByUsername> method is called from Admin Controller.");
        return userService.getRentHistoryByUsername(username);
    }

    @PatchMapping(value = "/users/{username}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO changeUserStatusByUsername(@PathVariable("username") String username,
                                              @RequestParam("status") String status) {
        logger.info("The <changeUserStatusByUsername> method is called from Admin Controller.");
        UserStatus userStatus = UserStatusMap.getStatusByName(status);
        return userService.changeUserStatusByUsername(username, userStatus);
    }

//    scooter models
@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterModelDTO> getScooterModelList() {
        logger.info("The <getScooterModelList> method is called from Admin Controller.");
        return scooterModelService.getList();
    }

    @PostMapping(value = "/scooter-models/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO createScooterModel(@RequestBody CreateScooterModelDTO createData) {
        logger.info("The <createScooterModel> method is called from Admin Controller.");
        return scooterModelService.create(createData);
    }

    @PutMapping(value = "/scooter-models/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO updateScooterModelByName(@PathVariable("name") String name, @RequestBody UpdateScooterModelDTO updateData) {
        logger.info("The <updateScooterModelByName> method is called from Admin Controller.");
        return scooterModelService.updateByName(name, updateData);
    }

    @DeleteMapping(value = "/scooter-models/{name}")
    public boolean deleteScooterModelByName(@PathVariable("name") String name) {
        logger.info("The <deleteScooterModelByName> method is called from Admin Controller.");
        return scooterModelService.deleteByName(name);
    }

    @GetMapping(value = "/scooter-models/{name}/scooters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getScootersByScooterModelName(@PathVariable("name") String name) {
        logger.info("The <getScootersByScooterModelName> method is called from Admin Controller.");
        return scooterModelService.getScootersByName(name);
    }

//    rental-places

    @PostMapping(value = "/rental-places/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO createRentalPlace(@RequestBody CreateRentalPlaceDTO createData) {
        logger.info("The <createRentalPlace> method is called from Admin Controller.");
        return rentalPlaceService.create(createData);
    }

    @PutMapping(value = "/rental-places/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO updateRentalPlaceByName(@PathVariable("name") String name, @RequestBody UpdateRentalPlaceDTO updateData) {
        logger.info("The <updateRentalPlaceByName> method is called from Admin Controller.");
        return rentalPlaceService.updateByName(name, updateData);
    }

    @DeleteMapping(value = "/rental-places/{name}")
    public boolean deleteRentalPlaceByName(@PathVariable("name") String name) {
        logger.info("The <deleteRentalPlaceByName> method is called from Admin Controller.");
        return rentalPlaceService.deleteByName(name);
    }

    //    scooters

    @GetMapping(value = "/scooters/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getScooterList(@RequestParam(value = "sort", required = false) String sortMethod,
                                    @RequestParam(value = "ordering", required = false) String ordering,
                                    @RequestParam(value = "state-filter", required = false) String state) {
        logger.info("The <getScooterList> method is called from Admin Controller. " +
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

    @PostMapping(value = "/scooters/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO createScooter(@RequestBody CreateScooterDTO createData) {
        logger.info("The <createScooter> method is called from Admin Controller.");
        return scooterService.create(createData);
    }

    @DeleteMapping(value = "/scooters/{id}")
    public boolean deleteScooterById(@PathVariable("id") int id) {
        logger.info("The <deleteScooterById> method is called from Admin Controller.");
        return scooterService.deleteById(id);
    }

    @PutMapping(value = "/scooters/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO updateScooterById(@PathVariable("id") int id, @RequestBody UpdateScooterDTO updateData) {
        logger.info("The <updateScooterById> method is called from Admin Controller.");
        return scooterService.updateById(id, updateData);
    }

//    scooter rentals

    @GetMapping(value = "/rentals/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> getScooterRentalList(@RequestParam(value = "passed-filter", required = false) Boolean passed) {
        logger.info("The <getScooterRentalList> method is called from Admin Controller. Passed filter: " + passed);
        return scooterRentalService.getList(passed);
    }

    @GetMapping(value = "/rentals/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO findScooterRentalById(@PathVariable("id") int id) {
        logger.info("The <findScooterRentalById> method is called from Admin Controller.");
        return scooterRentalService.findById(id);
    }

    @PatchMapping(value = "/rentals/{id}/close", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO closeRentalById(@PathVariable("id") int id, @RequestParam("rental-place") String rentalPlace) {
        logger.info("The <closeRentalById> method is called from Scooter Rental Controller.");
        return scooterRentalService.closeRentalById(id, rentalPlace);
    }
}
