# Ecommerce Microservices

Proyecto de aprendizaje basado en arquitectura de microservicios con Spring Boot.

## Services

- gateway-service (8080)
- auth-service (8081)
- users-service (8082)
- products-service (8083)
- orders-service (8084)

Cada servicio es independiente y tiene su propia base de datos.

## Requirements

- Java 11
- Maven

## Environment Variables

See each service README for details.

## Run

Run each service from its folder:

mvn spring-boot:run
