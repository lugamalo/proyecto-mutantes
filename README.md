# prueba-mutantes

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar
contra los X-Men.
Te ha contratado a ti para que desarrolles un proyecto que detecte si un
humano es mutante basándose en su secuencia de ADN.
Para eso te ha pedido crear un programa con un método o función con la siguiente firma (En
alguno de los siguiente lenguajes: Java / Golang / C-C++ / Javascript (node) / Python / Ruby):

boolean isMutant(String[] dna); // Ejemplo Java

En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla
de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las
cuales representa cada base nitrogenada del ADN.

![image](https://user-images.githubusercontent.com/56520213/109398228-9c5ded80-7909-11eb-960e-4983e1defb0e.png)

Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras
iguales​, de forma oblicua, horizontal o vertical.
Ejemplo (Caso mutante):
String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
En este caso el llamado a la función isMutant(dna) devuelve “true”.
Desarrolla el algoritmo de la manera más eficiente posible.

# **Desafíos:**
## **Nivel 1:**
Programa (en cualquier lenguaje de programación) que cumpla con el método pedido por
Magneto.
## **Nivel 2:**
Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine,
Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es
mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el
siguiente formato:
POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un
403-Forbidden

## **Nivel 3:**
Anexar una base de datos, la cual guarde los ADN’s verificados con la API.
Solo 1 registro por ADN.
Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las
verificaciones de ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1
millón de peticiones por segundo).
Test-Automáticos, Code coverage > 80%.

# Solución del Problema

La solución del problema esta planteada para que se resuelva usando Arquitectura Serverless con el cloud provider AWS

![image](https://user-images.githubusercontent.com/56520213/109396854-9401b480-7901-11eb-9b01-7a206904ebb8.png)

# Servicios empleados de AWS
### **API Gateway** --> Publica las 2 APIs REST de los niveles 2 y 3 del desafio
### **Lambda** --> Funciones lambda que ejecutan el código Java para invocar la funcionalidad de detección de mutantes y estadísticas de verificación ADN.
### **DynamoBD** --> Base de datos NoSQL que almacena los ADNs verificados a traves del API

# **Nivel 1:** 

El algoritmo que permite detectar si es mutante dada una cadena de secuencia de ADNs fue realizado en el lenguaje de programación Java.
Se implementaron funciones lambda en lenguaje de programación Java las cuales esta usando el controlador de la función AWS Lambda. La clase Handler define un método de controlador llamado handleRequest. El método handler toma un evento y un objeto contextual como entrada y devuelve una cadena. 

![image](https://user-images.githubusercontent.com/56520213/109398064-50f70f80-7908-11eb-8051-47ec7ca225b2.png)


# **Nivel 2:**
## Method HTTP --> POST /mutant/
 URL CLoud Provider AWS  -->  https://adg27inhri.execute-api.us-east-1.amazonaws.com/test/mutant
 
 **Mensajes de Validación** &nbsp;
 * HTTP 200-OK --> Es Mutante &nbsp;
 ![image](https://user-images.githubusercontent.com/56520213/109658924-22aa4780-7b35-11eb-8fa0-b08059dae8fd.png)

 * 403-Forbidden --> Es Humano &nbsp;
 ![image](https://user-images.githubusercontent.com/56520213/109659121-5b4a2100-7b35-11eb-844d-44aa18ab14fa.png)

 * 400-Bad Request --> Cadena de secuencias invalida (nula, longitud 0 no es matriz cuadradada) &nbsp;
 
 **Prueba de Invocacion API  cliente Postman**
Valida que las letras que componen la base Nitrogenada ADN no cuente con letras invalidas
![image](https://user-images.githubusercontent.com/56520213/109657828-efb38400-7b33-11eb-8264-4e5902297ce9.png)
Valida la matriz de entrada cuente con elementos 
 ![image](https://user-images.githubusercontent.com/56520213/109397101-06bf5f80-7903-11eb-8add-d58f9b5bb75a.png)
 ![image](https://user-images.githubusercontent.com/56520213/109397875-19d42e80-7907-11eb-95a6-64d3408efa27.png)


 
#  **Nivel 3:**
## Method HTTP --> GET /stats
 URL CLoud Provider AWS -->  https://ofpupwchtl.execute-api.us-east-1.amazonaws.com/Test/stats
 
 Prueba de Invocacion cliente Postman
 ![image](https://user-images.githubusercontent.com/56520213/109397047-b21be480-7902-11eb-81ae-d4339297373c.png)
 
 # **Code Coverage > 80#**
 
 Para realizar la cobertura de pruebas en el código se uso la libreria JaCoCo, la cual generó el siguiente reporte HTML el cual describe los diferentes escenarios que se evaluaron en las pruebas realizadas al algoritmo de detección de mutantes 

 ![image](https://user-images.githubusercontent.com/56520213/109402030-75f77c80-7920-11eb-8976-a722d3d82bde.png)
 
