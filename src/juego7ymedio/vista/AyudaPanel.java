package juego7ymedio.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.util.Recursos;

public class AyudaPanel extends PanelConFondo {
    private JTextArea texto;
    private JLabel imagen;

    public AyudaPanel(ControladorJuego controlador) {
        super("/imagenes/fondo_panel.png");
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        add(Estilos.barraTitulo("Ayuda"), BorderLayout.NORTH);

        String[] apartados = {"Objetivo", "Valor de las cartas", "Turnos", "Final de ronda"};
        JList<String> lista = new JList<>(apartados);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("Arial", Font.BOLD, 17));
        lista.setFixedCellHeight(46);
        lista.setSelectedIndex(0);
        lista.setBackground(Estilos.PANEL);
        lista.setForeground(Estilos.TEXTO);
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Arial", Font.PLAIN, 18));
        texto.setBackground(Estilos.PANEL);
        texto.setForeground(Color.BLACK);
        texto.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        imagen = new JLabel();
        imagen.setPreferredSize(new Dimension(380, 300));
        imagen.setHorizontalAlignment(JLabel.CENTER);
        imagen.setVerticalAlignment(JLabel.CENTER);
        imagen.setBorder(BorderFactory.createCompoundBorder(
                Estilos.BORDE_DORADO,
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        imagen.setOpaque(true);
        imagen.setBackground(Estilos.PANEL);

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarTexto(lista.getSelectedValue());
            }
        });

        JPanel centro = new JPanel(new BorderLayout(15, 15));
        centro.setOpaque(false);
        centro.add(new JScrollPane(lista), BorderLayout.WEST);
        centro.add(new JScrollPane(texto), BorderLayout.CENTER);
        centro.add(imagen, BorderLayout.EAST);
        add(centro, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(1, 1));
        botones.setOpaque(false);
        JButton atras = Estilos.botonIcono("Atras", "/imagenes/iconos/atras.png");
        atras.addActionListener(e -> controlador.mostrarMenu());
        botones.add(atras);
        add(botones, BorderLayout.SOUTH);

        actualizarTexto("Objetivo");
    }

    private void actualizarTexto(String apartado) {
        if ("Objetivo".equals(apartado)) {
            texto.setText("El objetivo es acercarse lo maximo posible a 7,5 puntos sin pasarse\n\nGana el jugador con la puntuacion mas alta que no supere 7,5");
            imagen.setIcon(Recursos.icono("/imagenes/ayuda_objetivo.png", 350, 245));
        } else if ("Valor de las cartas".equals(apartado)) {
            texto.setText("Las cartas del 1 al 7 valen su propio numero\n\nLa sota, el caballo y el rey valen 0,5 puntos\n\nLa baraja usada tiene oros, copas, espadas y bastos");
            imagen.setIcon(Recursos.icono("/imagenes/ayuda_cartas.png", 350, 245));
        } else if ("Turnos".equals(apartado)) {
            texto.setText("Cada jugador recibe una carta inicial\n\nEn su turno puede pedir carta o plantarse\n\nSi supera 7,5 queda eliminado de la ronda");
            imagen.setIcon(Recursos.icono("/imagenes/ayuda_turnos.png", 350, 245));
        } else {
            texto.setText("Cuando todos los jugadores se plantan o se pasan, se calcula el ganador\n\nSi varios jugadores empatan con la mejor puntuacion, se juega una muerte subita solo entre ellos");
            imagen.setIcon(Recursos.icono("/imagenes/ayuda_final.png", 350, 245));
        }
    }
}
