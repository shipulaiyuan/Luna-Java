package com.virtual.luna.controller.publish;

import com.virtual.luna.common.base.constant.Constants;
import com.virtual.luna.infra.register.remote.BaPoFactory;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/remote/forward")
public class BaPoForwardController {

    @Resource
    private BaPoFactory remoteServiceFactory;

    @PermitAll
    @GetMapping
    public ResponseEntity<String> handleGetRequest(
            @RequestHeader("Service-Name") String serviceName,
            @RequestHeader("Client-Path") String clientPath,
            @RequestParam Map<String, String> queryParams) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.get(fullUrl, queryParams, String.class, headers));
    }

    @PostMapping
    public ResponseEntity<String> handlePostRequest(
            @RequestHeader("Service-Name") String serviceName,
            @RequestHeader("Client-Path") String clientPath,
            @RequestBody String requestBody) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.post(fullUrl, requestBody, String.class, headers));
    }

    @PutMapping
    public ResponseEntity<String> handlePutRequest(
            @RequestHeader("Service-Name") String serviceName,
            @RequestHeader("Client-Path") String clientPath,
            @RequestBody String requestBody) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.put(fullUrl, requestBody, String.class, headers));
    }

    @DeleteMapping
    public ResponseEntity<String> handleDeleteRequest(
            @RequestHeader("Service-Name") String serviceName,
            @RequestHeader("Client-Path") String clientPath,
            @RequestParam Map<String, String> queryParams) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.delete(fullUrl, queryParams, String.class, headers));
    }

    private String buildFullUrl(String serviceName, String clientPath) {
        String host = "127.0.0.1";
        int port = 11002;
        return Constants.HTTP + host + ":" + port  + clientPath;
    }

    private HttpHeaders createHeaders(String serviceName, String clientPath) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Service-Name", serviceName);
        headers.set("Client-Path", clientPath);
        return headers;
    }
}