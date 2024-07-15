package com.virtual.luna.framework.sms.utils;

import com.alibaba.fastjson.JSONObject;
import com.virtual.luna.framework.sms.config.SmsProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmsUtil {
    private static final Logger LOGGER = Logger.getLogger(SmsUtil.class.getName());
    private final SmsProperties smsProperties;

    public SmsUtil() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SmsProperties.class);
        context.register(PropertySourcesPlaceholderConfigurer.class);
        context.refresh();
        this.smsProperties = context.getBean(SmsProperties.class);
    }

    public Map<String, Object> sendSms(String tel, String param) {
        String result;

        /**
         * 应用：
         * String param = String.format("**code**:%s,**time**:%s", "1234", "15分钟");
         * Map<String, Object> response = smsUtil.sendSms("1234567890", param);
         */
        Map<String, String> querys = Map.of(
                "mobile", tel,
                "param", param,
                "smsSignId", smsProperties.getSmsSignId(),
                "templateId", smsProperties.getTemplateId()
        );

        String queryString = querys.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((entry1, entry2) -> entry1 + "&" + entry2)
                .orElse("");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(smsProperties.getHost() + smsProperties.getPath() + "?" + queryString))
                    .header("Authorization", "APPCODE " + smsProperties.getAppcode())
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();

            return parseResult(result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to send SMS", e);
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", e.getMessage());
            return errorMap;
        }
    }

    private static Map<String, Object> parseResult(String result) {
        if (result == null || result.isEmpty()) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", "Empty response");
            return errorMap;
        }
        return JSONObject.parseObject(result, HashMap.class);
    }
}


