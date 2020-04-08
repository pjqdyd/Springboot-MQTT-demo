package com.pjqdyd.controller;

import com.pjqdyd.service.MqttPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**   
 * @Description:  [测试推送消息的Controller层]
 * @Author:       pjqdyd
 * @Version:      [v1.0.0]
 */

@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    private MqttPushService mqttPushService;

    @GetMapping("/hello")
    public String hello(){
        mqttPushService.sendToMqtt("springboot_topic", "Hello World!");
        return "push success!";
    }

}
