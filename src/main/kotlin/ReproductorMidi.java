//ReproductorMidi.java
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

class ReproductorMidi {

    Sequencer sequencer;

    public ReproductorMidi(String ficheroMidi) throws Exception {
        //simplificamos el throws con Exception para hacer código limpio (aunque de peor calidad)
        // lo más fácil pillar el secuenciador por defecto.
        sequencer = MidiSystem.getSequencer();
        //antes de usar el secuenciaador hay que abrirlo.
        sequencer.open();
        // la secuencia midi es un InputStream. Puede ser un fichero, un stream de internet, ...en este caso es un fichero
        InputStream is = new BufferedInputStream(new FileInputStream(new File(ficheroMidi)));
        sequencer.setSequence(is);
        //cuando nos interesa arrancamos la reproducción.
        sequencer.start();
        //repetimos indefinidadmente la cancion indicando que se repita LOPP_CONTINUOSLY
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }

    public void parar(){sequencer.stop();}

    void cerrar() {
        sequencer.close();
    }
}
