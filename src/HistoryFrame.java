import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HistoryFrame extends JFrame {
    private JPanel mainPanel;
    private int count = 1;

    public HistoryFrame(Point p) {
        super("BrickBreaker+");
        this.setIconImage(new ImageIcon("").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(500, 700));
        this.setLocation(p);
        // Set the background color of the frame
        this.getContentPane().setBackground(Color.BLACK);

        // Create a button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartingFrame(getLocation());
            }
        });
        // Add the button to the top of the frame
        this.getContentPane().add(backButton, BorderLayout.NORTH);

        // Create a panel with a layout
        mainPanel = new JPanel();
        // Adjust spacing between panels
        mainPanel.setLayout(new GridLayout(0, 1, 0, 5)); // GridLayout with 10px vertical gap
        this.getContentPane().setBackground(Color.BLACK);

        File file = new File("src/games.txt");
        try {
            Scanner fr = new Scanner(file);
            while (fr.hasNextLine()) {
                String line = fr.nextLine();
                String[] line2 = line.split(",");
                addRecord(line2[0], line2[1], line2[2]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create a scroll pane and add the main panel to it
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        // Add the scroll pane to the frame
        this.getContentPane().add(scrollPane);

        this.setVisible(true);
    }

    public void addRecord(String playerName, String score, String date) {
        JPanel panel = new JPanel();
        // Set panel background color to black
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel("<html><div style='text-align: center; line-height: 150%;'>Game " + count + "<br>"
                + "Name: " + playerName + "<br>"
                + "Score: " + score + "<br>"
                + "Date: " + date + "</div></html>");
        label.setFont(new Font("Calibri", Font.PLAIN, 32));
        label.setForeground(Color.GREEN);
        // Set label background color to black
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setUI(new BasicLabelUI());
        panel.add(label);

        // Add more components to the panel if needed
        mainPanel.add(panel);
        count++;
    }

}
