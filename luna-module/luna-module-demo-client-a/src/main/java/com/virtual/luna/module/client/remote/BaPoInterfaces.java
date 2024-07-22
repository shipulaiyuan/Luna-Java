package com.virtual.luna.module.client.remote;

import com.virtual.luna.infra.bridge.annotation.TongtianBridge;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@TongtianBridge("test-demo-b")
public interface BaPoInterfaces {

    /**
     * PathVariable 测试
     * @param id
     * @return
     */
    @GetMapping("/test/{id}")
    public String testPathVariable(@PathVariable("id") Long id);

    /**
     * RequestParam 测试
     * @return
     */
    @GetMapping("/test/requestParam")
    public String testRequestParam(@RequestParam String a,
                                   @RequestParam String b);

    /**
     * RequestBody 测试
     * @return
     */
    @PostMapping("/test/requestBody")
    public String testRequestBody(@RequestBody HashMap<String,Object> map);


}

