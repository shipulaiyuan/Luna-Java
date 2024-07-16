package com.virtual.luna.infra.register.remote;

import com.virtual.luna.infra.register.annotation.RemoteTransferService;
import com.virtual.luna.infra.register.properties.ServiceRegisterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class RemoteServiceFactory {

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
        String fullUrl = forwardUrl + "/" + serviceName + "/" + clientPath;

        try {
            if (returnType.equals(String.class)) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    Map<String, Object> pathVariables = extractPathVariableValues(method, args);
                    fullUrl = appendPathVariables(fullUrl, pathVariables);
                    return get(fullUrl, String.class);
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    Object requestBody = extractRequestBody(args);
                    return post(fullUrl, requestBody, String.class);
                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    Object requestBody = extractRequestBody(args);
                    return put(fullUrl, requestBody, String.class);
                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    Map<String, Object> pathVariables = extractPathVariableValues(method, args);
                    fullUrl = appendPathVariables(fullUrl, pathVariables);
                    return delete(fullUrl, String.class);
                } else {
                    throw new UnsupportedOperationException("Unsupported HTTP method for returnType String");
                }
            } else if (returnType.equals(Void.TYPE)) {
                if (method.isAnnotationPresent(PostMapping.class)) {
                    Object requestBody = extractRequestBody(args);
                    post(fullUrl, requestBody, Void.class);
                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    Object requestBody = extractRequestBody(args);
                    put(fullUrl, requestBody, Void.class);
                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    Map<String, Object> pathVariables = extractPathVariableValues(method, args);
                    fullUrl = appendPathVariables(fullUrl, pathVariables);
                    delete(fullUrl, Void.class);
                } else {
                    throw new UnsupportedOperationException("Unsupported HTTP method for returnType Void");
                }
                return null;
            } else {
                throw new UnsupportedOperationException("Unsupported returnType");
            }
        } catch (Exception e) {
            // 记录日志或者抛出自定义异常
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

    private Object extractRequestBody(Object[] args) {
        for (Object arg : args) {
            if (arg != null && arg.getClass().isAnnotationPresent(RequestBody.class)) {
                return arg;
            }
        }
        return null;
    }


    private <T> T get(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
        return responseEntity.getBody();
    }

    private <T> T post(String url, Object requestBody, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, requestBody, responseType);
        return responseEntity.getBody();
    }

    private <T> T put(String url, Object requestBody, Class<T> responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
        return responseEntity.getBody();
    }

    private <T> T delete(String url, Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, responseType);
        return responseEntity.getBody();
    }
}
