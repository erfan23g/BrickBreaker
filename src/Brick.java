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
    private double x2, y2, width2, height2;
    private double dWidth2, dHeight2;

    public int getScore() {
        return score;
    }

    private final int score;

    public Brick(int x, int y, int width, int height, int mode, int level) {
        super(x, y, width, height);
        this.x2 = x;
        this.y2 = y;
        this.width2 = width;
        this.height2 = height;
        this.dWidth2 = Math.random() < 0.5 ? 0.05 : -0.05;
        this.dHeight2 = Math.random() < 0.5 ? 0.05 : -0.05;
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
        score = health;
        color = ColorPalate.randomColor();
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
        if (PlayingPanel.earthquake && type.isEmpty()){
            width2 += dWidth2;
            height2 += dHeight2;
            g.fillRect((int) x2, (int) y2, (int) width2, (int) height2);
        } else {
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

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
            PlayingPanel.score += this.score;
        }
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getWidth2() {
        return width2;
    }

    public void setWidth2(double width2) {
        this.width2 = width2;
    }

    public double getHeight2() {
        return height2;
    }

    public void setHeight2(double height2) {
        this.height2 = height2;
    }

    public String chooseType() {
        int emptyProbability;
        int discoProbability;
        int earthquakeProbability;
        int bombProbability;
        switch (mode) {
            case 1:
                emptyProbability = 6;
                discoProbability = 0;
                earthquakeProbability = 0;
                bombProbability = 0;
                break;
            case 2:
                emptyProbability = 5;
                discoProbability = 0;
                earthquakeProbability = 0;
                bombProbability = 0;
                break;
            case 3:
                emptyProbability = 4;
                discoProbability = 0;
                earthquakeProbability = 0;
                bombProbability = 0;
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
            choices[i + emptyProbability] = PlayingPanel.disco ? "" : "Disco";
        }
        for (int i = 0; i < earthquakeProbability; i++) {
            choices[i + emptyProbability + discoProbability] = PlayingPanel.earthquake ? "" : "Earthquake";
        }
        for (int i = 0; i < bombProbability; i++) {
            choices[i + emptyProbability + discoProbability + earthquakeProbability] = PlayingPanel.bomb ? "" : "Bomb";
        }
        return choices[randomNumber];
    }

    public String getType() {
        return type;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
