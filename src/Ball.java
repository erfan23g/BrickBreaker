
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Ball {
    private final Color color;
    private int x;
    private int y;
    private int diameter;

    private int vx;
    private int vy;
    private int maxX;
    private int maxY;
    private boolean isActive; //whether the ball is currently moving
    private boolean launchReady; //whether the ball is ready to be launched

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public Ball(int x, int y, int diameter, int courtWidth, int courtHeight, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.maxX = courtWidth - diameter;
        this.maxY = courtHeight - diameter;
        this.color = color;
        this.isActive = false;
        this.launchReady = false;
    }


    public void draw(Graphics g) {
        if (PlayingPanel.disco) {
            g.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        } else {
            g.setColor(color);
        }
        g.fillOval(getX(), getY(), getDiameter(), getDiameter());
    }

    public void move() {
        setX(getX() + this.vx);
        setY(getY() + this.vy);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean status) {
        isActive = status;
    }


    //checks if the ball hits the floor
    //returns true only if the ball hits the floor while coming downward
    public boolean hitsFloor() {
        return getY() + this.vy >= this.maxY
                && getY() < this.maxY;
    }

    public void bounce(Direction d) {
        if (d == null) return;

        switch (d) {
            case UP:
                this.vy = Math.abs(this.vy);
//                this.vy = -this.vy;
                break;
            case DOWN:
                this.vy = -Math.abs(this.vy);
//                this.vy = -this.vy;
                break;
            case LEFT:
                this.vx = Math.abs(this.vx);
//                this.vx = -this.vx;
                break;
            case RIGHT:
                this.vx = -Math.abs(this.vx);
//                this.vx = -this.vx;
                break;
        }
    }

    public Direction hitWallDirection() {
        if (getX() + this.vx < 0) {
            return Direction.LEFT;
        } else if (getX() + this.vx > this.maxX) {
            return Direction.RIGHT;
        }
        if (getY() + this.vy < 0) {
            return Direction.UP;
        } else {
            return null;
        }
    }

    public boolean collidesWith(GameObject o) {
        if (o instanceof Brick) {
            return (getX() + diameter >= o.getX()
                    && getY() + diameter >= o.getY()
                    && o.getX() + o.getWidth() >= getX()
                    && o.getY() + o.getHeight() >= getY());
        } else {
            return (getX() + diameter >= o.getX() + o.getWidth() / 2 - 10
                    && getY() + diameter >= o.getY() + o.getHeight() / 2 - 10
                    && o.getX() + o.getWidth() / 2 + 10 >= getX()
                    && o.getY() + o.getHeight() / 2 + 10 >= getY());
        }
    }


    public Direction getDirectionToObject(GameObject o) {


//         Calculate centers of both objects
        double centerX = getX() + diameter / 2;
        double centerY = getY() + diameter / 2;
        double targetCenterX = o.getX() + o.getWidth() / 2;
        double targetCenterY = o.getY() + o.getHeight() / 2;

        // Calculate differences in positions
        double deltaX = targetCenterX - centerX;
        double deltaY = targetCenterY - centerY;

        if (vx >= 0) {
            // right
            if (vy >= 0){
                // down
                return centerY >= o.getY() ? Direction.RIGHT : Direction.DOWN;
            } else {
                // up
                return centerY <= o.getY() + o.getHeight() ? Direction.RIGHT : Direction.UP;
            }
        } else {
            // left
            if (vy >= 0){
                // down
                return centerY >= o.getY() ? Direction.LEFT : Direction.DOWN;
            } else {
                // up
                return centerY <= o.getY() + o.getHeight() ? Direction.LEFT : Direction.UP;
            }
        }

    }

    public boolean isLost(BrickGrid grid) {
        for (GameObject[] row : grid.getGrid()) {
            for (GameObject object : row) {
                if (getX() >= object.getX()
                        && getX() + getDiameter() <= object.getX() + object.getWidth()
                        && getY() >= object.getY()
                        && getY() + getDiameter() <= object.getY() + object.getHeight()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLost(Brick object) {
        return getX() >= object.getX()
                && getX() + getDiameter() <= object.getX() + object.getWidth()
                && getY() >= object.getY()
                && getY() + getDiameter() <= object.getY() + object.getHeight();
    }


    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVx() {
        return this.vx;
    }

    public int getVy() {
        return this.vy;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public void setLaunchReady(boolean launchReady) {
        this.launchReady = launchReady;
    }

    public boolean isLaunchReady() {
        return this.launchReady;
    }

}
