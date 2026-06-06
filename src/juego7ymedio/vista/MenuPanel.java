package juego7ymedio.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.util.Recursos;
import juego7ymedio.util.Sonidos;

public class MenuPanel extends PanelConFondo {
    public MenuPanel(ControladorJuego controlador) {
        super("/imagenes/fondo_menu.png");
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(24, 70, 24, 70));

        JPanel cabecera = new JPanel();
        cabecera.setOpaque(false);
        cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
        cabecera.add(Box.createVerticalStrut(4));

        JLabel logo = new JLabel(Recursos.icono("/imagenes/logo.png", 570, 158));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        cabecera.add(logo);
        cabecera.add(Box.createVerticalStrut(8));
        JPanel subtitulo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        subtitulo.setOpaque(false);
        subtitulo.add(Estilos.etiquetaOscura("Juega con hasta 4 jugadores", 16));
        cabecera.add(subtitulo);
        add(cabecera, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(5, 1, 0, 10));
        centro.setOpaque(false);
        centro.setPreferredSize(new Dimension(410, 292));

        JButton iniciar = Estilos.botonOscuroIcono("Iniciar partida", "/imagenes/iconos/iniciar.png");
        JButton ayuda = Estilos.botonOscuroIcono("Ayuda", "/imagenes/iconos/ayuda.png");
        JButton ranking = Estilos.botonOscuroIcono("Ranking de victorias", "/imagenes/iconos/ranking.png");
        JButton musica = Estilos.botonOscuroIcono(Sonidos.isMusicaActiva() ? "Musica activada" : "Musica pausada", "/imagenes/iconos/musica.png");
        JButton salir = Estilos.botonOscuroIcono("Salir", "/imagenes/iconos/salir.png");

        iniciar.addActionListener(e -> controlador.mostrarSeleccionJugadores());
        ayuda.addActionListener(e -> controlador.mostrarAyuda());
        ranking.addActionListener(e -> controlador.mostrarRanking());
        musica.addActionListener(e -> musica.setText(Sonidos.cambiarMusica() ? "Musica activada" : "Musica pausada"));
        salir.addActionListener(e -> controlador.salir());

        centro.add(iniciar);
        centro.add(ayuda);
        centro.add(ranking);
        centro.add(musica);
        centro.add(salir);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(new Color(18, 35, 26, 230));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                Estilos.BORDE_DORADO,
                BorderFactory.createEmptyBorder(16, 24, 16, 24)
        ));
        tarjeta.add(centro, BorderLayout.CENTER);

        JPanel contenedor = new JPanel();
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        contenedor.add(tarjeta);
        add(contenedor, BorderLayout.CENTER);
    }
}
