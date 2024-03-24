import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingFrame extends JFrame {

    public StartingFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(500, 700));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
//        this.setLayout(null);

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

        // Display the this
        this.setLocationRelativeTo(null); // Center the frame on screen
        this.setVisible(true);
    }
    public StartingFrame(Point p){
        this();
        this.setLocation(p);
    }

}
