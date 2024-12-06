# Proyecto POO II: Fútbol Admin

### Equipo: **_CarGer_**

#### Integrantes:

- Josué Gerardo Torres García
- Carlos Alberto Rodríguez García

#### Nombre del Proyecto:

Sistema de administración de ligas de futbol

#### Descripción del Proyecto:

El proyecto consiste en un sistema que permite a los administradores de una liga de futbol almacenar, consultar, modificar y eliminar información acerca de la misma, con el objetivo de que los usuarios de la liga puedan consultar información como partidos, clasificación y estadísticas de jugadores de una forma accesible y sencilla.

- Para ver las instrucciones de ejecución, [haz click aquí.](https://github.com/Josgtg/futbolAdmin/blob/main/instrucciones.md)

- Puedes encontrar un diagrama de vista-controlador [aquí.](https://github.com/Josgtg/futbolAdmin/blob/main/docs/diagramas/window-controller.png)

# Características

## Consulta de estadísticas

El sistema constantemente revisará la información de los partidos, actualizando la información de los equipos y jugadores en base a los eventos que hayan ocurrido en ellos.

La pantalla principal mostrará la información de la liga en curso.

Desde la pantalla principal puedes acceder a la pantalla de consulta de jugadores.

### Ordenamiento personalizado

En ambas pantallas puedes ordenar los datos en relación a una columna escribiendo un conjunto de letras.
Puedes escribir un signo "-" para ordenar de forma ascendiente después de la(s) letras de la opción a ordenar.

Por ejemplo, si escribir "n" ordena los datos en relación al nombre en orden descendiente, escribir "n-" los ordenará en orden ascendente.

### Acceso sencillo a todas las estadísticas

Desde la pantalla principal puedes acceder a los datos de un equipo escribiendo su posición en la tabla. La pantalla de equipo mostrará los datos de este así como los datos de todos los jugadores que lo conforman.

## Administración de la liga

Si eres un administrador, puedes iniciar sesión y manejar los datos de los equipos, jugadores y partidos de la liga.

### CRUD

Puedes realizar las accione de:

- Consultar
- Guardar
- Actualizar
- Eliminar

Equipos y jugadores.

La administración de la tabla de partidos es manejada en un módulo distinto, que también es parte de las opciones del administrador.

### Generación de calendarización.

En lugar de crear el matchmaking y vaciar los partidos manualmente, puedes usar la aplicación para generarlo por ti. Es una de las opciones del administrador y esta generará una calendarización estándar (todos contra todos), teniendo en cuenta todos los equipos registrados.

### Registro de partidos

Después de generar una calendarización, puedes comenzar a llenar los eventos de los partidos en una interfaz simple. Se te preguntará por los eventos del partido, (Goles, Tarjetas Amarillas, Substituciones, etc...), para después registrar el partido con los eventos dados.
El partido registrado será entonces utilizado para generar las estadísticas de la liga.
