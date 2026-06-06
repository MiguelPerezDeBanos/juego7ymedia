package juego7ymedio.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.util.Recursos;

public class NombresJugadoresPanel extends PanelConFondo {
    private ArrayList<JTextField> campos = new ArrayList<>();

    public NombresJugadoresPanel(ControladorJuego controlador, int numeroJugadores) {
        super("/imagenes/fondo_panel.png");
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(35, 60, 35, 60));

        add(Estilos.barraTitulo("Nombres de jugadores"), BorderLayout.NORTH);

        JPanel centro = Estilos.panelClaro();
        centro.setLayout(new GridLayout(numeroJugadores, 3, 15, 15));

        for (int i = 1; i <= numeroJugadores; i++) {
            JLabel icono = new JLabel(Recursos.icono("/imagenes/iconos/jugador.png", 55, 55));
            icono.setHorizontalAlignment(JLabel.CENTER);

            JLabel label = new JLabel("Jugador " + i);
            label.setForeground(Estilos.TEXTO);
            label.setFont(new Font("Arial", Font.BOLD, 18));

            JTextField campo = new JTextField();
            campo.setFont(new Font("Arial", Font.PLAIN, 18));
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Estilos.DORADO, 1),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            campos.add(campo);

            centro.add(icono);
            centro.add(label);
            centro.add(campo);
        }

        add(centro, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(1, 2, 20, 0));
        botones.setOpaque(false);

        JButton volver = Estilos.botonOscuroIcono("Volver", "/imagenes/iconos/atras.png");
        JButton empezar = Estilos.botonOscuroIcono("Empezar", "/imagenes/iconos/iniciar.png");

        volver.addActionListener(e -> controlador.mostrarSeleccionJugadores());
        empezar.addActionListener(e -> {
            ArrayList<String> nombres = obtenerNombres();
            if (nombres != null) {
                controlador.iniciarPartida(nombres);
            }
        });

        botones.add(volver);
        botones.add(empezar);
        add(botones, BorderLayout.SOUTH);
    }

    private ArrayList<String> obtenerNombres() {
        ArrayList<String> nombres = new ArrayList<>();
        HashSet<String> repetidos = new HashSet<>();

        for (JTextField campo : campos) {
            String nombre = campo.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Rellena todos los nombres");
                return null;
            }

            if (repetidos.contains(nombre.toLowerCase())) {
                JOptionPane.showMessageDialog(this, "No repitas nombres en la misma partida");
                return null;
            }

            repetidos.add(nombre.toLowerCase());
            nombres.add(nombre);
        }

        return nombres;
    }
}
