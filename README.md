# Juego 7 y Medio

Proyecto Java Swing del juego Siete y Medio para 1º DAW

## Contenido

- `src`: codigo Java, pantallas Swing, imagenes y sonidos
- `lib`: conector JDBC de MySQL
- `database/schema.sql`: script para crear la base de datos del ranking
- `.project` y `.classpath`: configuracion para importar el proyecto en Eclipse

## Ejecucion en Eclipse

1. Abrir Eclipse
2. Ir a `File > Import > Existing Projects into Workspace`
3. Seleccionar la carpeta del proyecto
4. Revisar que `lib/mysql-connector-j-9.6.0.jar` esta en el Build Path
5. Ejecutar `juego7ymedio.App` como Java Application

## Base de datos

El ranking usa MySQL en `localhost:3306` con la base de datos `Juego_7YMedio` y la tabla `Jugadores`

La aplicacion intenta crear la base y la tabla al iniciar. Tambien se puede ejecutar manualmente el script `database/schema.sql`

Configuracion usada:

- Usuario: `root`
- Password: `Studium2025;`

## Funciones principales

- Partidas de 2 a 4 jugadores
- Cartas reales en PNG
- Musica de fondo y efectos de sonido
- Ranking de victorias guardado en MySQL
- Pantallas de menu, ayuda, ranking, seleccion, nombres y partida
- Boton para jugar otra vez con los mismos jugadores
- Estructura MVC con paquetes de modelo, vista, controlador y DAO
