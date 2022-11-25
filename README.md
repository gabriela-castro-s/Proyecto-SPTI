# Gestion-biblioteca
## Proyecto final de CVDS (Ciclos de Vida Del Desarrollo de Software). Periodo 2022-1
## Escuela Colombiana de Ingeniería Julio Garavito

### Integrantes
 * Diego Fernando Ruiz Rojas (Scrum Master)
 * Juan Diego Becerra Peña (Team developer)
 * Laura Nathalia Garcia Acuña (Team developer)
 * Hayden Esteban Cristancho Pinzón (Team developer)
### Profesor
 * Hernan Dario Tenjo Mateus (Product Owner)

## Descripción del Proyecto

## Arquitectura y diseño detallado

### Modelo E-R (Entidad-Relación)
![ER](https://media.discordapp.net/attachments/771508386452734003/976617344669474866/unknown.png)

### Diagrama de clases
![DC](https://media.discordapp.net/attachments/976630416016539688/976630426082893895/unknown.png)

### Enlace de la aplicación en Heroku
https://cvds-gestor.herokuapp.com/

### Descripción de la arquitectura y tecnologías utilizadas
La aplicación está construida en 3 capas:

#### Capa de presentación
En esta capa se usaron las siguientes tecnologias:
 *  Primefaces
 *  JSF (Java Server Faces)


#### Capa de aplicación
Aquí se usaron tecnologías:
 *  Java
 *  Google Guice (Un framework para inyección de dependencias)
 *  Maven (Herramienta para gestión de dependencias)
 *  Apache Shiro (Framework para autentiticación)

#### Capa de persistencia de datos
Como motor de bases de datos se uso **PostgreSQL** y la herramienta **myBatis**, esta se encarga de realizar la  persistencia (mappea sentencias SQL y procedimientos almacenados con objetos a partir de ficheros XML o anotaciones).

### Integración continua
![CircleCI](https://circleci.com/gh/diegoruroeci/gestion-biblioteca/tree/master.svg?style=svg)

### Calidad del codigo
Al momento de autorizar el codacy, tira un error que no permite implementar codacy dentro del proyecto.
![Codacy](https://media.discordapp.net/attachments/976630416016539688/976640238581583882/unknown.png)

### Cubrimiento de pruebas
Mediante el plugin de Jacoco se realizó el porcentaje de cubrimiento de pruebas unitarias del proyecto:

![cubrumiento](https://media.discordapp.net/attachments/976630416016539688/976639051929100308/unknown.png)   

## Descripción del proceso
Durante la realización del proyecto, se trabajo por medio de la metodología ágil Scrum. En este tiempo, se trabajaron 3 sprint cada uno con una duración de 2 semanas y que implementaron 10 historias de usuario.

## Descripción sprint 1
![BC1](https://media.discordapp.net/attachments/976630416016539688/976633717797834804/unknown.png)

 * Durante este primer sprint, teniamos 3 historias de usuario, creamos las tareas entre todos y cada designamos las mismas, tuvimos un buen desempreño en general y al concluir el tiempo de este sprint, habiamos finalizado las 3 historias de usuario de manera exitosa. Cada integrante cumplio los objetivos en el tiempo especificado. La página de incio de sesión sirve correctamente y se puede realizar ciertas funciones dependiendo del usuario que inicie sesión.


## Descripción sprint 2
![BC2](https://media.discordapp.net/attachments/976630416016539688/976634113589133332/unknown.png)

* En este sprint, teniamos 4 historias de usuario, Juan Diego Becerra tomo la historia de visualizar reserva, Nathalia Garcia tomo la historia de consultar reservas, Diego Ruiz tomó la historia de reservar un recurso y Esteban Cristancho tomo la historia de visualizar horario de reservas de recurso. En la entrega de este sprint, teniamos algunos errores al momento de realizar una reserva en el calendario y registrando un recurso necesitabamos alguna excepciones.

## Descripción sprint 3
![BC3](https://media.discordapp.net/attachments/976630416016539688/976634296720834560/unknown.png)

* En el último sprint, teniamos 3 historias de usuario, Juan Diego y Esteban tomaron la historia de Reporte de ocupación, Nathalia tomo la historia de cambiar estado de un recurso y Diego Ruiz tomo la historia de cancelación de reserva. Se solucionaron los pendientes de los sprints pasados, pero tuvimos problemas al momento de exportar los reportes al excel. Podría decirse que se cumplieron con todas las tareas solcitadas, y que el proyecto está en un excelente estado en cuanto a funcionalidad y calidad.
