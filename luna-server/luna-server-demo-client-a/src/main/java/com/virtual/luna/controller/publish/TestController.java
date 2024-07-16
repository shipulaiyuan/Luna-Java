//package com.virtual.luna.controller.publish;
//
//import com.virtual.luna.module.client.remote.TestRemoteInterfaces;
//import jakarta.annotation.Resource;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//    @Resource
//    private TestRemoteInterfaces testRemoteInterfaces;
//
//    @GetMapping
//    public String testPath()  {
//        String s = testRemoteInterfaces.testPath(123L);
//        System.out.println(s);
//        return s;
//    }
//
//}
