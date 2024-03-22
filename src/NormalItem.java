import java.awt.*;

public class NormalItem extends GameObject{
    private final int diameter = 20;
    private final String type;
    private boolean readyToBeDestroyed;

    public NormalItem(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.type = this.chooseType();
        this.readyToBeDestroyed = false;
    }

    public String chooseType() {
        int ballProbability = 5;
        int speedProbability = PlayingPanel.speed ? 0 : 1;
        int powerProbability = PlayingPanel.power ? 0 : 1;
        int vertigoProbability = 0;
        int reverseProbability = 0;
        int heartProbability = 0;
        int totalBasket = ballProbability + speedProbability + powerProbability + vertigoProbability + reverseProbability + heartProbability;
        int randomNumber = (int) (Math.random() * totalBasket);

        String[] choices = new String[totalBasket];
        for (int i = 0; i < ballProbability; i++) {
            choices[i] = "Ball";
        }
        for (int i = 0; i < speedProbability; i++) {
            choices[i + ballProbability] = "Speed";
        }
        for (int i = 0; i < powerProbability; i++) {
            choices[i + ballProbability + speedProbability] = "Power";
        }
        for (int i = 0; i < vertigoProbability; i++) {
            choices[i + ballProbability + speedProbability + powerProbability] = "Vertigo";
        }
        for (int i = 0; i < reverseProbability; i++) {
            choices[i + ballProbability + speedProbability + powerProbability + vertigoProbability] = "Reverse";
        }
        for (int i = 0; i < heartProbability; i++) {
            choices[i + ballProbability + speedProbability + powerProbability + vertigoProbability + heartProbability] = "Heart";
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
        if (!isReadyToBeDestroyed()) {
            switch (type){
                case "Ball":
                    g.setColor(Color.black);
                    break;
                case "Speed":
                    g.setColor(Color.red);
                    break;
                case "Power":
                    g.setColor(Color.blue);
                    break;
            }
            int startX = getX() + (int) Math.round(getWidth()/2.0 - diameter/2.0);
            int startY = getY() +(int) Math.round(getHeight()/2.0 - diameter/2.0);
            g.fillOval(startX, startY, diameter, diameter);
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
