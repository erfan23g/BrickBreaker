import javax.swing.*;
import java.awt.*;

public class NormalItem extends GameObject{
    private final int diameter = 20;
    private final String type;
    private boolean readyToBeDestroyed;
    private int mode;

    public NormalItem(int x, int y, int width, int height, int mode) {
        super(x, y, width, height);
        this.mode = mode;
        this.type = this.chooseType();
        this.readyToBeDestroyed = false;
    }

    public String chooseType() {
        int ballProbability;
        int speedProbability;
        int powerProbability;
        int vertigoProbability;
        int reverseProbability;
        int heartProbability;

        switch (mode){
            case 1:
                ballProbability = 5;
                speedProbability = 3;
                powerProbability = 3;
                vertigoProbability = 2;
                reverseProbability = 3;
                heartProbability = 4;
                break;
            case 2:
                ballProbability = 5;
                speedProbability = 4;
                powerProbability = 2;
                vertigoProbability = 4;
                reverseProbability = 2;
                heartProbability = 3;
                break;
            case 3:
                ballProbability = 5;
                speedProbability = 4;
                powerProbability = 2;
                vertigoProbability = 5;
                reverseProbability = 2;
                heartProbability = 2;
                break;
            default:
                ballProbability = 1;
                speedProbability = 1;
                powerProbability = 1;
                vertigoProbability = 1;
                reverseProbability = 1;
                heartProbability = 1;
                break;
        }

        int totalBasket = ballProbability + speedProbability + powerProbability + vertigoProbability + reverseProbability + heartProbability;
        int randomNumber = (int) (Math.random() * totalBasket);

        String[] choices = new String[totalBasket];
        for (int i = 0; i < ballProbability; i++) {
            choices[i] = "Ball";
        }
        for (int i = 0; i < speedProbability; i++) {
            choices[i + ballProbability] = PlayingPanel.speed ? "" : "Speed";
        }
        for (int i = 0; i < powerProbability; i++) {
            choices[i + ballProbability + speedProbability] = PlayingPanel.power ? "" : "Power";
        }
        for (int i = 0; i < vertigoProbability; i++) {
            choices[i + ballProbability + speedProbability + powerProbability] = Main.aiming ? "Vertigo" : "";
        }
        for (int i = 0; i < reverseProbability; i++) {
            choices[i + ballProbability + speedProbability + powerProbability + vertigoProbability] = "Reverse";
        }
        for (int i = 0; i < heartProbability; i++) {
            choices[i + ballProbability + speedProbability + powerProbability + vertigoProbability + reverseProbability] = PlayingPanel.heart2 ? "" : "Heart";
        }
        if (choices[randomNumber].equals("Heart")){
            PlayingPanel.heart2 = true;
        }
        return choices[randomNumber];
    }


    public boolean isReadyToBeDestroyed() {
        return readyToBeDestroyed;
    }

    public void setReadyToBeDestroyed(boolean readyToBeDestroyed) {
        this.readyToBeDestroyed = readyToBeDestroyed;
    }

    @Override
    public void draw(Graphics g) {
        if (!isReadyToBeDestroyed() && !type.equals("")) {
            int startX = getX() + (int) Math.round(getWidth()/2.0 - diameter/2.0);
            int startY = getY() +(int) Math.round(getHeight()/2.0 - diameter/2.0);
            if (PlayingPanel.disco){
                g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                g.fillOval(startX, startY, diameter, diameter);
            } else {
                switch (type){
                    case "Ball":
                        g.drawImage(new ImageIcon("src/Ball.png").getImage(), startX, startY, null);
                        break;
                    case "Speed":
//                        g.setColor(Color.red);
                        g.drawImage(new ImageIcon("src/Speed.png").getImage(), startX, startY, null);
                        break;
                    case "Power":
//                        g.setColor(Color.blue);
                        g.drawImage(new ImageIcon("src/Power.png").getImage(), startX, startY, null);
                        break;
                    case "Vertigo":
//                        g.setColor(Color.yellow);
                        g.drawImage(new ImageIcon("src/Vertigo.png").getImage(), startX, startY, null);
                        break;
                    case "Reverse":
//                        g.setColor(Color.pink);
                        g.drawImage(new ImageIcon("src/Reverse.png").getImage(), startX, startY, null);
                        break;
                    case "Heart":
//                        g.setColor(Color.green);
                        g.drawImage(new ImageIcon("src/Heart.png").getImage(), startX, startY, null);
                        break;
                    default:
                        g.setColor(Color.black);
                        break;
                }
            }
        }
    }

    @Override
    public void onCollision(int ballPower) {
        setReadyToBeDestroyed(true);

    }

    public String getType() {
        return type;
    }


    public int getDiameter() {
        return diameter;
    }
}
