//package com.spring.smpor.demo.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * nacos
// *
// * @author fanlz
// * @date 2022/07/06 14:44
// **/
//@RestController
//@RequestMapping("config")
//@RefreshScope
//public class NacosDemoController {
//    @Value("${name}")
//    private String name;
//    @GetMapping("/get")
//    public String hell(){
//        return name;
//    }
//}
