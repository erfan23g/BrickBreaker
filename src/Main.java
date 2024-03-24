
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;


import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Timer;

public class Main {
    public static boolean aiming, music, saving;
    public static AdvancedPlayer player;
    private static FileInputStream fileInputStream;
    private static int pausePosition = 0;

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
        if (music) {
            try {
                play();
            } catch (JavaLayerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void play() throws JavaLayerException {
        try {
            // Reset and prepare the stream
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            fileInputStream = new FileInputStream("src/song2.mp3");
            player = new AdvancedPlayer(fileInputStream);

            // Skip to the pause position if resuming
            fileInputStream.getChannel().position(pausePosition);

            // Play the MP3 file
            new Thread(() -> {
                try {
                    player.play();
                    player.play(pausePosition, Integer.MAX_VALUE);
                } catch (JavaLayerException e) {
//                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void pause() throws JavaLayerException {
        if (player != null) {
            try {
                // Remember the current position and close the player
                pausePosition += fileInputStream.getChannel().position();
                player.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
