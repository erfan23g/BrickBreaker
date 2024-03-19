import javax.swing.*;
import java.awt.*;

public class StartingFrame extends JFrame {

    public StartingFrame() {
        // Set the title of the frame
        super("Simple Frame");

        // Create a panel for buttons with BoxLayout for vertical alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Create buttons
        JButton newGameButton = new JButton("New Game");
        JButton historyButton = new JButton("History");
        JButton settingsButton = new JButton("Settings");

        // Determine the maximum width of the buttons based on the widest button
        int maxWidth = Math.max(newGameButton.getPreferredSize().width,
                Math.max(historyButton.getPreferredSize().width,
                        settingsButton.getPreferredSize().width));

        // Set the preferred and maximum size for all buttons
        Dimension buttonSize = new Dimension(maxWidth, newGameButton.getPreferredSize().height);
        newGameButton.setPreferredSize(buttonSize);
        historyButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);

        // Add buttons to the panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(settingsButton);

        // Add the panel to the frame
        this.add(buttonPanel);

        // Set the default operation to exit the application when the frame is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        this.setSize(300, 200);

        // Make the frame visible
        this.setVisible(true);
    }

}
