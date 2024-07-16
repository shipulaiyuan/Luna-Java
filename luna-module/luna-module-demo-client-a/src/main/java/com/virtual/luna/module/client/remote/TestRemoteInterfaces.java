package com.virtual.luna.module.client.remote;

import com.virtual.luna.infra.register.annotation.RemoteTransferService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RemoteTransferService("test-demo-b")
public interface TestRemoteInterfaces {

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
    @GetMapping("/test")
    public String testRequestParam(@RequestParam String a,
                                   @RequestParam String b);

    /**
     * RequestBody 测试
     * @return
     */
    @PostMapping("/test/requestBody")
    public String testRequestBody(@RequestBody HashMap<String,Object> map);

}

