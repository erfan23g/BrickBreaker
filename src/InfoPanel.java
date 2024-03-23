import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {
    private final String playerName;
    private Timer timer;
    private int time, score;
    private boolean paused;
    JButton pauseButton;

    public InfoPanel(String playerName) {
        this.playerName = playerName;
        this.setPreferredSize(new Dimension(500, 72));
        this.setBackground(Color.darkGray);
        this.setLayout(new GridBagLayout());
        this.time = 0;
        this.score = 0;
        this.paused = false;
        this.pauseButton = new JButton();
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!paused) {
                    PlayingPanel.timer.stop();
                    PlayingPanel.timer2.stop();
                    timer.stop();
                    paused = true;
                    PlayingPanel.paused = true;
                    pauseButton.setIcon(new ImageIcon("src/Play.png"));
                } else {
                    PlayingPanel.timer.restart();
                    PlayingPanel.timer2.restart();
                    timer.restart();
                    paused = false;
                    PlayingPanel.paused = false;
                    pauseButton.setIcon(new ImageIcon("src/Pause.png"));
                }
            }
        });
        pauseButton.setBackground(Color.darkGray);
        pauseButton.setOpaque(true);
        pauseButton.setUI(new BasicButtonUI());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // The column of the component, starting at 0
        gbc.gridy = 0; // The row of the component, starting at 0
        gbc.gridwidth = 1; // The number of columns the component occupies
        gbc.gridheight = 1; // The number of rows the component occupies
        gbc.weightx = 1.0; // Give the column a weight to allow horizontal stretching
        gbc.weighty = 1.0; // Give the row a weight to allow vertical stretching
        gbc.fill = GridBagConstraints.NONE; // How the component should be stretched
        gbc.anchor = GridBagConstraints.CENTER; // Position the component in the center of its cell
        gbc.insets = new Insets(35, 0, 0, 0);
        pauseButton.setVisible(true);
        pauseButton.setIcon(new ImageIcon("src/Pause.png"));
        this.add(pauseButton, gbc);
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

    public void paintComponent(Graphics g) {
        g.setFont(new Font("Calibri", Font.BOLD, 18));
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = SwingUtilities.computeStringWidth(fm, playerName);
        int x = (int) Math.round(this.getWidth() / 2.0 - stringWidth / 2.0);
        int y = (int) Math.round(2 * this.getHeight() / 5.0 + (fm.getHeight() / 8.0));
        g.setColor(Color.white);
        g.drawString(playerName, x, y);

        stringWidth = SwingUtilities.computeStringWidth(fm, "score: " + score);
        x = (int) Math.round(this.getWidth() / 5.0 - stringWidth / 2.0);
        y = (int) Math.round(4 * this.getHeight() / 5.0 - (fm.getHeight() / 2.0));
        g.setColor(Color.white);
        g.drawString("score: " + score, x, y);

        stringWidth = SwingUtilities.computeStringWidth(fm, timeFormat());
        x = (int) Math.round(4 * this.getWidth() / 5.0 - stringWidth / 2.0);
        y = (int) Math.round(4 * this.getHeight() / 5.0 - (fm.getHeight() / 2.0));
        g.setColor(Color.white);
        g.drawString(timeFormat(), x, y);

        ImageIcon imageIcon = new ImageIcon("src/Heart.png");
        x = (int) Math.round(this.getWidth() / 5.0 - imageIcon.getIconWidth() / 2.0);
        y = (int) Math.round(this.getHeight() / 4.0 - imageIcon.getIconHeight() / 2.0);
        g.drawImage(imageIcon.getImage(), x, y, null);
        if (PlayingPanel.heart) {
            x += imageIcon.getIconWidth() + 5;
            g.drawImage(imageIcon.getImage(), x, y, null);
        }

//        pauseButton.paint(g);
        g.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight());
    }

    public String timeFormat() {
        long hours = time / 3600000;
        long minutes = (time % 3600000) / 60000;
        long seconds = ((time % 3600000) % 60000) / 1000;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
