package com.virtual.luna.controller.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class BaPoController {

    private static final Logger log = LoggerFactory.getLogger(BaPoController.class);

    /**
     * 测试 Path 请求
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String testPath(@PathVariable("id") Long id)  {
        log.info("testPath - 入参[{}]",id);
        return "Hello Test";
    }

    /**
     * RequestParam 测试
     * @return
     */
    @GetMapping("/requestParam")
    public String testRequestParam(@RequestParam String a,
                                   @RequestParam String b){
        log.info("testRequestParam - 入参[a=>{} b=>{}]",a,b);
        return a + b;
    }

}
