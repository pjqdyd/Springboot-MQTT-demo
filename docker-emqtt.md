
#### Docker运行EMQTT服务器的方式

1. 下载镜像: ` docker pull emqx/emqx:v3.1.0`

2. 启动容器: `docker run -d --name emqx31 -p 1883:1883 -p 8083:8083 -p 8883:8883 -p 8084:8084 -p 18083:18083 emqx/emqx:v3.1.0`

3. 启动容器后浏览器访问: http://docker主机ip:18083进入Web控制台,
   账户: admin 密码: public
 
   
 参考[官方文档](https://docs.emqx.io/broker/v3/cn/getstarted.html)