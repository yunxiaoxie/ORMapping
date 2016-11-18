SpringBootApplication
================

The Application is a very simple and clean-design blog system implemented with Spring Boot.
It's one of my learning projects to explore awesome features in Spring Boot web programming.

Application is powered by many powerful frameworks and third-party projects:

- Spring Boot and many of Spring familiy (e.g. Spring MVC, Spring JPA, Spring Secruity and etc)
- Mybatis + MySQL
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - A solid high-performance JDBC connection pool
- [Redis](http://redis.io/) - A very powerful in-memory data cache server.

##State
-10000 login success.

## Development

Before development, please install the following service software:

- [MySQL](https://www.mysql.com)
- [Redis](http://redis.io)
- [Pygments](http://pygments.org)

Edit the spring config profile `src/main/resources/application.yml` according to your settings.

And start MySQL and Redis first before running the application.

```
# If you're using Ubuntu server

# Install MySQL
apt-get install mysql-server
service mysql start
mysql -u root -p
>> create database spring_blog;


# Install Python pygments
apt-get install python-pip
pip install pygments
```

```
# If you want to enable redis cache
# Install redis server first, you can find instructions
# from https://www.digitalocean.com/community/tutorials/how-to-install-and-use-redis
service redis_6379 start
```

This is a Gradle project. Make sure Gradle is installed in your machine.
Try `gradle -v` command. Otherwise install in from [http://www.gradle.org/](http://www.gradle.org/).
I recommend you import the source code into Intellij IDE to edit the code.

```
# Start the web application
./gradlew bootRun
```

## Development

**How to import the project into Intellij IDEA and run from the IDE?**


1. Clone the project
`git clone https://github.com/Raysmond/SpringBlog.git `
2. Download all dependencies
`cd SpringBlog `
`./gradlew idea `
3. Open the project in Intellij IDEA.
4. Run `Application.java` as Java application.
5. Preview: http://localhost:9000
    Admin: http://localhost:900/admin , the default admin account is: admin@admin.com, password: admin


> Lombok is required to run the project. You can install the plugin in Intellij IDEA.
> Reference: https://github.com/mplushnikov/lombok-intellij-plugin


## Deployment

- Build application jar `mvn clean package`.
- Copy `application.properties` to `application_production.properties`, if you want to change some configurations.
- Upload `application-production.yml` to your server and change it according to your server settings.
- Run it (Java8 is a must)

  ```
  # assuming you have the jar and yml files under current dir
  java -jar spring-boot-mybatis-sources.jar
  or
  java -jar spring-boot-mybatis-sources.jar --spring.config.location=application-production.yml
  ```

## License
Modified BSD license. Copyright (c) 2015, Jiankun LEI (Raysmond).
