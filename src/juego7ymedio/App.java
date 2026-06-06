package juego7ymedio;

import javax.swing.SwingUtilities;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.dao.JugadorDAO;
import juego7ymedio.util.Sonidos;
import juego7ymedio.vista.VentanaPrincipal;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JugadorDAO jugadorDAO = new JugadorDAO();
            jugadorDAO.prepararBaseDatos();
            VentanaPrincipal ventana = new VentanaPrincipal();
            ControladorJuego controlador = new ControladorJuego(ventana, jugadorDAO);
            Sonidos.iniciarMusica();
            controlador.mostrarMenu();
            ventana.setVisible(true);
        });
    }
}
