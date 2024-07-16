package com.virtual.luna.controller.publish;

import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRemoteController {

    private static final Logger log = LoggerFactory.getLogger(TestRemoteController.class);

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

}
