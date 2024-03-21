import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.TreeSet;

public class PlayingPanel extends JPanel {
    private boolean launched;
    private PreviewLine line;
    private final Color ballColor;
    private final int PANEL_WIDTH = 500;
    private final int PANEL_HEIGHT = 600;
    private final int ballDiameter = 20;
    private  int ballSpeed = 10;
    private ArrayList<Ball> balls;
    private Timer timer;
    public PlayingPanel(Color ballColor){
        this.ballColor = ballColor;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        balls = new ArrayList<>();
        balls.add(new Ball((PANEL_WIDTH - ballDiameter)/2, PANEL_HEIGHT - ballDiameter, ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
        this.line = new PreviewLine();
        line.setX1(balls.get(0).getX() + ballDiameter/2);
        line.setY1(balls.get(0).getY() + ballDiameter/2);
        this.timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Ball ball : balls) {
                    if (ball.isLaunchReady()) {
                        launch(ball);
                    }
                }
                boolean launchedtmp = false;
                for (Ball ball : balls){
                    if (ball.isActive()){
                        launchedtmp = true;
                    }
                }
                launched = launchedtmp;
                if (!launchedtmp){
                    line.setX1(balls.get(0).getX() + ballDiameter/2);
                    line.setY1(balls.get(0).getY() + ballDiameter/2);
                    balls.add(new Ball(balls.get(0).getX(), balls.get(0).getY(), ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
                }
                repaint();
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
    }
    public void paint(Graphics g){
        for (Ball ball : balls){
            ball.draw(g);
        }
        if (!launched){
            line.paintComponent(g);
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
            if (ball.hitsFloor()){
                ball.setActive(false);
                ball.setLaunchReady(false);
            }
        }
    }
}
