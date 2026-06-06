package juego7ymedio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import juego7ymedio.config.DatabaseConfig;
import juego7ymedio.modelo.JugadorRanking;

public class JugadorDAO {
    private String ultimoError = "";

    public boolean prepararBaseDatos() {
        ultimoError = "";

        try (Connection conexion = conectar(DatabaseConfig.SERVIDOR + DatabaseConfig.PARAMETROS);
             Statement sentencia = conexion.createStatement()) {
            sentencia.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DatabaseConfig.BASE_DATOS);
        } catch (SQLException e) {
            ultimoError = e.getMessage();
            return false;
        }

        try (Connection conexion = conectarBaseDatos();
             Statement sentencia = conexion.createStatement()) {
            sentencia.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Jugadores ("
                            + "IDJugador INT AUTO_INCREMENT PRIMARY KEY,"
                            + "Apodo VARCHAR(45) NOT NULL UNIQUE,"
                            + "Victorias INT NOT NULL DEFAULT 0"
                            + ")"
            );
            return true;
        } catch (SQLException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    public boolean guardarVictoria(String apodo) {
        ultimoError = "";

        String sql = "INSERT INTO Jugadores (Apodo, Victorias) VALUES (?, 1) "
                + "ON DUPLICATE KEY UPDATE Victorias = Victorias + 1";

        try (Connection conexion = conectarBaseDatos();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, apodo);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            ultimoError = e.getMessage();
            return false;
        }
    }

    public ArrayList<JugadorRanking> obtenerRanking() {
        ultimoError = "";

        ArrayList<JugadorRanking> ranking = new ArrayList<>();
        String sql = "SELECT IDJugador, Apodo, Victorias FROM Jugadores ORDER BY Victorias DESC, Apodo ASC LIMIT 10";

        try (Connection conexion = conectarBaseDatos();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {
            int posicion = 1;
            while (resultado.next()) {
                ranking.add(new JugadorRanking(
                        posicion,
                        resultado.getInt("IDJugador"),
                        resultado.getString("Apodo"),
                        resultado.getInt("Victorias")
                ));
                posicion++;
            }
        } catch (SQLException e) {
            ultimoError = e.getMessage();
        }

        return ranking;
    }

    public String getUltimoError() {
        return ultimoError;
    }

    private Connection conectarBaseDatos() throws SQLException {
        return conectar(DatabaseConfig.SERVIDOR + DatabaseConfig.BASE_DATOS + DatabaseConfig.PARAMETROS);
    }

    private Connection conectar(String url) throws SQLException {
        SQLException ultimoFallo = null;

        for (String password : DatabaseConfig.PASSWORDS) {
            try {
                return DriverManager.getConnection(url, DatabaseConfig.USUARIO, password);
            } catch (SQLException e) {
                ultimoFallo = e;
            }
        }

        throw ultimoFallo;
    }
}
