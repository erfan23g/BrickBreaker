import javax.swing.*;
import java.awt.*;

public class Brick extends GameObject{
    private boolean readyToBeDestroyed;

    public boolean isReadyToBeDestroyed() {
        return readyToBeDestroyed;
    }

    public void setReadyToBeDestroyed(boolean readyToBeDestroyed) {
        this.readyToBeDestroyed = readyToBeDestroyed;
    }

    private int health;
    private final Color color;

    public Brick(int x, int y, int width, int height, int mode, int level) {
        super(x, y, width, height);
        // TODO health
        int rnd = (int) (Math.random() * 9);
        switch (mode){
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
        // -------
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
        g.setColor(color);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        FontMetrics fm = g.getFontMetrics();
        int stringWidth = SwingUtilities.computeStringWidth(fm, health+"");

        int centerX = (int) Math.round(getX() + getWidth()/2.0 - stringWidth/2.0);
        int centerY = getY() + (int) Math.round(getHeight()/2.0 + (fm.getHeight()/4.0));

        g.setColor(Color.BLACK);
        g.setFont(new Font("Calibri", Font.PLAIN, 24));
        g.drawString(health + "", centerX, centerY);
    }
    public void onCollision(int ballPower){
        if (health > 0) {
            health -= ballPower;
        }
        if (health <= 0){
            setReadyToBeDestroyed(true);
        }
    }
}