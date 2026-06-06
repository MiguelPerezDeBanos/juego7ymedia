package juego7ymedio.vista;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("Siete y medio");
        setMinimumSize(new Dimension(1120, 720));
        setPreferredSize(new Dimension(1180, 780));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void cambiarPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }
}
