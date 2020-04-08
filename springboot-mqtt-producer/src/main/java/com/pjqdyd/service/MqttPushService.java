package com.pjqdyd.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**   
 * @Description:  [消息推送服务的接口]
 * @Author:       pjqdyd
 * @Version:      [v1.0.0]
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttPushChannel")
public interface MqttPushService {

    /**
     * 发送信息到MQTT服务器
     * @param data 发送的文本(payload)
     */
    void sendToMqtt(String data);

    /**
     * 发送信息到MQTT服务器
     * @param topic 主题
     * @param data 消息主体(payload)
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);


    /**
     * 发送信息到MQTT服务器
     * @param topic
     * @param qos 对消息处理的几种机制,
     *     0 表示的是如果订阅者没收到消息不会再次发送，消息会丢失 (只发送一次),
     *     1 表示的是会尝试重试，一直到接收到消息，但这种情况可能导致订阅者收到多次重复消息
     *     2 多了一次去重的动作，确保订阅者收到的消息有一次
     * @param data
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC)String topic,
                    @Header(MqttHeaders.QOS) int qos,
                    String data);

}
