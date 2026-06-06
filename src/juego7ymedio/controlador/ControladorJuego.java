package juego7ymedio.controlador;

import java.util.ArrayList;

import juego7ymedio.dao.JugadorDAO;
import juego7ymedio.modelo.Partida;
import juego7ymedio.vista.AyudaPanel;
import juego7ymedio.vista.JuegoPanel;
import juego7ymedio.vista.MenuPanel;
import juego7ymedio.vista.NombresJugadoresPanel;
import juego7ymedio.vista.RankingPanel;
import juego7ymedio.vista.SeleccionJugadoresPanel;
import juego7ymedio.vista.VentanaPrincipal;

public class ControladorJuego {
    private VentanaPrincipal ventana;
    private JugadorDAO jugadorDAO;

    public ControladorJuego(VentanaPrincipal ventana, JugadorDAO jugadorDAO) {
        this.ventana = ventana;
        this.jugadorDAO = jugadorDAO;
    }

    public JugadorDAO getJugadorDAO() {
        return jugadorDAO;
    }

    public void mostrarMenu() {
        ventana.cambiarPanel(new MenuPanel(this));
    }

    public void mostrarAyuda() {
        ventana.cambiarPanel(new AyudaPanel(this));
    }

    public void mostrarRanking() {
        ventana.cambiarPanel(new RankingPanel(this));
    }

    public void mostrarSeleccionJugadores() {
        ventana.cambiarPanel(new SeleccionJugadoresPanel(this));
    }

    public void mostrarNombresJugadores(int numeroJugadores) {
        ventana.cambiarPanel(new NombresJugadoresPanel(this, numeroJugadores));
    }

    public void iniciarPartida(ArrayList<String> nombres) {
        ventana.cambiarPanel(new JuegoPanel(this, new Partida(nombres)));
    }

    public void repetirPartida(ArrayList<String> nombres) {
        iniciarPartida(new ArrayList<>(nombres));
    }

    public void salir() {
        System.exit(0);
    }
}
