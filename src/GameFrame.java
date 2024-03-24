import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private final String playerName;
    private final Color ballColor;
    private int time;
    private final int mode;


    public GameFrame(String playerName, Color ballColor, int mode, Point p){
        this.playerName = playerName;
        this.ballColor = ballColor;
        this.mode = mode;
        this.time = 0;
        this.setLocation(p);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 700));
        this.setLayout(new BorderLayout());
        PlayingPanel playingPanel = new PlayingPanel(ballColor, mode, playerName);
        this.add(playingPanel, BorderLayout.SOUTH);
        // TODO change info panel
        InfoPanel infoPanel = new InfoPanel(playerName);

        this.add(infoPanel, BorderLayout.NORTH);
        // -------------------


        this.setVisible(true);
    }
}
