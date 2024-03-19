import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Create the frame
        JFrame frame = new JFrame("Brick Breaker+");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 700));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.add(Box.createVerticalGlue());
        frame.add(Box.createRigidArea(new Dimension(0, 100))); // Adjust this to move the buttons down


        // Create some glue to add space at the top and between buttons
        frame.add(Box.createVerticalGlue());

        // Create the buttons
        JButton newGameButton = new JButton("New Game");
        JButton historyButton = new JButton("History");
        JButton settingsButton = new JButton("Settings");

        // Change button colors
        newGameButton.setBackground(Color.ORANGE);
        historyButton.setBackground(Color.ORANGE);
        settingsButton.setBackground(Color.ORANGE);



        // Set a maximum size for the buttons to enforce a consistent size
        newGameButton.setMaximumSize(new Dimension(250, 250));
        historyButton.setMaximumSize(new Dimension(250, 250));
        settingsButton.setMaximumSize(new Dimension(250, 250));

        // Align the buttons in the center horizontally
        newGameButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
        historyButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(JFrame.CENTER_ALIGNMENT);


        // Add the buttons to the frame with space between them
        frame.add(newGameButton);
        frame.add(Box.createVerticalStrut(10)); // Space between buttons
        frame.add(historyButton);
        frame.add(Box.createVerticalStrut(10)); // Space between buttons
        frame.add(settingsButton);

        // Add some glue at the bottom as well to center the buttons vertically
        frame.add(Box.createVerticalGlue());

        // Pack the frame to respect preferred sizes
        frame.pack();

        // Display the frame
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }
}
