# spring-boot-websocket, 依赖spring-boot-parent
* not support session
* 

# Test
* Run application service first, then run test. 

#For SSL certificate command:
keytool -genkey -alias tomcat  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650
password:123456

#如果使用Spring Boot，且在内嵌tomcat中添加HTTPS，需要以下步骤
* 要有一个证书，买或者自己生成的
* 在Spring Boot中配置HTTPS
* 将HTTP重定向到HTTPS（可选）
