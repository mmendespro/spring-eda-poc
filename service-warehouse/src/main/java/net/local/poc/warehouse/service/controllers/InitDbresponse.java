package net.local.poc.warehouse.service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Value;

@Value
@RequestMapping
public class InitDbresponse {
    
    private final String id = "OK";
}
