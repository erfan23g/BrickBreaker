import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RematchFrame extends JFrame {

    public RematchFrame(String playerName, Color ballColor, int mode, Point p) {
        this.setLocation(p);
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
        JButton rematchButton = new JButton("Rematch with the same setting");
        JButton back1Button = new JButton("Back to game settings menu");
        JButton back2Button = new JButton("Back to starting menu");

        // Change button colors
        rematchButton.setBackground(Color.ORANGE);
        back1Button.setBackground(Color.ORANGE);
        back2Button.setBackground(Color.ORANGE);



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

        // Display the this
        this.setLocationRelativeTo(null); // Center the frame on screen
        this.setVisible(true);
    }

}
