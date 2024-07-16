package com.virtual.luna.controller.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ServerForwardController {

    private final RestTemplate restTemplate;

    public ServerForwardController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/forward")
    public ResponseEntity<String> forwardRequest() {
        String url = "http://127.0.0.1:11002";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response;
    }
}

