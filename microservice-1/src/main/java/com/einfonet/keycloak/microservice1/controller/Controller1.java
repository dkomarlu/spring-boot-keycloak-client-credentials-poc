package com.einfonet.keycloak.microservice1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller1 {


    @GetMapping("/microservice1/home")
    @ResponseStatus(HttpStatus.OK)
    public String helloRestTemplate() {
        return "Hello";
    }
}
