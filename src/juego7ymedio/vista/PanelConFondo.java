package juego7ymedio.vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import juego7ymedio.util.Recursos;

public class PanelConFondo extends JPanel {
    private Image fondo;

    public PanelConFondo(String ruta) {
        fondo = Recursos.imagen(ruta);
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
