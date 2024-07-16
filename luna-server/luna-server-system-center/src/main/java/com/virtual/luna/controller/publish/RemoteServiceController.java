package com.virtual.luna.controller.publish;

import com.virtual.luna.common.base.constant.Constants;
import com.virtual.luna.common.base.constant.HttpStatus;
import com.virtual.luna.infra.register.remote.RemoteServiceFactory;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RemoteServiceController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/{serviceName}/{clientPath}")
    public ResponseEntity<String> handleGetRequest(
            @PathVariable String serviceName,
            @PathVariable String clientPath,
            @RequestParam Map<String, String> queryParams) {
        String fullUrl = buildFullUrl(serviceName, clientPath);
        return restTemplate.getForEntity(fullUrl, String.class, queryParams);
    }

    @PostMapping("/{serviceName}/{clientPath}")
    public ResponseEntity<String> handlePostRequest(
            @PathVariable String serviceName,
            @PathVariable String clientPath,
            @RequestBody String requestBody) {
        String fullUrl = buildFullUrl(serviceName, clientPath);
        return restTemplate.postForEntity(fullUrl, requestBody, String.class);
    }

    @PutMapping("/{serviceName}/{clientPath}")
    public ResponseEntity<String> handlePutRequest(
            @PathVariable String serviceName,
            @PathVariable String clientPath,
            @RequestBody String requestBody) {
        String fullUrl = buildFullUrl(serviceName, clientPath);
        return restTemplate.exchange(fullUrl, HttpMethod.PUT, new HttpEntity<>(requestBody), String.class);
    }

    @DeleteMapping("/{serviceName}/{clientPath}")
    public ResponseEntity<String> handleDeleteRequest(
            @PathVariable String serviceName,
            @PathVariable String clientPath) {
        String fullUrl = buildFullUrl(serviceName, clientPath);
        return restTemplate.exchange(fullUrl, HttpMethod.DELETE, null, String.class);
    }

    private String buildFullUrl(String serviceName, String clientPath) {
//        String host = remoteServiceFactory.getServiceHost(serviceName);
//        int port = remoteServiceFactory.getServicePort(serviceName);
        return Constants.HTTP + "127.0.0.1" + ":" + 11002 + "/" + clientPath;
    }
}
