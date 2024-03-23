import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Brick extends GameObject {
    private boolean readyToBeDestroyed;

    public boolean isReadyToBeDestroyed() {
        return readyToBeDestroyed;
    }

    public void setReadyToBeDestroyed(boolean readyToBeDestroyed) {
        this.readyToBeDestroyed = readyToBeDestroyed;
    }

    private int health;
    private final Color color;
    private final String type;
    private final int mode;

    public Brick(int x, int y, int width, int height, int mode, int level) {
        super(x, y, width, height);
        this.mode = mode;
        this.type = chooseType();
        int rnd = (int) (Math.random() * 9);
        switch (mode) {
            case 1:
                health = level + rnd - 6;
                break;
            case 2:
                health = level + rnd - 4;
                break;
            case 3:
                health = level + rnd - 2;
                break;
            default:
                health = level;
        }
        health = Math.max(health, 1);
        color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Color getColor() {
        return color;
    }

    public void draw(Graphics g) {
        if (PlayingPanel.disco){
            g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        } else {
            g.setColor(color);
        }
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        if (type.equals("Disco")) {
            ImageIcon imageIcon = new ImageIcon("src/Disco.png");
            int imgWidth = imageIcon.getIconWidth();
            int imgHeight = imageIcon.getIconHeight();
            g.drawImage(imageIcon.getImage(), getX() + (int) Math.round(getWidth() / 2.0 - imgWidth / 2.0), getY() + (int) Math.round(getHeight() / 2.0 - imgHeight / 2.0), null);
        } else if (type.equals("Earthquake")) {
            ImageIcon imageIcon = new ImageIcon("src/Earthquake.png");
            int imgWidth = imageIcon.getIconWidth();
            int imgHeight = imageIcon.getIconHeight();
            g.drawImage(imageIcon.getImage(), getX() + (int) Math.round(getWidth() / 2.0 - imgWidth / 2.0), getY() + (int) Math.round(getHeight() / 2.0 - imgHeight / 2.0), null);
        } else if (type.equals("Bomb")) {
            ImageIcon imageIcon = new ImageIcon("src/Explosion.png");
            int imgWidth = imageIcon.getIconWidth();
            int imgHeight = imageIcon.getIconHeight();
            g.drawImage(imageIcon.getImage(), getX() + (int) Math.round(getWidth() / 2.0 - imgWidth / 2.0), getY() + (int) Math.round(getHeight() / 2.0 - imgHeight / 2.0), null);
        }


        Font font = new Font("Calibri", Font.PLAIN, 24);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = SwingUtilities.computeStringWidth(fm, health + "");
        int centerX = (int) Math.round(getX() + getWidth() / 2.0 - stringWidth / 2.0);
        int centerY = getY() + (int) Math.round(getHeight() / 2.0 + (fm.getHeight() / 4.0));
        g.setColor(Color.WHITE);
        g.drawString(health + "", centerX, centerY);
    }

    public void onCollision(int ballPower) {
        if (health > 0) {
            health -= ballPower;
        }
        if (health <= 0) {
            setReadyToBeDestroyed(true);
        }
    }

    public String chooseType() {
        int emptyProbability;
        int discoProbability;
        int earthquakeProbability;
        int bombProbability;
        switch (mode) {
            case 1:
                emptyProbability = 6;
                discoProbability = 3;
                earthquakeProbability = 1;
                bombProbability = 2;
                break;
            case 2:
                emptyProbability = 5;
                discoProbability = 4;
                earthquakeProbability = 1;
                bombProbability = 3;
                break;
            case 3:
                emptyProbability = 2;
                discoProbability = 7;
                earthquakeProbability = 2;
                bombProbability = 1;
                break;
            default:
                throw new RuntimeException();
        }
        int totalBasket = emptyProbability + discoProbability + earthquakeProbability + bombProbability;
        int randomNumber = (int) (Math.random() * totalBasket);

        String[] choices = new String[totalBasket];
        for (int i = 0; i < emptyProbability; i++) {
            choices[i] = "";
        }
        for (int i = 0; i < discoProbability; i++) {
            choices[i + emptyProbability] = "Disco";
        }
        for (int i = 0; i < earthquakeProbability; i++) {
            choices[i + emptyProbability + discoProbability] = "Earthquake";
        }
        for (int i = 0; i < bombProbability; i++) {
            choices[i + emptyProbability + discoProbability + earthquakeProbability] = "Bomb";
        }
        return choices[randomNumber];
    }

    public String getType() {
        return type;
    }
}
