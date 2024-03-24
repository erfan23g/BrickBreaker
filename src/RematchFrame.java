import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class RematchFrame extends JFrame {

    public RematchFrame(String playerName, Color ballColor, int mode, Point p) {
        super("BrickBreaker+");
        this.setIconImage(new ImageIcon("").getImage());
        this.setLocation(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(500, 700));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
//        this.setLayout(null);
        this.getContentPane().setBackground(Color.black);

        this.add(Box.createVerticalGlue());
        this.add(Box.createRigidArea(new Dimension(0, 100))); // Adjust this to move the buttons down


        // Create some glue to add space at the top and between buttons
        this.add(Box.createVerticalGlue());

        // Create the buttons
        JButton rematchButton = new JButton("Rematch with the same setting");
        JButton back1Button = new JButton("Back to game settings menu");
        JButton back2Button = new JButton("Back to starting menu");

        // Change button colors
        rematchButton.setBackground(Color.ORANGE);
        back1Button.setBackground(Color.ORANGE);
        back2Button.setBackground(Color.ORANGE);

        rematchButton.setBackground(Color.darkGray);
        rematchButton.setOpaque(true);
        rematchButton.setUI(new BasicButtonUI());
        rematchButton.setForeground(Color.white);

        back1Button.setBackground(Color.darkGray);
        back1Button.setOpaque(true);
        back1Button.setUI(new BasicButtonUI());
        back1Button.setForeground(Color.white);

        back2Button.setBackground(Color.darkGray);
        back2Button.setOpaque(true);
        back2Button.setUI(new BasicButtonUI());
        back2Button.setForeground(Color.white);




        // Set a maximum size for the buttons to enforce a consistent size
        rematchButton.setMaximumSize(new Dimension(250, 250));
        back1Button.setMaximumSize(new Dimension(250, 250));
        back2Button.setMaximumSize(new Dimension(250, 250));
        rematchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameFrame(playerName, ballColor, mode, getLocation());
            }
        });
        back1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameSettingsFrame(getLocation());
            }
        });
        back2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartingFrame(getLocation());
            }
        });
        // Align the buttons in the center horizontally
        rematchButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
        back1Button.setAlignmentX(JFrame.CENTER_ALIGNMENT);
        back2Button.setAlignmentX(JFrame.CENTER_ALIGNMENT);


        // Add the buttons to the frame with space between them
        this.add(rematchButton);
        this.add(Box.createVerticalStrut(10)); // Space between buttons
        this.add(back1Button);
        this.add(Box.createVerticalStrut(10)); // Space between buttons
        this.add(back2Button);

        // Add some glue at the bottom as well to center the buttons vertically
        this.add(Box.createVerticalGlue());

        // Pack the frame to respect preferred sizes
        this.pack();

        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File("src/games.txt");
        try {
            Scanner fr = new Scanner(file);
            while (fr.hasNextLine()) {
                String line = fr.nextLine();
                String[] line2 = line.split(",");
                scores.add(Integer.parseInt(line2[1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scores.sort(Comparator.naturalOrder());
        this.setLayout(null);
        JLabel label = new JLabel("<html><div style='text-align: center; line-height: 150%;'>High score: " + scores.getLast() + "<br>");
        label.setFont(new Font("Calibri", Font.PLAIN, 32));
        label.setForeground(Color.GREEN);
        label.setUI(new BasicLabelUI());
        label.setBounds(150, 50, 200, 100);
        this.add(label);

        JLabel label1 = new JLabel(new ImageIcon("src/Logo.png"));
        label1.setBounds(150, 150, 200, 200);
        this.add(label1);

        // Display the this
        this.setLocationRelativeTo(null); // Center the frame on screen
        this.setVisible(true);
    }

}
