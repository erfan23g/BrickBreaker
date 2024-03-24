
import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    static Clip clip;
    public static boolean aiming, music, saving;
    public static void main(String[] args) {
        File file = new File("src/settings.txt");
        try {
            Scanner fr = new Scanner(file);
            if (fr.hasNextLine()){
                String line = fr.nextLine();
                String[] line2 = line.split(",");
                aiming = line2[0].equals("true");
                music = line2[1].equals("true");
                saving = line2[2].equals("true");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        new StartingFrame();
//        new OptionsFrame(new Point(300, 200));
//        new HistoryFrame(new Point(200, 300));


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
