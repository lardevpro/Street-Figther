package modelo;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 * Representa un reproductor de música que carga y reproduce archivos de audio en formato WAV.
 * Permite reproducir y detener la música.
 */
public class Musica {
    private Clip clip;

    /**
     * Constructor de la clase Musica que carga un archivo de audio desde la ruta especificada.
     *
     * @param rutaArchivo La ruta del archivo de audio a cargar.
     */
    public Musica(String rutaArchivo) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(rutaArchivo).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.start();
        }
    }
    

    public void detener() {
        if (clip != null && clip.isRunning() || clip != null && !clip.isRunning()) {
            clip.stop();
            clip = null;
        }
    }

}
