### Springboot整合EMqtt实现消息的发送(生产)和订阅(消费)

#### 项目结构:

    ```
      ├─springboot-mqtt-producer       Springboot + mqtt 消息发布生产模块
      ├─springboot-mqtt-consumer       Springboot + mqtt 消息订阅消费模块
      ├─docker-emqtt.md                Docker下运行EMQTT服务
      ├─.gitignore                     .gitignore文件
      ├─README.md                      README.md文件
      └─pom.xml                        父模块pom文件
     ```
#### 如何运行:
    
    1.先将EMQTT服务运行起来.
    2.运行生产者模块
    3.运行消费者模块
    4.访问生产者的http://localhost:8080/push/hello发送一条消息
    5.生产者打印接收到的消息

参考: [EMQTT官方文档](https://www.emqx.io/cn/)