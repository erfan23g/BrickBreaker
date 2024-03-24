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

public class StartingFrame extends JFrame {

    public StartingFrame() {
        super("BrickBreaker+");
        this.setIconImage(new ImageIcon("").getImage());
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
        JButton newGameButton = new JButton("New Game");
        JButton historyButton = new JButton("History");
        JButton optionsButton = new JButton("Options");

        // Change button colors
        newGameButton.setBackground(Color.ORANGE);
        historyButton.setBackground(Color.ORANGE);
        optionsButton.setBackground(Color.ORANGE);

        newGameButton.setBackground(Color.darkGray);
        newGameButton.setOpaque(true);
        newGameButton.setUI(new BasicButtonUI());
        newGameButton.setForeground(Color.white);

        historyButton.setBackground(Color.darkGray);
        historyButton.setOpaque(true);
        historyButton.setUI(new BasicButtonUI());
        historyButton.setForeground(Color.white);

        optionsButton.setBackground(Color.darkGray);
        optionsButton.setOpaque(true);
        optionsButton.setUI(new BasicButtonUI());
        optionsButton.setForeground(Color.white);




        // Set a maximum size for the buttons to enforce a consistent size
        newGameButton.setMaximumSize(new Dimension(250, 250));
        historyButton.setMaximumSize(new Dimension(250, 250));
        optionsButton.setMaximumSize(new Dimension(250, 250));

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameSettingsFrame(getLocation());
            }
        });
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OptionsFrame(getLocation());
            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HistoryFrame(getLocation());
            }
        });
        // Align the buttons in the center horizontally
        newGameButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
        historyButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
        optionsButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);


        // Add the buttons to the frame with space between them
        this.add(newGameButton);
        this.add(Box.createVerticalStrut(10)); // Space between buttons
        this.add(historyButton);
        this.add(Box.createVerticalStrut(10)); // Space between buttons
        this.add(optionsButton);

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
    public StartingFrame(Point p){
        this();
        this.setLocation(p);
    }

}
