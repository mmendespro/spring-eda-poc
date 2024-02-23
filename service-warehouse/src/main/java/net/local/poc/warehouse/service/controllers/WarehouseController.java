package net.local.poc.warehouse.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.local.poc.warehouse.service.services.WarehouseService;


@Slf4j
@RestController
@RequestMapping("v1/warehouse")
public class WarehouseController {
    
    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @GetMapping
    public String initDatabase() {
        log.info("Init database...");
        service.initdb();
        return "OK";
    }
    
}
