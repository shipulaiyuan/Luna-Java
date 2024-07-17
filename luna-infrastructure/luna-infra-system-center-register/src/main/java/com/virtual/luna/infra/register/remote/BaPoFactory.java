package com.virtual.luna.infra.register.remote;

import com.virtual.luna.infra.register.annotation.RemoteTransferService;
import com.virtual.luna.infra.register.constant.BoPoConstants;
import com.virtual.luna.infra.register.properties.ServiceRegisterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class BaPoFactory {



    @Autowired
    private ServiceRegisterProperties serviceRegisterProperties;

    @Autowired
    private RestTemplate restTemplate;

    public <T> T create(Class<T> remoteServiceInterface) {
        return (T) Proxy.newProxyInstance(
                remoteServiceInterface.getClassLoader(),
                new Class[]{remoteServiceInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RemoteTransferService annotation = remoteServiceInterface.getAnnotation(RemoteTransferService.class);
                        String serviceName = annotation.value();
                        String forwardUrl = serviceRegisterProperties.getRegistration().getForward();

                        // 构建远程调用的路径
                        String clientPath = buildClientPath(method);

                        // 发起远程调用
                        return forwardServer(serviceName, forwardUrl, clientPath, args, method.getReturnType(), method);
                    }
                });
    }

    public Object forwardServer(String serviceName, String forwardUrl, String clientPath, Object[] args, Class<?> returnType, Method method) {
        String fullUrl = forwardUrl;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(BoPoConstants.SERVICE_NAME, serviceName);
            headers.set(BoPoConstants.CLIENT_PATH, clientPath);

            if (returnType.equals(String.class)) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    Map<String, Object> pathVariables = extractPathVariableValues(method, args);
                    Map<String, String> queryParams = extractRequestParamValues(method, args);
                    clientPath = appendPathVariables(clientPath, pathVariables);
                    headers.set(BoPoConstants.CLIENT_PATH, clientPath);
                    return get(fullUrl, queryParams, String.class, headers);
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    Object requestBody = extractRequestBody(method, args);
                    headers.set("Content-Type", "application/json");
                    return post(fullUrl, requestBody, String.class, headers);
                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    Object requestBody = extractRequestBody(method, args);
                    headers.set("Content-Type", "application/json");
                    return put(fullUrl, requestBody, String.class, headers);
                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    Map<String, Object> pathVariables = extractPathVariableValues(method, args);
                    Map<String, String> queryParams = extractRequestParamValues(method, args);
                    clientPath = appendPathVariables(clientPath, pathVariables);
                    headers.set(BoPoConstants.CLIENT_PATH, clientPath);
                    return delete(fullUrl, queryParams, String.class, headers);
                } else {
                    throw new UnsupportedOperationException("Unsupported HTTP method for returnType String");
                }
            } else if (returnType.equals(Void.TYPE)) {
                if (method.isAnnotationPresent(PostMapping.class)) {
                    Object requestBody = extractRequestBody(method, args);
                    headers.set("Content-Type", "application/json");
                    post(fullUrl, requestBody, Void.class, headers);
                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    Object requestBody = extractRequestBody(method, args);
                    headers.set("Content-Type", "application/json");
                    put(fullUrl, requestBody, Void.class, headers);
                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    Map<String, Object> pathVariables = extractPathVariableValues(method, args);
                    Map<String, String> queryParams = extractRequestParamValues(method, args);
                    clientPath = appendPathVariables(clientPath, pathVariables);
                    headers.set(BoPoConstants.CLIENT_PATH, clientPath);
                    delete(fullUrl, queryParams, Void.class, headers);
                } else {
                    throw new UnsupportedOperationException("Unsupported HTTP method for returnType Void");
                }
                return null;
            } else {
                throw new UnsupportedOperationException("Unsupported returnType");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to invoke remote service", e);
        }
    }

    private String appendPathVariables(String url, Map<String, Object> pathVariables) {
        if (pathVariables != null && !pathVariables.isEmpty()) {
            for (Map.Entry<String, Object> entry : pathVariables.entrySet()) {
                url = url.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
        }
        return url;
    }

    private String buildClientPath(Method method) {
        String clientPath = "";
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            clientPath = getMapping.value()[0];
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            clientPath = postMapping.value()[0];
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping putMapping = method.getAnnotation(PutMapping.class);
            clientPath = putMapping.value()[0];
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
            clientPath = deleteMapping.value()[0];
        }
        return clientPath;
    }

    private Map<String, Object> extractPathVariableValues(Method method, Object[] args) {
        Map<String, Object> pathVariables = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof PathVariable) {
                    PathVariable pathVariable = (PathVariable) annotation;
                    pathVariables.put(pathVariable.value(), args[i]);
                }
            }
        }
        return pathVariables;
    }

    private Map<String, String> extractRequestParamValues(Method method, Object[] args) {
        Map<String, String> requestParams = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof RequestParam) {
                    RequestParam requestParam = (RequestParam) annotation;
                    String paramName = requestParam.value();
                    if (paramName.isEmpty()) {
                        paramName = method.getParameters()[i].getName();
                    }
                    requestParams.put(paramName, args[i].toString());
                }
            }
        }
        return requestParams;
    }

    private Object extractRequestBody(Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof RequestBody) {
                    return args[i];
                }
            }
        }
        return null;
    }

    public <T> T get(String url, Map<String, String> queryParams, Class<T> responseType, HttpHeaders headers) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, responseType);
        return responseEntity.getBody();
    }

    public <T> T post(String url, Object requestBody, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        return responseEntity.getBody();
    }

    public  <T> T put(String url, Object requestBody, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        return responseEntity.getBody();
    }

    public <T> T delete(String url, Map<String, String> queryParams, Class<T> responseType, HttpHeaders headers) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, responseType);
        return responseEntity.getBody();
    }
}
