package com.tony.saas.controller;

import com.tony.saas.feign.SayHelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-8-20.
 */
@RestController
public class ConsumerController {

    @Autowired
    SayHelloClient sayHelloClient;

    @RequestMapping(value = "/sayHello",method = RequestMethod.GET)
    public String index(@RequestParam String name){
        return sayHelloClient.sayHello(name);
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String info(){
        return " Hi,I am a consumer!";
    }
}
