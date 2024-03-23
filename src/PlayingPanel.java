import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PlayingPanel extends JPanel {
    public static boolean speed;
    public static boolean power;
    public static boolean heart;
    public static boolean heart2;
    public static boolean disco;
    public static boolean earthquake;
    public static boolean bomb;
    public static boolean paused;
    private int eX, eY;
    private boolean vertigo;
    private int ballPower;
    private int ballsToAdd;
    BrickGrid grid;
    private boolean launched;
    private final PreviewLine line, vertigoLine;
    private final Color ballColor;
    private final int PANEL_WIDTH = 500;
    private final int PANEL_HEIGHT = 600;
    private final int ballDiameter = 20;
    private int ballSpeed;
    private final int brickSpeed;
    public ArrayList<Ball> balls;
    public static int score;
    public static Timer timer, timer2;

    public PlayingPanel(Color ballColor, int mode) {
        paused = false;
        speed = false;
        power = false;
        heart = false;
        heart2 = false;
        disco = false;
        earthquake = false;
        bomb = false;
        vertigo = false;
        score = 0;
        this.ballsToAdd = 0;
        this.ballSpeed = 10;
        this.ballPower = 1;
        this.grid = new BrickGrid(mode);
        this.ballColor = ballColor;
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        balls = new ArrayList<>();
        balls.add(new Ball((PANEL_WIDTH - ballDiameter) / 2, PANEL_HEIGHT - ballDiameter, ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
        this.line = new PreviewLine();
        this.vertigoLine = new PreviewLine();
        line.setX1(balls.get(0).getX() + ballDiameter / 2);
        line.setY1(balls.get(0).getY() + ballDiameter / 2);
        vertigoLine.setX1(balls.get(0).getX() + ballDiameter / 2);
        vertigoLine.setY1(balls.get(0).getY() + ballDiameter / 2);
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
                if (grid.isReadyToEnd()) {
                    closeFrame();
                }
                repaint();
            }
        });
        this.timer2 = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!launched) {
                    grid.moveObjects(brickSpeed);
                    repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!paused){
                    vertigo = false;
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
                        double vx = Math.round(ballSpeed * Math.cos(angle));
                        double vy = Math.round(ballSpeed * Math.sin(angle));

                        for (Ball ball : balls) {
                            ball.setActive(true);
                            ball.setVx((int) (vx));
                            ball.setVy((int) (vy));
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!launched && !paused) {
                    int mouseX = (int) e.getPoint().getX();
                    int mouseY = (int) e.getPoint().getY();
                    line.setX2(mouseX);
                    line.setY2(mouseY);
                    vertigoLine.setX2((int) (Math.random() * PANEL_WIDTH));
                    vertigoLine.setY2((int) (Math.random() * PANEL_HEIGHT));
                    repaint();
                }
            }
        });
        timer.start();
        timer2.start();
    }

    public void paint(Graphics g) {
        grid.paint(g);
        for (Ball ball : balls) {
            if (!PlayingPanel.disco || Math.random() < 0.5) {
                ball.draw(g);
            }
        }
        if (!launched) {
            if (vertigo) {
                vertigoLine.paintComponent(g, ballColor);
            } else {
                line.paintComponent(g, ballColor);
            }
            g.setFont(new Font("Calibri", Font.PLAIN, 16));
            g.drawString(balls.size() + "×", balls.getFirst().getX(), balls.getFirst().getY() - ballDiameter / 2);
        }
        if (bomb){
            ImageIcon imageIcon = new ImageIcon("src/Explosion2.png");
            g.drawImage(imageIcon.getImage(), eX, eY, null);
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

    public void launch(Ball ball) {
        if (ball.isActive()) {
            ball.move();
            ball.bounce(ball.hitWallDirection());
            for (int i = 0; i < grid.getGrid().length; i++) {
                for (int j = 0; j < grid.getGrid()[i].length; j++) {
                    GameObject obj = grid.getGrid()[i][j];
                    if (ball.collidesWith(obj)) {
                        if (obj instanceof Brick) {
                            obj.onCollision(ballPower);
                            ball.bounce(ball.hitObjDirection(obj));
                            if (((Brick) obj).isReadyToBeDestroyed()) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                                if (((Brick) obj).getType().equals("Disco")) {
                                    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                    disco = true;
                                    scheduler.schedule(() -> {
                                        disco = false;
                                    }, 10, TimeUnit.SECONDS);
                                    scheduler.shutdown();
                                } else if (((Brick) obj).getType().equals("Earthquake")) {
                                    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                                    earthquake = true;
                                    scheduler.schedule(() -> {
                                        earthquake = false;
                                    }, 10, TimeUnit.SECONDS);
                                    scheduler.shutdown();
                                } else if (((Brick) obj).getType().equals("Bomb")) {
                                    Explosion(obj.getX() + obj.getWidth() / 2, obj.getY() + obj.getHeight() / 2);
                                }
                            }
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("Ball")) {
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            ballsToAdd++;
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("Speed")) {
                            speed = true;
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                            ballSpeed *= 2;
                            for (Ball ball1 : balls) {
                                ball1.setVx(ball1.getVx() * 2);
                                ball1.setVy(ball1.getVy() * 2);
                            }
                            scheduler.schedule(() -> {
                                ballSpeed /= 2;
                                for (Ball ball1 : balls) {
                                    ball1.setVx(ball1.getVx() / 2);
                                    ball1.setVy(ball1.getVy() / 2);
                                }
                                speed = false;
                            }, 15, TimeUnit.SECONDS);
                            scheduler.shutdown();
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("Power")) {
                            power = true;
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                            ballPower *= 2;
                            scheduler.schedule(() -> {
                                ballPower /= 2;
                                power = false;
                            }, 15, TimeUnit.SECONDS);
                            scheduler.shutdown();
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("Vertigo")) {
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            vertigo = true;
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("Reverse")) {
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            grid.shiftUp();
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("Heart")) {
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                            heart = true;
                            heart2 = true;
                        } else if (obj instanceof NormalItem && ((NormalItem) obj).getType().equals("")) {
                            obj.onCollision(ballPower);
                            if ((((NormalItem) obj).isReadyToBeDestroyed())) {
                                grid.getGrid()[i][j] = new EmptyObject(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
                            }
                        }
                    }
                }
            }

            if (ball.hitsFloor()) {
                ball.setActive(false);
                ball.setLaunchReady(false);
                boolean launchEnded = true;
                for (Ball ball1 : balls) {
                    if (ball1.isActive()) {
                        launchEnded = false;
                    } else {
                        ball.setX(ball1.getX());
                        ball.setY(ball1.getY());
                    }
                }
                if (launchEnded) {
                    launched = false;
                    line.setX1(balls.get(0).getX() + ballDiameter / 2);
                    line.setY1(balls.get(0).getY() + ballDiameter / 2);
                    vertigoLine.setX1(balls.get(0).getX() + ballDiameter / 2);
                    vertigoLine.setY1(balls.get(0).getY() + ballDiameter / 2);
                    balls.add(new Ball(balls.get(0).getX(), balls.get(0).getY(), ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
                    grid.nextRound(true);
                    grid.setLevel(grid.getLevel() + 1);
                    for (int i = 0; i < ballsToAdd; i++) {
                        balls.add(new Ball(balls.getFirst().getX(), balls.getFirst().getY(), ballDiameter, PANEL_WIDTH, PANEL_HEIGHT, ballColor));
                    }
                    ballsToAdd = 0;
                }
            }
        }
    }

    public void closeFrame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose();
            timer.stop();
            timer2.stop();
        }
    }

    public void Explosion(int x, int y) {
        ImageIcon imageIcon = new ImageIcon("src/Explosion2.png");
        eX = x - imageIcon.getIconWidth()/2;
        eY = y - imageIcon.getIconHeight()/2;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        bomb = true;
        scheduler.schedule(() -> {
            bomb = false;
        }, 1, TimeUnit.SECONDS);
        scheduler.shutdown();

        for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[i].length; j++){
                GameObject object = grid.getGrid()[i][j];
                if (object instanceof Brick){
                    outerLoop:
                    for (int u = object.getX(); u <= object.getX() + object.getWidth(); u++){
                        for (int v = object.getY(); v <= object.getY() + object.getHeight(); v++){
                            double distance = Math.sqrt(Math.pow(u - x, 2) + Math.pow(v - y, 2));
                            if (distance <= 120.0){
                                object.onCollision(50);
                                if (((Brick) object).isReadyToBeDestroyed()){
                                    grid.getGrid()[i][j] = new EmptyObject(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                                }
                                break outerLoop;
                            }
                        }
                    }
                }
            }
        }
    }
}
