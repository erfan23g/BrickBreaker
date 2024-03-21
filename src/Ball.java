
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
        g.setColor(color);
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
                break;
            case DOWN:
                this.vy = -Math.abs(this.vy);
                break;
            case LEFT:
                this.vx = Math.abs(this.vx);
                break;
            case RIGHT:
                this.vx = -Math.abs(this.vx);
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
        return (getX() + diameter >= o.getX()
                && getY() + diameter >= o.getY()
                && o.getX() + o.getWidth() >= getX()
                && o.getY() + o.getHeight() >= getY());
    }

    public Direction hitObjDirection(GameObject o) {
        double dx = o.getX() + (double) o.getWidth() / 2 - (getX() + (double) diameter / 2);
        double dy = o.getY() + (double) o.getHeight() / 2 - (getY() + (double) diameter / 2);

        double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy * dy)));
        double diagTheta = Math.atan2((double) diameter / 2, (double) diameter / 2);

        if (theta <= diagTheta) {
            return Direction.RIGHT;
        } else if (theta > diagTheta && theta <= Math.PI - diagTheta) {
            // Coordinate system for GUIs is switched
            if (dy > 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        } else {
            return Direction.LEFT;
        }
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
