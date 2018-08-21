package com.tony.saas.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-8-20.
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/info")
    public String Hello(){
        return "hello xxx，this is demo-client1 messge";
    }
    /*
        服务提供者
     */
    @RequestMapping("/sayHello")
    public String Hello(@RequestParam("name") String name){
        return "hello " + name + "，this is tony say hello. port:"+port;
    }

}
