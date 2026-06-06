package juego7ymedio.util;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import juego7ymedio.modelo.Carta;

public class Recursos {
    public static Image imagen(String ruta) {
        try {
            return ImageIO.read(Recursos.class.getResource(ruta));
        } catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    public static ImageIcon icono(String ruta, int ancho, int alto) {
        Image imagen = imagen(ruta);
        if (imagen == null) {
            return new ImageIcon();
        }
        return new ImageIcon(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
    }

    public static String rutaCarta(Carta carta) {
        return "/imagenes/cartas/" + carta.getNumero() + "_" + carta.getPalo().toLowerCase() + ".png";
    }
}
