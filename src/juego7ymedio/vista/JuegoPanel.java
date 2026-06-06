package juego7ymedio.vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.modelo.Carta;
import juego7ymedio.modelo.Jugador;
import juego7ymedio.modelo.Partida;
import juego7ymedio.util.Recursos;
import juego7ymedio.util.Sonidos;

public class JuegoPanel extends PanelConFondo {
    private ControladorJuego controlador;
    private Partida partida;
    private JLabel turno;
    private JLabel mensaje;
    private JLabel jugadorActivo;
    private JLabel puntosActivo;
    private JLabel fasePartida;
    private JPanel listaJugadores;
    private JTextArea historial;
    private MesaPanel mesa;
    private JButton pedirCarta;
    private JButton plantarse;
    private JButton repetir;
    private boolean victoriaGuardada;

    public JuegoPanel(ControladorJuego controlador, Partida partida) {
        super("/imagenes/fondo_panel.png");
        this.controlador = controlador;
        this.partida = partida;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearCabecera(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setOpaque(false);
        mesa = new MesaPanel();
        centro.add(mesa, BorderLayout.CENTER);
        centro.add(crearPanelLateral(), BorderLayout.EAST);
        add(centro, BorderLayout.CENTER);

        pedirCarta = Estilos.botonOscuroIcono("Pedir carta", "/imagenes/iconos/pedir.png");
        plantarse = Estilos.botonOscuroIcono("Plantarse", "/imagenes/iconos/plantarse.png");
        repetir = Estilos.botonOscuroIcono("Jugar otra vez", "/imagenes/iconos/repetir.png");
        JButton finalizar = Estilos.botonOscuroIcono("Menu", "/imagenes/iconos/menu.png");

        pedirCarta.addActionListener(e -> {
            partida.pedirCarta();
            Sonidos.reproducir("carta.wav");
            registrarMensaje(partida.getMensaje());
            actualizar();
        });

        plantarse.addActionListener(e -> {
            partida.plantarse();
            Sonidos.reproducir("plantarse.wav");
            registrarMensaje(partida.getMensaje());
            actualizar();
        });

        finalizar.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, "Quieres volver al menu principal", "Finalizar partida", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                controlador.mostrarMenu();
            }
        });

        repetir.addActionListener(e -> {
            if (!partida.isFinalizada()) {
                int opcion = JOptionPane.showConfirmDialog(this, "Quieres reiniciar la partida con los mismos jugadores", "Jugar otra vez", JOptionPane.YES_NO_OPTION);
                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            controlador.repetirPartida(partida.getNombresJugadores());
        });

        JPanel botones = new JPanel(new GridLayout(1, 4, 12, 0));
        botones.setOpaque(false);
        botones.add(pedirCarta);
        botones.add(plantarse);
        botones.add(repetir);
        botones.add(finalizar);
        add(botones, BorderLayout.SOUTH);

        registrarMensaje(partida.getMensaje());
        actualizar();
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout(12, 0));
        cabecera.setBackground(new Color(24, 43, 32, 225));
        cabecera.setBorder(BorderFactory.createCompoundBorder(
                Estilos.BORDE_DORADO,
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));

        JLabel ficha = new JLabel(Recursos.icono("/imagenes/iconos/cartas.png", 54, 54));
        turno = Estilos.subtitulo("");
        mensaje = Estilos.subtitulo("");

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(turno);
        textos.add(mensaje);

        cabecera.add(ficha, BorderLayout.WEST);
        cabecera.add(textos, BorderLayout.CENTER);
        return cabecera;
    }

    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(280, 540));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 252, 240, 238));
        panel.setBorder(BorderFactory.createCompoundBorder(
                Estilos.BORDE_DORADO,
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        JLabel titulo = new JLabel("Marcador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Estilos.PANEL_OSCURO);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        jugadorActivo = new JLabel("", SwingConstants.CENTER);
        jugadorActivo.setFont(new Font("Arial", Font.BOLD, 18));
        jugadorActivo.setForeground(Estilos.TEXTO);
        jugadorActivo.setAlignmentX(CENTER_ALIGNMENT);

        puntosActivo = new JLabel("", SwingConstants.CENTER);
        puntosActivo.setFont(new Font("Arial", Font.BOLD, 30));
        puntosActivo.setForeground(Estilos.ROJO);
        puntosActivo.setAlignmentX(CENTER_ALIGNMENT);

        fasePartida = new JLabel("", SwingConstants.CENTER);
        fasePartida.setFont(new Font("Arial", Font.PLAIN, 14));
        fasePartida.setForeground(Estilos.TEXTO);
        fasePartida.setAlignmentX(CENTER_ALIGNMENT);

        listaJugadores = new JPanel();
        listaJugadores.setLayout(new BoxLayout(listaJugadores, BoxLayout.Y_AXIS));
        listaJugadores.setOpaque(false);

        historial = new JTextArea();
        historial.setEditable(false);
        historial.setLineWrap(true);
        historial.setWrapStyleWord(true);
        historial.setFont(new Font("Arial", Font.PLAIN, 12));
        historial.setBackground(new Color(40, 58, 44));
        historial.setForeground(Estilos.CREMA);
        historial.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(historial);
        scroll.setPreferredSize(new Dimension(250, 130));
        scroll.setBorder(BorderFactory.createLineBorder(Estilos.DORADO, 1));

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(jugadorActivo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(puntosActivo);
        panel.add(fasePartida);
        panel.add(Box.createVerticalStrut(12));
        panel.add(listaJugadores);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Historial"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(scroll);

        return panel;
    }

    private void actualizar() {
        Jugador actual = partida.getJugadorActual();

        if (partida.isFinalizada()) {
            pedirCarta.setEnabled(false);
            plantarse.setEnabled(false);

            if (partida.getGanador() != null) {
                turno.setText("Partida finalizada");
                mensaje.setText(partida.getMensaje());

                if (!victoriaGuardada) {
                    victoriaGuardada = true;
                    boolean guardado = controlador.getJugadorDAO().guardarVictoria(partida.getGanador().getNombre());
                    if (guardado) {
                        Sonidos.reproducir("victoria.wav");
                        JOptionPane.showMessageDialog(this, "Gana " + partida.getGanador().getNombre() + " y se guarda en el ranking\nPuedes jugar otra vez con los mismos jugadores");
                    } else {
                        JOptionPane.showMessageDialog(this, "Gana " + partida.getGanador().getNombre() + " pero no se pudo guardar en MySQL\nPuedes jugar otra vez con los mismos jugadores");
                    }
                }
            } else {
                turno.setText("Partida finalizada");
                mensaje.setText(partida.getMensaje());
                if (!victoriaGuardada) {
                    victoriaGuardada = true;
                    JOptionPane.showMessageDialog(this, "No hay ganador en esta partida\nPuedes jugar otra vez con los mismos jugadores");
                }
            }
        } else {
            pedirCarta.setEnabled(true);
            plantarse.setEnabled(true);
            turno.setText("Turno de " + actual.getNombre());
            mensaje.setText(partida.getMensaje());
        }

        actualizarMarcador();
        mesa.repaint();
    }

    private void actualizarMarcador() {
        Jugador actual = partida.getJugadorActual();

        if (partida.isFinalizada()) {
            jugadorActivo.setText("Finalizada");
            puntosActivo.setText(partida.getGanador() == null ? "0" : partida.formatearPuntos(partida.getGanador().getPuntuacion()));
            fasePartida.setText(partida.getGanador() == null ? "Sin ganador" : "Ganador: " + partida.getGanador().getNombre());
        } else {
            jugadorActivo.setText(actual.getNombre());
            puntosActivo.setText(partida.formatearPuntos(actual.getPuntuacion()));
            fasePartida.setText(partida.isMuerteSubita() ? "Muerte subita" : "Ronda normal");
        }

        listaJugadores.removeAll();
        for (Jugador jugador : partida.getJugadores()) {
            listaJugadores.add(crearFilaJugador(jugador));
            listaJugadores.add(Box.createVerticalStrut(5));
        }
        listaJugadores.revalidate();
        listaJugadores.repaint();
    }

    private JPanel crearFilaJugador(Jugador jugador) {
        boolean activo = jugador == partida.getJugadorActual() && !partida.isFinalizada();

        JPanel fila = new JPanel(new BorderLayout(8, 0));
        fila.setMaximumSize(new Dimension(250, 48));
        fila.setBackground(activo ? new Color(245, 232, 179) : new Color(245, 239, 220));
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(activo ? Estilos.DORADO : new Color(190, 181, 150), 1),
                BorderFactory.createEmptyBorder(5, 6, 5, 6)
        ));

        JLabel icono = new JLabel(Recursos.icono(activo ? "/imagenes/iconos/marcador.png" : "/imagenes/iconos/jugador.png", 34, 34));
        JLabel nombre = new JLabel(jugador.getNombre());
        nombre.setFont(new Font("Arial", Font.BOLD, 13));
        nombre.setForeground(Estilos.TEXTO);

        JLabel estado = new JLabel(estadoJugador(jugador), SwingConstants.RIGHT);
        estado.setFont(new Font("Arial", Font.PLAIN, 12));
        estado.setForeground(Estilos.TEXTO);

        fila.add(icono, BorderLayout.WEST);
        fila.add(nombre, BorderLayout.CENTER);
        fila.add(estado, BorderLayout.EAST);

        return fila;
    }

    private String estadoJugador(Jugador jugador) {
        if (jugador.isEliminado()) {
            return "Pasado";
        }
        if (partida.isFinalizada() || jugador == partida.getJugadorActual()) {
            return partida.formatearPuntos(jugador.getPuntuacion());
        }
        if (jugador.isPlantado()) {
            return "Plantado";
        }
        return "Espera";
    }

    private void registrarMensaje(String texto) {
        if (historial != null && texto != null && !texto.isEmpty()) {
            historial.append("- " + texto + "\n");
            historial.setCaretPosition(historial.getDocument().getLength());
        }
    }

    private class MesaPanel extends JPanel {
        private Image tapete = Recursos.imagen("/imagenes/tapete.png");
        private Image mazo = Recursos.imagen("/imagenes/mazo.png");
        private Image reverso = Recursos.imagen("/imagenes/cartas/reverso.png");
        private Image avatar = Recursos.imagen("/imagenes/iconos/jugador.png");

        public MesaPanel() {
            setPreferredSize(new Dimension(780, 540));
            setBackground(Estilos.VERDE_MESA);
            setBorder(BorderFactory.createLineBorder(Estilos.DORADO, 2));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int ancho = getWidth();
            int alto = getHeight();

            if (tapete != null) {
                g2.drawImage(tapete, 0, 0, ancho, alto, this);
            } else {
                g2.setColor(Estilos.VERDE_MESA);
                g2.fillRect(0, 0, ancho, alto);
                g2.setColor(Estilos.VERDE_OSCURO);
                g2.fillOval(75, 55, ancho - 150, alto - 110);
                g2.setStroke(new BasicStroke(6));
                g2.setColor(Estilos.DORADO);
                g2.drawOval(75, 55, ancho - 150, alto - 110);
            }

            dibujarMazo(g2, ancho / 2 - 65, alto / 2 - 86);
            dibujarJugadores(g2, ancho, alto);
        }

        private void dibujarMazo(Graphics2D g2, int x, int y) {
            if (mazo != null) {
                g2.drawImage(mazo, x, y, 130, 160, this);
                return;
            }
            g2.setColor(new Color(30, 50, 90));
            g2.fillRoundRect(x, y, 82, 118, 12, 12);
        }

        private void dibujarJugadores(Graphics2D g2, int ancho, int alto) {
            ArrayList<Jugador> jugadores = partida.getJugadores();
            int[][] posiciones = obtenerPosiciones(jugadores.size(), ancho, alto);

            for (int i = 0; i < jugadores.size(); i++) {
                Jugador jugador = jugadores.get(i);
                boolean activo = jugador == partida.getJugadorActual() && !partida.isFinalizada();
                boolean mostrarCartas = activo || partida.isFinalizada();
                dibujarZonaJugador(g2, jugador, posiciones[i][0], posiciones[i][1], activo, mostrarCartas);
            }
        }

        private int[][] obtenerPosiciones(int cantidad, int ancho, int alto) {
            if (cantidad == 2) {
                return new int[][]{{ancho / 2 - 155, alto - 170}, {ancho / 2 - 155, 24}};
            }
            if (cantidad == 3) {
                return new int[][]{{ancho / 2 - 155, alto - 170}, {28, alto / 2 - 75}, {ancho / 2 - 155, 24}};
            }
            return new int[][]{{ancho / 2 - 155, alto - 170}, {28, alto / 2 - 75}, {ancho / 2 - 155, 24}, {ancho - 338, alto / 2 - 75}};
        }

        private void dibujarZonaJugador(Graphics2D g2, Jugador jugador, int x, int y, boolean activo, boolean mostrarCartas) {
            g2.setColor(new Color(0, 0, 0, 60));
            g2.fillRoundRect(x + 5, y + 6, 310, 145, 18, 18);

            g2.setColor(activo ? new Color(255, 252, 240, 246) : new Color(220, 224, 214, 225));
            g2.fillRoundRect(x, y, 310, 145, 18, 18);
            g2.setColor(activo ? Estilos.DORADO : new Color(145, 145, 132));
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(x, y, 310, 145, 18, 18);

            if (avatar != null) {
                g2.drawImage(avatar, x + 10, y + 9, 38, 38, this);
            }

            g2.setColor(Estilos.TEXTO);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(jugador.getNombre(), x + 54, y + 24);

            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            if (mostrarCartas) {
                g2.drawString("Puntos " + partida.formatearPuntos(jugador.getPuntuacion()), x + 205, y + 24);
            } else {
                g2.drawString("Cartas ocultas", x + 202, y + 24);
            }

            int cartaX = x + 18;
            for (Carta carta : jugador.getCartas()) {
                dibujarCarta(g2, cartaX, y + 50, carta, mostrarCartas);
                cartaX += 42;
            }
        }

        private void dibujarCarta(Graphics2D g2, int x, int y, Carta carta, boolean bocaArriba) {
            Image imagen = bocaArriba ? Recursos.imagen(Recursos.rutaCarta(carta)) : reverso;
            if (imagen != null) {
                g2.drawImage(imagen, x, y, 62, 90, this);
                return;
            }

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, 62, 86, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, y, 62, 86, 10, 10);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString(bocaArriba ? carta.getNumeroTexto() : "7M", x + 10, y + 48);
        }
    }
}
