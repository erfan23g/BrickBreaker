
import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;

public class Main {
    static Clip clip;
    public static boolean music, aiming, saving;
    public static void main(String[] args) {
        music = true;
        new StartingFrame();
//        new OptionsFrame(new Point(300, 200));



//        while (music){
//            try {
//                File musicFile = new File("src/output.wav"); // Change this to your music file path
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
//                AudioFormat format = audioInputStream.getFormat();
//                DataLine.Info info = new DataLine.Info(Clip.class, format);
//                clip = (Clip) AudioSystem.getLine(info);
//                clip.open(audioInputStream);
//                clip.start();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }
}
