package com.tony.saas.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2018-8-20.
 */
//name:远程服务名，即spring.application.name配置的名称
@Service
@FeignClient(name= "eureka-provider")
public interface SayHelloClient {

    //需要匹配服务提供者接口名称
    @RequestMapping(value = "/sayHello")
    public String sayHello(@RequestParam(value="name") String name);

}
