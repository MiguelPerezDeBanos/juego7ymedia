package juego7ymedio.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import juego7ymedio.controlador.ControladorJuego;
import juego7ymedio.modelo.JugadorRanking;
import juego7ymedio.util.Recursos;

public class RankingPanel extends PanelConFondo {
    private ControladorJuego controlador;
    private DefaultTableModel modelo;
    private JLabel estado;

    public RankingPanel(ControladorJuego controlador) {
        super("/imagenes/fondo_panel.png");
        this.controlador = controlador;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        add(Estilos.barraTitulo("Ranking de victorias"), BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"Posicion", "ID", "Apodo", "Victorias"}, 0) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 17));
        tabla.setRowHeight(42);
        tabla.setBackground(Estilos.PANEL);
        tabla.setForeground(Estilos.TEXTO);
        tabla.setSelectionBackground(Estilos.DORADO);
        tabla.setGridColor(new Color(210, 200, 180));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
        tabla.getTableHeader().setBackground(Estilos.PANEL_OSCURO);
        tabla.getTableHeader().setForeground(Estilos.CREMA);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(Estilos.BORDE_DORADO);

        JLabel trofeo = new JLabel(Recursos.icono("/imagenes/iconos/ranking.png", 230, 230));
        trofeo.setHorizontalAlignment(JLabel.CENTER);
        trofeo.setPreferredSize(new Dimension(260, 260));

        JPanel centro = new JPanel(new BorderLayout(18, 18));
        centro.setOpaque(false);
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(trofeo, BorderLayout.EAST);
        add(centro, BorderLayout.CENTER);

        estado = new JLabel("", SwingConstants.CENTER);
        estado.setOpaque(true);
        estado.setBackground(new Color(18, 35, 26, 220));
        estado.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));
        estado.setForeground(Estilos.CREMA);
        estado.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton actualizar = Estilos.botonOscuroIcono("Actualizar", "/imagenes/iconos/actualizar.png");
        JButton atras = Estilos.botonOscuroIcono("Atras", "/imagenes/iconos/atras.png");

        actualizar.addActionListener(e -> cargarRanking());
        atras.addActionListener(e -> controlador.mostrarMenu());

        JPanel botones = new JPanel(new GridLayout(1, 2, 15, 0));
        botones.setOpaque(false);
        botones.add(actualizar);
        botones.add(atras);

        JPanel sur = new JPanel(new BorderLayout(10, 10));
        sur.setOpaque(false);
        sur.add(estado, BorderLayout.NORTH);
        sur.add(botones, BorderLayout.SOUTH);
        add(sur, BorderLayout.SOUTH);

        cargarRanking();
    }

    private void cargarRanking() {
        modelo.setRowCount(0);
        ArrayList<JugadorRanking> ranking = controlador.getJugadorDAO().obtenerRanking();

        for (JugadorRanking jugador : ranking) {
            modelo.addRow(new Object[]{
                    jugador.getPosicion(),
                    jugador.getIdJugador(),
                    jugador.getApodo(),
                    jugador.getVictorias()
            });
        }

        if (ranking.isEmpty()) {
            String error = controlador.getJugadorDAO().getUltimoError();
            if (error == null || error.isEmpty()) {
                estado.setText("Todavia no hay victorias registradas");
                estado.setForeground(Estilos.CREMA);
            } else {
                estado.setText("No se pudo cargar el ranking  Revisa MySQL");
                estado.setForeground(new Color(255, 190, 160));
            }
        } else {
            estado.setText("Ranking cargado desde MySQL");
            estado.setForeground(Estilos.CREMA);
        }
    }
}
