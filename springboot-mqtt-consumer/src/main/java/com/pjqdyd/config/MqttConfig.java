package com.pjqdyd.config;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**   
 * @Description:  [MQTT配置类]
 * @Author:       pjqdyd
 * @Version:      [v1.0.0]
 */

@Data
@Configuration
@IntegrationComponentScan
@ConfigurationProperties("spring.mqtt")
public class MqttConfig {

    private String username;   //用户名
    private String password;   //密码
    private String hostUrl;    //MQTT服务地址
    private String clientId;   //客户端ID
    private String defaultTopic; //默认主题
    private int timeout;         //超时时间
    private int keepalive;       //连接数

    //配置连接器选项
    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{hostUrl});
        options.setConnectionTimeout(timeout);
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*keepalive秒的时间向客户端发送心跳判断客户端是否在线,但这个方法并没有重连的机制
        options.setKeepAliveInterval(keepalive);
        // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        options.setWill("will_topic", "离线".getBytes(), 2, false);
        return options;
    }

    //MQTT客户端
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    //接收的通道Channel
    @Bean(name = "mqttInputChannel")
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    //配置client,可以监听多个topic, 消费消息
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId + "_consumer", mqttClientFactory(),
                        defaultTopic, "springboot_topic", "will_topic");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel()); //设置订阅消费的通道
        return adapter;
    }


    //消费消息的处理Handler
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("接收消息:===============>" + message.getPayload());
            }
        };
    }

}
