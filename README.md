# Prueba Técnica
#### Arquitectura Microservicio (Nivel Intermedio)

## Autor: Kevin Chamorro

## Para la contruccion de la solucion se utilizo lo siguiente:

- Gradle 7.4.2
- Java 11
- Spring Boot 2.6.11
- Docker 4.9.1
- Postgresql 14.2
- IntelliJ IDEA

## Proceso para levantar la solucion

1. Clonar el repositorio:</br>
``git clone ..``
2. Copiar IP de su maquina local en application.yml ubicado en:</br>
``\src\main\resources\application.yml``
3. Creacion de la base de datos:</br>
``docker run -d --name db-bp-kch -e POSTGRES_DB=db-bp-kch-db -e POSTGRES_USER=db-bp-kch-user -e POSTGRES_PASSWORD=db-bp-kch-pwd-2022 -p 5432:5432 postgres``
4. Posicionarse dentro del repo clonado y ejecutar el siguiente comando para crear la imagen en docker:</br>
``docker build -t kevinchamorro-bancopichincha .``
5. Posicionarse dentro del repo clonado y ejecutar el siguiente comando para ejecutar el servicio:</br>
``docker run -dp 8081:8080 kevinchamorro-bancopichincha``
6. Importar en POSTMAN el archivo ``postmant.json`` que se encuentra en la raiz del repo.
7. Una vez importado se podra realizar las acciones solicitadas.





