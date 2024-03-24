import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsFrame extends JFrame {
    private int gameMode = 0;
    private Color ballColor;
    public GameSettingsFrame(Point p){
        super("BrickBreaker+");
        this.setIconImage(new ImageIcon("").getImage());
        this.ballColor = Color.white;
        this.setLocation(p);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 700));
        this.setLayout(null);
        this.getContentPane().setBackground(Color.black);
        JLabel label = new JLabel("Choose your game mode:");
        label.setFont(new Font("Calibri", Font.PLAIN, 22));
        label.setBounds(0, 50, 500, 100);
        label.setForeground(Color.green);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label);

        JRadioButton easyButton = new JRadioButton("Easy");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton hardButton = new JRadioButton("Hard");
        easyButton.setBounds(0, 110, 500, 50);
        mediumButton.setBounds(0, 135, 500, 50);
        hardButton.setBounds(0, 160, 500, 50);
        easyButton.setHorizontalAlignment(JRadioButton.CENTER);
        easyButton.setForeground(Color.green);
        mediumButton.setHorizontalAlignment(JRadioButton.CENTER);
        mediumButton.setForeground(Color.green);
        hardButton.setHorizontalAlignment(JRadioButton.CENTER);
        hardButton.setForeground(Color.green);

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
        colorButton.setBounds(150, 220, 200, 40);
        colorButton.setBackground(Color.darkGray);
        colorButton.setOpaque(true);
        colorButton.setUI(new BasicButtonUI());
        colorButton.setForeground(Color.white);
        colorButton.setHorizontalAlignment(JButton.CENTER);
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballColor = JColorChooser.showDialog(null, "Pick a color", Color.white);
            }
        });

        this.add(colorButton);
        JLabel label2 = new JLabel("Enter your name here:");
        label2.setFont(new Font("Calibri", Font.PLAIN, 22));
        label2.setBounds(0, 270, 500, 100);
        label2.setForeground(Color.green);
        label2.setHorizontalAlignment(JLabel.CENTER);
        this.add(label2);


        JTextField textField = new JTextField();
        textField.setBounds(150, 350, 200, 50);
        textField.setText("Name");
        textField.setFont(new Font("Consolas", Font.PLAIN, 35));
        textField.setBackground(Color.darkGray);
        textField.setForeground(Color.green);
        textField.setCaretColor(Color.white);
        this.add(textField);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Calibri", Font.PLAIN, 15));
        startButton.setBounds(150, 420, 200, 40);
        startButton.setBackground(Color.darkGray);
        startButton.setOpaque(true);
        startButton.setUI(new BasicButtonUI());
        startButton.setForeground(Color.white);
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
