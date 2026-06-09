# ARSW - Proyecto: Matrix Escape

**Autor:** Eduardo Rico Duarte

**Curso:** Arquitecturas de Software (ARSW)

**Institución:** Escuela Colombiana de Ingeniería Julio Garavito

---

# Introducción

El presente proyecto consiste en el desarrollo de una simulación inspirada en la película *The Matrix*, donde el personaje principal, Neo, debe escapar de la matriz alcanzando uno de los teléfonos disponibles en el mapa antes de ser capturado por los agentes.

La simulación se desarrolla sobre una cuadrícula bidimensional y permite al usuario configurar dinámicamente la cantidad de agentes, muros y teléfonos que participarán en cada ejecución. El comportamiento de todos los personajes es automático, por lo que la simulación evoluciona de manera autónoma una vez iniciada.

---

# Marco Teórico

Las aplicaciones concurrentes permiten ejecutar múltiples tareas de manera aparentemente simultánea mediante el uso de hilos (Threads). Este enfoque resulta especialmente útil en simulaciones, videojuegos y sistemas interactivos, donde diferentes elementos deben actualizar su comportamiento de forma independiente sin bloquear la ejecución general del programa.

En Java, los hilos permiten modelar entidades autónomas que evolucionan en el tiempo. Sin embargo, cuando varios componentes interactúan sobre un mismo entorno compartido, es necesario coordinar su ejecución para garantizar la consistencia del sistema y evitar comportamientos inesperados.

---

# Objetivos

## Objetivo General

Desarrollar una simulación gráfica basada en el universo de Matrix, aplicando conceptos de arquitectura de software como concurrencia  para modelar el comportamiento autónomo de múltiples entidades dentro de un entorno compartido.

## Objetivos Específicos

* Implementar una cuadrícula bidimensional que represente el entorno de la simulación.
* Permitir al usuario configurar la cantidad de agentes, muros y teléfonos antes de iniciar la ejecución.
* Implementar el comportamiento automático de Neo y los agentes.
* Gestionar condiciones de victoria y derrota.

---

# Desarrollo

## Diseño de la Simulación

Se construyó una cuadrícula de 18 × 18 posiciones, reutilizando parte de la infraestructura desarrollada previamente en el proyecto **BadDopoIceCream**, especialmente la representación matricial del tablero y la organización general del dominio.

El modelo se estructuró utilizando dos jerarquías principales:

### Entidades

Corresponden a los elementos capaces de desplazarse dentro de la matriz.

* **Neo**

  * Personaje principal.
  * Se mueve automáticamente buscando alcanzar el teléfono más cercano.
  * Pierde la partida si es alcanzado por un agente.

* **Agente**

  * Enemigo de la simulación.
  * Se mueve automáticamente persiguiendo la posición actual de Neo.
  * No puede atravesar muros ni ocupar casillas con teléfonos.

### Bloques

Representan elementos estáticos del escenario.

* **Muro**

  * Obstáculo que bloquea el movimiento de Neo y de los agentes.

* **Teléfono**

  * Punto de escape para Neo.
  * Si Neo alcanza un teléfono, la simulación finaliza exitosamente.
  * Para los agentes funciona como una casilla bloqueada.

---

## Configuración Dinámica

Antes de iniciar la simulación, el usuario puede seleccionar:

* Cantidad de agentes: entre 1 y 5.
* Cantidad de muros: entre 1 y 15.
* Cantidad de teléfonos: entre 1 y 4.

Todos los elementos son ubicados aleatoriamente dentro del tablero.

Como restricción adicional, alrededor de la posición inicial de Neo se define una zona de seguridad de tres casillas en todas las direcciones, evitando que agentes, muros o teléfonos aparezcan demasiado cerca del jugador al inicio de la partida.

---

## Interfaz Gráfica

La aplicación fue dividida en dos ventanas principales:

### Menú Principal

Permite al usuario:

* Seleccionar la cantidad de agentes.
* Seleccionar la cantidad de muros.
* Seleccionar la cantidad de teléfonos.
* Iniciar la simulación.

### Nivel de Juego

Contiene:

* Representación gráfica de la cuadrícula.
* Temporizador regresivo.
* Botón de inicio.
* Botón de pausa y reanudación.
* Visualización de Neo, agentes, muros y teléfonos mediante imágenes.

---

## Condiciones de Finalización

La simulación puede finalizar de tres formas:

### Victoria

Neo alcanza cualquiera de los teléfonos presentes en el tablero.

### Derrota por Captura

Un agente alcanza la posición ocupada por Neo.

### Derrota por Tiempo

El temporizador llega a cero antes de que Neo alcance un teléfono.

---
## Implementación de Concurrencia

Uno de los aspectos centrales del proyecto fue la incorporación de concurrencia para representar el comportamiento autónomo de los personajes de la simulación.

Cada agente y Neo se ejecutan de forma independiente mediante tareas que actualizan periódicamente su posición dentro de la matriz. De esta manera, todos los personajes pueden actuar simultáneamente sobre el mismo entorno sin depender de una secuencia estrictamente lineal de ejecución.

La actualización constante de las entidades permite simular la persecución de los agentes y la búsqueda de los teléfonos por parte de Neo, generando un comportamiento dinámico similar al observado en sistemas concurrentes reales.

Para evitar inconsistencias en el tablero, todos los movimientos se realizan respetando las restricciones definidas por los muros, los teléfonos y los límites de la cuadrícula.

---
# Ejecución 
## Compilar 
```java
javac -d bin src/main/java/dominio/*.java src/main/java/presentacion/*.java
```

## Ejecutar 
```java
java -cp bin main.java.presentacion.Matrix
```

# Evidencias

## Menú de Configuración

![alt text](imagenes/ev1.png)

---

## Simulación en Ejecución

![alt text](imagenes/ev2.png)
---

## Victoria

![alt text](imagenes/ev3.png)

---

## Derrota

![alt text](imagenes/ev4.png)

---

# Conclusiones

* Se implementó exitosamente una simulación basada en una cuadrícula bidimensional configurable por el usuario.
* La reutilización de componentes desarrollados previamente en el proyecto **BadDopoIceCream** permitió reducir el tiempo de implementación y mantener una arquitectura organizada.
* La implementación permitió aplicar conceptos de concurrencia estudiados en el curso, modelando comportamientos autónomos para Neo y los agentes mediante tareas que se ejecutan de manera independiente dentro de la simulación.
* La simulación cumple con las reglas establecidas inicialmente y presenta comportamientos autónomos tanto para Neo como para los agentes.

---

# Bibliografía

Benavides Navarro, L. D., & Gualtero Martínez, R. H. (2024). *Concurrency and Threads in Java and Go* [Course slides].

OpenAI. (2026). *ChatGPT (GPT-5.5 version)* [Large Language Model]. https://chatgpt.com/ (Used primarily as a support tool.)
