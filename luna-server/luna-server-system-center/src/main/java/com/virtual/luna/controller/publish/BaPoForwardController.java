package com.virtual.luna.controller.publish;

import com.virtual.luna.common.base.constant.Constants;
import com.virtual.luna.infra.register.constant.BoPoConstants;
import com.virtual.luna.infra.register.remote.BaPoFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "BaPo传话")
@RestController
@RequestMapping("/remote/forward")
public class BaPoForwardController {

    @Resource
    private BaPoFactory remoteServiceFactory;

    @Operation(summary = "GetMapping")
    @PermitAll
    @GetMapping
    public ResponseEntity<String> handleGetRequest(
            @RequestHeader(BoPoConstants.SERVICE_NAME) String serviceName,
            @RequestHeader(BoPoConstants.CLIENT_PATH) String clientPath,
            @RequestParam Map<String, String> queryParams) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.get(fullUrl, queryParams, String.class, headers));
    }

    @Operation(summary = "PostMapping")
    @PermitAll
    @PostMapping
    public ResponseEntity<String> handlePostRequest(
            @RequestHeader(BoPoConstants.SERVICE_NAME) String serviceName,
            @RequestHeader(BoPoConstants.CLIENT_PATH) String clientPath,
            @RequestBody HashMap<String,Object> requestBody) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.post(fullUrl, requestBody, String.class, headers));
    }

    @Operation(summary = "PutMapping")
    @PermitAll
    @PutMapping
    public ResponseEntity<String> handlePutRequest(
            @RequestHeader(BoPoConstants.SERVICE_NAME) String serviceName,
            @RequestHeader(BoPoConstants.CLIENT_PATH) String clientPath,
            @RequestBody HashMap<String,Object> requestBody) {

        String fullUrl = buildFullUrl(serviceName, clientPath);
        HttpHeaders headers = createHeaders(serviceName, clientPath);
        return ResponseEntity.ok(remoteServiceFactory.put(fullUrl, requestBody, String.class, headers));
    }

    @Operation(summary = "DeleteMapping")
    @PermitAll
    @DeleteMapping
    public ResponseEntity<String> handleDeleteRequest(
            @RequestHeader(BoPoConstants.SERVICE_NAME) String serviceName,
            @RequestHeader(BoPoConstants.CLIENT_PATH) String clientPath,
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
        headers.set(BoPoConstants.SERVICE_NAME, serviceName);
        headers.set(BoPoConstants.CLIENT_PATH, clientPath);
        return headers;
    }
}