import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.TreeSet;

public class PlayingPanel extends JPanel {
    private final int mode;
    private int ballPower;
    private int ballsToAdd = 0;
    BrickGrid grid;
    private boolean launched;
    private PreviewLine line;
    private final Color ballColor;
    private final int PANEL_WIDTH = 500;
    private final int PANEL_HEIGHT = 600;
    private final int ballDiameter = 20;
    private int ballSpeed = 10;
    private final int brickSpeed;
    private ArrayList<Ball> balls;
    private Timer timer, timer2;
    public PlayingPanel(Color ballColor, int mode){
        this.ballPower = 1;
        this.mode = mode;
        this.grid = new BrickGrid(mode);
        this.ballColor = ballColor;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        balls = new ArrayList<>();
        balls.add(new Ball((PANEL_WIDTH - ballDiameter)/2, PANEL_HEIGHT - ballDiameter, ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
        this.line = new PreviewLine();
        line.setX1(balls.get(0).getX() + ballDiameter/2);
        line.setY1(balls.get(0).getY() + ballDiameter/2);
//        brickSpeed = switch (mode){case 1 -> 1; case 2 -> 2; case 3 -> 4;
//            default -> throw new IllegalStateException("Unexpected value: " + mode);
//        };
        brickSpeed = mode;
        this.timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Ball> tmp = new ArrayList<>(balls);
                for (Ball ball : tmp) {
                    if (ball.isLaunchReady()) {
                        launch(ball);
                    }
                }
                if (grid.isReadyToEnd()){
                    closeFrame();
                }
                repaint();
            }
        });
        this.timer2 = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!launched){
                    grid.moveObjects(brickSpeed);
                    repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (line.getY2() >= balls.getFirst().getY() - 15) {
                    return;
                }
                if (!launched) {
                    //set overall release here
                    launched = true;
                    //sets release of each ball with small delay
                    setLaunchReadyWithDelay();

                    //calculate vx and vy based on mouse release position
                    double angle = Math.atan2(line.getY2() - balls.getFirst().getY(), line.getX2() - balls.getFirst().getX());
                    double vx = Math.round(ballSpeed*Math.cos(angle));
                    double vy = Math.round(ballSpeed*Math.sin(angle));

                    for (Ball ball : balls) {
                        ball.setActive(true);
                        ball.setVx((int)vx);
                        ball.setVy((int)vy);
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!launched) {
                    int mouseX = (int) e.getPoint().getX();
                    int mouseY = (int) e.getPoint().getY();
                    line.setX2(mouseX);
                    line.setY2(mouseY);
                    repaint();
                }
            }
        });
        timer.start();
        timer2.start();
    }
    public void paint(Graphics g){
        grid.paint(g);
        for (Ball ball : balls){
            ball.draw(g);
        }
        if (!launched){
            line.paintComponent(g);
            g.setFont(new Font("Calibri", Font.PLAIN, 16));
            g.drawString(balls.size() + " ×", balls.getFirst().getX() - 30, balls.getFirst().getY() + 3 * ballDiameter / 4);
        }
    }
    private void setLaunchReadyWithDelay() {
        java.util.Timer launchTimer = new java.util.Timer();
        launchTimer.schedule(new TimerTask() {
            ArrayList<Ball> temp = new ArrayList<Ball>(balls);
            Iterator<Ball> iter = temp.iterator();
            Ball ball = iter.next();
            @Override
            public void run() {
                ball.setLaunchReady(true);
                if (iter.hasNext()) {
                    ball = iter.next();
                } else {
                    launchTimer.cancel();
                }
            }
        }, 0, 100);
    }
    public void launch(Ball ball){
        if (ball.isActive()){
            ball.move();
            ball.bounce(ball.hitWallDirection());
            for (int i = 0; i < grid.getGrid().length; i++){
                for (int j = 0; j < grid.getGrid()[i].length; j++){
                    GameObject obj = grid.getGrid()[i][j];
                    if (ball.collidesWith(obj)){
                        if (obj instanceof Brick){
                            obj.onCollision(ballPower);
                            ball.bounce(ball.hitObjDirection(obj));
                            if (((Brick) obj).isReadyToBeDestroyed()) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                        } else if (obj instanceof BallNormalItem) {
                            obj.onCollision(ballPower);
                            if (((BallNormalItem) obj).isReadyToBeDestroyed()) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            ballsToAdd++;
                        }
                    }
                }
            }

            if (ball.hitsFloor()){
                ball.setActive(false);
                ball.setLaunchReady(false);
                boolean launchEnded = true;
                for (Ball ball1 : balls){
                    if (ball1.isActive()){
                        launchEnded = false;
                    } else {
                        ball.setX(ball1.getX());
                        ball.setY(ball1.getY());
                    }
                }
                if (launchEnded){
                    launched = false;
                    line.setX1(balls.get(0).getX() + ballDiameter/2);
                    line.setY1(balls.get(0).getY() + ballDiameter/2);
                    balls.add(new Ball(balls.get(0).getX(), balls.get(0).getY(), ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
                    grid.nextRound(true);
                    grid.setLevel(grid.getLevel() + 1);
                    for (int i = 0; i < ballsToAdd; i++){
                        balls.add(new Ball(balls.getFirst().getX(), balls.getFirst().getY(), ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
                    }
                    ballsToAdd = 0;
                }
            }
        }
    }
    public void closeFrame(){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose();
            timer.stop();
            timer2.stop();
        }
    }
}
