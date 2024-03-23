import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {
    private final String playerName;
    private Timer timer;
    private int time, score;
    public InfoPanel(String playerName){
        this.playerName = playerName;
        this.setPreferredSize(new Dimension(500, 72));
        this.setBackground(Color.lightGray);
        this.time = 0;
        this.score = 0;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time += 100;
                score = Math.max(0, PlayingPanel.score - time / 5000);
                repaint();
            }
        });
        timer.start();
    }
    public void paint(Graphics g){
        g.setFont(new Font("Calibri", Font.BOLD, 18));
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = SwingUtilities.computeStringWidth(fm, playerName);
        int x = (int) Math.round(this.getWidth() / 2.0 - stringWidth / 2.0);
        int y = (int) Math.round(this.getHeight() / 4.0 + (fm.getHeight() / 8.0));
        g.setColor(Color.black);
        g.drawString(playerName, x, y);

        stringWidth = SwingUtilities.computeStringWidth(fm, "score: " + score);
        x = (int) Math.round(this.getWidth() / 5.0 - stringWidth / 2.0);
        y = (int) Math.round(3 * this.getHeight() / 4.0 - (fm.getHeight() / 2.0));
        g.setColor(Color.black);
        g.drawString("score: " + score, x, y);

        stringWidth = SwingUtilities.computeStringWidth(fm, timeFormat());
        x = (int) Math.round(4 * this.getWidth() / 5.0 - stringWidth / 2.0);
        y = (int) Math.round(3 * this.getHeight() / 4.0 - (fm.getHeight() / 2.0));
        g.setColor(Color.black);
        g.drawString(timeFormat(), x, y);
    }
    public String timeFormat(){
        long hours = time / 3600000;
        long minutes = (time % 3600000) / 60000;
        long seconds = ((time % 3600000) % 60000) / 1000;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
