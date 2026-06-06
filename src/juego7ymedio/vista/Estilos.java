package juego7ymedio.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

import juego7ymedio.util.Recursos;
import juego7ymedio.util.Sonidos;

public class Estilos {
    public static final Color FONDO = new Color(23, 34, 28);
    public static final Color VERDE_MESA = new Color(22, 103, 58);
    public static final Color VERDE_OSCURO = new Color(12, 72, 41);
    public static final Color CREMA = new Color(247, 240, 220);
    public static final Color DORADO = new Color(214, 169, 71);
    public static final Color ROJO = new Color(157, 50, 45);
    public static final Color PANEL = new Color(255, 252, 240);
    public static final Color PANEL_OSCURO = new Color(24, 43, 32);
    public static final Color TEXTO = new Color(38, 38, 34);
    public static final Border BORDE_DORADO = BorderFactory.createLineBorder(DORADO, 2);

    public static JButton boton(String texto) {
        JButton boton = new JButton(texto);
        boton.setUI(new BasicButtonUI());
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBackground(CREMA);
        boton.setForeground(TEXTO);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setMargin(new Insets(12, 18, 12, 18));
        boton.setPreferredSize(new Dimension(150, 46));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DORADO, 2),
                BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));
        boton.setIconTextGap(10);
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.addActionListener(e -> Sonidos.reproducir("click.wav"));
        activarHover(boton, CREMA, new Color(255, 247, 214));
        return boton;
    }

    public static JButton botonOscuro(String texto) {
        JButton boton = boton(texto);
        boton.setBackground(PANEL_OSCURO);
        boton.setForeground(CREMA);
        activarHover(boton, PANEL_OSCURO, new Color(33, 66, 48));
        return boton;
    }

    public static JButton botonIcono(String texto, String rutaIcono) {
        JButton boton = boton(texto);
        boton.setIcon(Recursos.icono(rutaIcono, 26, 26));
        return boton;
    }

    public static JButton botonOscuroIcono(String texto, String rutaIcono) {
        JButton boton = botonOscuro(texto);
        boton.setIcon(Recursos.icono(rutaIcono, 26, 26));
        return boton;
    }

    public static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(DORADO);
        label.setFont(new Font("Arial", Font.BOLD, 38));
        return label;
    }

    public static JPanel barraTitulo(String texto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18, 35, 26, 235));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BORDE_DORADO,
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        JLabel label = titulo(texto);
        label.setFont(new Font("Arial", Font.BOLD, 31));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static JLabel etiquetaOscura(String texto, int tamano) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(18, 35, 26, 218));
        label.setForeground(CREMA);
        label.setFont(new Font("Arial", Font.BOLD, tamano));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DORADO, 1),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));
        return label;
    }

    public static JLabel subtitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(CREMA);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        return label;
    }

    public static JLabel textoClaro(String texto, int tamano) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setForeground(CREMA);
        label.setFont(new Font("Arial", Font.PLAIN, tamano));
        return label;
    }

    public static JPanel panelClaro() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 252, 240, 238));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BORDE_DORADO,
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    private static void activarHover(JButton boton, Color normal, Color hover) {
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(hover);
                }
            }

            public void mouseExited(MouseEvent e) {
                boton.setBackground(normal);
            }
        });
    }
}
