import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class OptionsFrame extends JFrame {
    public static boolean change;
    public OptionsFrame(Point p){
        super("BrickBreaker+");
        this.setIconImage(new ImageIcon("").getImage());
        this.setLocation(p);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 700));
        this.setLayout(null);
        this.getContentPane().setBackground(Color.black);

        change = false;


        JLabel label = new JLabel("Turn aiming on/off:");
        label.setFont(new Font("Calibri", Font.PLAIN, 22));
        label.setBounds(0, 250, 500, 100);
        label.setForeground(Color.green);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label);
        ToggleSwitch aimingSwitch = new ToggleSwitch(210, 335, 1);
        this.add(aimingSwitch);


        JLabel label2 = new JLabel("Turn music on/off:");
        label2.setFont(new Font("Calibri", Font.PLAIN, 22));
        label2.setBounds(0, 370, 500, 100);
        label2.setForeground(Color.green);
        label2.setHorizontalAlignment(JLabel.CENTER);
        this.add(label2);
        ToggleSwitch musicSwitch = new ToggleSwitch(210, 455, 2);
        this.add(musicSwitch);



        JLabel label3 = new JLabel("Turn saving on/off:");
        label3.setFont(new Font("Calibri", Font.PLAIN, 22));
        label3.setBounds(0, 490, 500, 100);
        label3.setForeground(Color.green);
        label3.setHorizontalAlignment(JLabel.CENTER);
        this.add(label3);
        ToggleSwitch savingSwitch = new ToggleSwitch(210, 575, 3);
        this.add(savingSwitch);


        JButton backButton = new JButton("Save");
        backButton.setFont(new Font("Calibri", Font.PLAIN, 15));
        backButton.setBounds(150, 220, 200, 40);
        backButton.setBackground(Color.darkGray);
        backButton.setOpaque(true);
        backButton.setUI(new BasicButtonUI());
        backButton.setForeground(Color.white);
        backButton.setHorizontalAlignment(JButton.CENTER);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                File file = new File("src/settings.txt");
                try {
                    FileWriter fileWriter = new FileWriter(file, false);
                    fileWriter.append(Main.aiming + "," + Main.music + "," + Main.saving + "\n");
                    fileWriter.close();
                    if (change) {
                        if (!Main.music) {
                            try {
                                Main.pause();
                            } catch (JavaLayerException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                Main.play();
                            } catch (JavaLayerException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                new StartingFrame(getLocation());
            }
        });
        this.add(backButton);




        this.setVisible(true);
    }
}
