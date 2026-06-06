package juego7ymedio.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.util.Recursos;

public class SeleccionJugadoresPanel extends PanelConFondo {
    public SeleccionJugadoresPanel(ControladorJuego controlador) {
        super("/imagenes/fondo_panel.png");
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        add(Estilos.barraTitulo("Nueva partida"), BorderLayout.NORTH);

        JPanel centro = Estilos.panelClaro();
        centro.setLayout(new BorderLayout(25, 25));

        JLabel label = new JLabel("Numero de jugadores", SwingConstants.CENTER);
        label.setForeground(Estilos.TEXTO);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JComboBox<Integer> jugadores = new JComboBox<>(new Integer[]{2, 3, 4});
        jugadores.setFont(new Font("Arial", Font.BOLD, 24));
        jugadores.setPreferredSize(new Dimension(160, 52));
        jugadores.setMaximumSize(new Dimension(160, 52));

        JLabel imagen = new JLabel(Recursos.icono("/imagenes/iconos/jugadores.png", 210, 210));
        imagen.setHorizontalAlignment(JLabel.CENTER);

        JPanel selector = new JPanel();
        selector.setLayout(new BoxLayout(selector, BoxLayout.Y_AXIS));
        selector.setOpaque(false);
        label.setAlignmentX(CENTER_ALIGNMENT);
        jugadores.setAlignmentX(CENTER_ALIGNMENT);
        selector.add(Box.createVerticalGlue());
        selector.add(label);
        selector.add(Box.createVerticalStrut(24));
        selector.add(jugadores);
        selector.add(Box.createVerticalGlue());

        centro.add(imagen, BorderLayout.WEST);
        centro.add(selector, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(1, 2, 20, 0));
        botones.setOpaque(false);

        JButton cancelar = Estilos.botonOscuroIcono("Cancelar", "/imagenes/iconos/atras.png");
        JButton continuar = Estilos.botonOscuroIcono("Continuar", "/imagenes/iconos/confirmar.png");

        cancelar.addActionListener(e -> controlador.mostrarMenu());
        continuar.addActionListener(e -> controlador.mostrarNombresJugadores((Integer) jugadores.getSelectedItem()));

        botones.add(cancelar);
        botones.add(continuar);
        add(botones, BorderLayout.SOUTH);
    }
}
