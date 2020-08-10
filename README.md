# spring-data-redis-mysql
You have to install redis in your local with port "6379" before run this application

mvn spring-boot:run

GET http://localhost:8080/redis/student?studentId=12345

DELETE http://localhost:8080/redis/student#




# Issue
spring repository findById() is working fine on spring-data-redis version 2.3.1.RELEASE

its failed on spring-data-redis version 2.3.2.RELEASE



My logic code is very simple:

if studentId found, return existing RedisStudent object.

else create new RedisStudent & store in Redis, return new RedisStudent object.
