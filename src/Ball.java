
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
        // Calculate centers of both objects
        double centerX = getX() + diameter / 2;
        double centerY = getY() + diameter / 2;
        double targetCenterX = o.getX() + o.getWidth() / 2;
        double targetCenterY = o.getY() + o.getHeight() / 2;

        // Calculate differences in positions
        double deltaX = targetCenterX - centerX;
        double deltaY = targetCenterY - centerY;

        // Use absolute values for comparisons to determine primary direction
        if (Math.abs(deltaX) < Math.abs(deltaY) + 40) {
            // Horizontal movement is dominant
            return deltaY > 0 ? Direction.DOWN : Direction.UP;
        } else {
            // Vertical movement is dominant or equal; in GUI, positive deltaY means DOWN
            return deltaX > 0 ? Direction.RIGHT : Direction.LEFT;
        }
    }

    public Direction hitObjDirection(GameObject o) {
        double dx = o.getX() + o.getWidth() / 2 - (getX() + diameter / 2);
        double dy = o.getY() + o.getHeight() / 2 - (getY() + diameter / 2);

        double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy * dy)));
        double diagTheta = Math.atan2(diameter / 2, diameter / 2);

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

//    public Direction getDirectionToObject(GameObject o) {
//        // Calculate centers of both objects
//        double centerX = getX() + diameter / 2.0;
//        double centerY = getY() + diameter / 2.0;
//        double targetCenterX = o.getX() + o.getWidth() / 2.0;
//        double targetCenterY = o.getY() + o.getHeight() / 2.0;
//
//        // Calculate differences in positions
//        double deltaX = targetCenterX - centerX;
//        double deltaY = targetCenterY - centerY;
//
//        // Adjust for aspect ratio or scale difference
//        // Assuming width is longer than height, you might need to adjust these factors
//        double aspectRatio = (double) o.getWidth() / o.getHeight();
//        double adjustedDeltaX = deltaX * aspectRatio;
//        double adjustedDeltaY = deltaY; // No adjustment for Y since height is standard
//
//        // Compare adjusted differences to determine primary direction
//        if (Math.abs(adjustedDeltaX) > Math.abs(adjustedDeltaY)) {
//            // Horizontal movement is dominant
//            return adjustedDeltaX > 0 ? Direction.RIGHT : Direction.LEFT;
//        } else {
//            // Vertical movement is dominant or equal; in GUI, positive deltaY means DOWN
//            return adjustedDeltaY > 0 ? Direction.DOWN : Direction.UP;
//        }
//    }


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
