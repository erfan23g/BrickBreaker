import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private final String playerName;
    private final Color ballColor;
    private final int mode;
    public GameFrame(String playerName, Color ballColor, int mode, Point p){
        this.playerName = playerName;
        this.ballColor = ballColor;
        this.mode = mode;
        this.setLocation(p);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 700));
        this.setLayout(new BorderLayout());
        // TODO change info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(500, 100));
        infoPanel.setBackground(Color.CYAN);
        this.add(infoPanel, BorderLayout.NORTH);
        // -------------------
        this.add(new PlayingPanel(ballColor, mode), BorderLayout.SOUTH);


        this.setVisible(true);
    }
}
