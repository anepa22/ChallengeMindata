# ChallengeMindata

## Paso 1
Clonar el proyecto

## Paso 2
En la raiz del proyecto ejecutar: **docker build -t img-ojdk11-ch-mindata .**

## Paso 3
Ejecutar: **sudo docker run -p 8080:8080 -p 9990:9990 img-ojdk11-ch-mindata**

## Servicios Disponibles
Actuator para monitoreo.
http://localhost:8080/actuator

Swagger para doc
http://localhost:8080/v2/api-docs

Swagger-Ui para doc.
http://localhost:8080/swagger-ui.html

## Paso 4 Autenticacion
http://localhost:8080/auth/authUser
Post
Request

{
  "password": "string",
  "userName": "string"
}
Parameter content type
application/json

Response (Por cuestiones de practicidad el token esta en el body)

Ej:
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmVwYSIsImlzcyI6Ik1pbkRhdGEiLCJleHAiOjE2MzU4ODAzOTMsImlhdCI6MTYzNTg3OTQ5M30.E5aexL_jS0-tNskuE8FP9_-Fz4gKujXgCH_pdnS49pg
