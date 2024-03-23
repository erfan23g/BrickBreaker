import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsFrame extends JFrame {
    private int gameMode = 0;
    private Color ballColor;
    public GameSettingsFrame(Point p){
        this.ballColor = Color.white;
        this.setLocation(p);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 700));
        this.setLayout(null);
        JLabel label = new JLabel("Choose your game mode:");
        label.setFont(new Font("Calibri", Font.PLAIN, 22));
        label.setBounds(0, 50, 500, 100);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label);

        JRadioButton easyButton = new JRadioButton("Easy");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton hardButton = new JRadioButton("Hard");
        easyButton.setBounds(0, 110, 500, 50);
        mediumButton.setBounds(0, 135, 500, 50);
        hardButton.setBounds(0, 160, 500, 50);
        easyButton.setHorizontalAlignment(JRadioButton.CENTER);
        mediumButton.setHorizontalAlignment(JRadioButton.CENTER);
        hardButton.setHorizontalAlignment(JRadioButton.CENTER);

        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = 1;
            }
        });
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = 2;
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode = 3;
            }
        });


        this.add(easyButton);
        this.add(mediumButton);
        this.add(hardButton);

        JButton colorButton = new JButton("Choose your ball color");
        colorButton.setFont(new Font("Calibri", Font.PLAIN, 15));
        colorButton.setBounds(150, 220, 200, 100);
        colorButton.setHorizontalAlignment(JButton.CENTER);
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballColor = JColorChooser.showDialog(null, "Pick a color", Color.white);
            }
        });

        this.add(colorButton);


        JTextField textField = new JTextField();
        textField.setBounds(150, 350, 200, 50);
        textField.setText("Name");
        textField.setFont(new Font("Consolas", Font.PLAIN, 35));
        textField.setBackground(Color.black);
        textField.setForeground(Color.green);
        textField.setCaretColor(Color.white);
        this.add(textField);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Calibri", Font.PLAIN, 15));
        startButton.setBounds(150, 420, 200, 100);
        startButton.setHorizontalAlignment(JButton.CENTER);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameMode == 0){
                    JOptionPane.showMessageDialog(null, "You have to choose a game mode", "Error", JOptionPane.PLAIN_MESSAGE);

                } else if (textField.getText().equals("Name") || textField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You have to enter your name", "Error", JOptionPane.PLAIN_MESSAGE);
                } else {
                    dispose();
                    new GameFrame(textField.getText(), ballColor, gameMode, getLocation());
                }
            }
        });
        this.add(startButton);


        this.setVisible(true);
    }
}
