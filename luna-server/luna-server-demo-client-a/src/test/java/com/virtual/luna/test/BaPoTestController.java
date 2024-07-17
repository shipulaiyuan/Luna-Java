package com.virtual.luna.test;

import com.virtual.luna.infra.register.remote.BaPoFactory;
import com.virtual.luna.module.client.remote.BaPoInterfaces;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@SpringBootTest
public class BaPoTestController {

    @Autowired
    private BaPoFactory remoteServiceFactory;

    @Test
    public void testPath() {
        BaPoInterfaces remoteService = (BaPoInterfaces) remoteServiceFactory.create(BaPoInterfaces.class);

        // 测试 GetMapping  Path
        String result = remoteService.testPathVariable(12312L);

        System.out.println(result);

        // 测试 GetMapping RequestParam
        String s = remoteService.testRequestParam("1111", "2222");

        System.out.println(s);

        // 测试 PostMapping RequestBody
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", new HashMap<String, Object>() {
            {
                put("222", "333");
            }
        });
        String s1 = remoteService.testRequestBody(map);
        System.out.println(s1);
    }
}

