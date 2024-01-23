package com.einfonet.keycloak.microservice1.controller;

import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/v1")
public class Controller1 {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    private final WebClient webClient = WebClient.builder().build();

    @GetMapping("/microservice1/home")
    @ResponseStatus(HttpStatus.OK)
    public String helloRestTemplate() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<String> response = restTemplate.exchange(
               "http://localhost:8084/api/v1/microservice2/home",
               HttpMethod.GET,
               new HttpEntity<>(headers),
               String.class
       );

        return "Hello from Service 2: " + response.getBody();
    }

    @GetMapping("/microservice1/callMS2")
    @ResponseStatus(HttpStatus.OK)
    public String useWebCleint2CallAnotherService() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String response = this.webClient
                .get()
                .uri("http://localhost:8084/api/v1/microservice2/home")
                .headers(headers -> headers.setBearerAuth(jwt.getTokenValue()))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "Hello from Service 2 using WebClient : " + response;
    }
}
