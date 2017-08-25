# gradle-spring-boot
Build Spring Boot project using Gradle

#How to build
./gradlew build
./gradlew -x task --skip

#How to run
./gradlew bootRun

#How to deploy
./gradlew bootRun > system.log &

#How to close
jobs --show job
ps -ef|grep java  --show process
kill -9 id