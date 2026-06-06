package juego7ymedio.util;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public class Sonidos {
    private static Clip musica;
    private static boolean musicaActiva = true;

    public static void reproducir(String nombre) {
        try {
            InputStream entrada = Sonidos.class.getResourceAsStream("/sonidos/" + nombre);
            if (entrada == null) {
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(entrada));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            bajarVolumen(clip, -5f);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public static void iniciarMusica() {
        if (!musicaActiva || musica != null) {
            return;
        }

        try {
            InputStream entrada = Sonidos.class.getResourceAsStream("/sonidos/musica.wav");
            if (entrada == null) {
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(entrada));
            musica = AudioSystem.getClip();
            musica.open(audio);
            bajarVolumen(musica, -8f);
            musica.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            musica = null;
        }
    }

    public static boolean cambiarMusica() {
        musicaActiva = !musicaActiva;

        if (musicaActiva) {
            iniciarMusica();
        } else if (musica != null) {
            musica.stop();
            musica.close();
            musica = null;
        }

        return musicaActiva;
    }

    public static boolean isMusicaActiva() {
        return musicaActiva;
    }

    private static void bajarVolumen(Clip clip, float decibelios) {
        try {
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(decibelios);
        } catch (Exception e) {
            return;
        }
    }
}
