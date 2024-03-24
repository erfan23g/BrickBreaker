import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsFrame extends JFrame {
    public OptionsFrame(Point p){
        this.setLocation(p);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 700));
        this.setLayout(null);



        JLabel label = new JLabel("Turn aiming on/off:");
        label.setFont(new Font("Calibri", Font.PLAIN, 22));
        label.setBounds(0, 250, 500, 100);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label);
        ToggleSwitch aimingSwitch = new ToggleSwitch(210, 335, 1);
        this.add(aimingSwitch);


        JLabel label2 = new JLabel("Turn music on/off:");
        label2.setFont(new Font("Calibri", Font.PLAIN, 22));
        label2.setBounds(0, 370, 500, 100);
        label2.setHorizontalAlignment(JLabel.CENTER);
        this.add(label2);
        ToggleSwitch musicSwitch = new ToggleSwitch(210, 455, 2);
        this.add(musicSwitch);



        JLabel label3 = new JLabel("Turn music on/off:");
        label3.setFont(new Font("Calibri", Font.PLAIN, 22));
        label3.setBounds(0, 490, 500, 100);
        label3.setHorizontalAlignment(JLabel.CENTER);
        this.add(label3);
        ToggleSwitch savingSwitch = new ToggleSwitch(210, 575, 3);
        this.add(savingSwitch);


        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Calibri", Font.PLAIN, 15));
        backButton.setBounds(150, 120, 200, 100);
        backButton.setHorizontalAlignment(JButton.CENTER);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartingFrame(getLocation());
            }
        });
        this.add(backButton);




        this.setVisible(true);
    }
}
