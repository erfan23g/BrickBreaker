import java.awt.*;

public class BallNormalItem extends GameObject {
    private final int diameter = 20;
    private boolean readyToBeDestroyed;

    public BallNormalItem(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.readyToBeDestroyed = false;
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
            g.setColor(Color.BLACK);
            int startX = getX() + (int) Math.round(getWidth()/2.0 - diameter/2.0);
            int startY = getY() +(int) Math.round(getHeight()/2.0 - diameter/2.0);
            g.fillOval(startX, startY, diameter, diameter);
        }
    }

    @Override
    public void onCollision(int ballPower) {
        setReadyToBeDestroyed(true);

    }

    public int getDiameter() {
        return diameter;
    }
}
