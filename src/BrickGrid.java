
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

public class BrickGrid extends JComponent {
    private int level;
    private final int mode;
    private boolean readyToEnd;
    private GameObject[][] grid;
    private JLabel score;
    private static final int PADDING = 5;
    public static final int BRICK_WIDTH = 94;
    public static final int BRICK_HEIGHT = 54;
    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 10;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BrickGrid(int mode) {
        this.level = 0;
        this.mode = mode;
        this.readyToEnd = false;
        grid = new GameObject[GRID_HEIGHT][GRID_WIDTH];
        initializeGrid();
    }


    @Override
    public void paintComponent(Graphics g) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (!PlayingPanel.disco || Math.random() < 0.5){
                    grid[r][c].draw(g);
                }
            }
        }
    }

    @Override
    public Dimension getSize() {
        return new Dimension(grid[0].length * (BRICK_WIDTH + PADDING) + PADDING,
                grid.length * (BRICK_HEIGHT + PADDING) + PADDING);
    }

    @Override
    public int getWidth() {
        return grid[0].length * (BRICK_WIDTH + PADDING) + PADDING;
    }

    @Override
    public int getHeight() {
        return grid.length * (BRICK_HEIGHT + PADDING) + PADDING;
    }

    public GameObject[][] getGrid() {
        return grid;
    }

    public GameObject[][] getGridCopy() {
        GameObject[][] copy = new GameObject[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                copy[r][c] = grid[r][c];
            }
        }
        return copy;
    }

    private GameObject selectRandomBlock(int x, int y) {

        int emptyProbability;
        int brickProbability;
        int normalItemProbability;
        switch (mode){
            case 1:
                emptyProbability = 6;
                brickProbability = 3;
                normalItemProbability = 1;
                break;
            case 2:
                emptyProbability = 5;
                brickProbability = 4;
                normalItemProbability = 1;
                break;
            case 3:
                emptyProbability = 2;
                brickProbability = 7;
                normalItemProbability = 1;
                break;
            default:
                throw new RuntimeException();
        }
        int totalBasket = emptyProbability + brickProbability + normalItemProbability;
        int randomNumber = (int) (Math.random() * totalBasket);

        GameObject[] choices = new GameObject[totalBasket];
        for (int i = 0; i < emptyProbability; i++) {
            choices[i] = new EmptyObject(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        }
        for (int i = 0; i < brickProbability; i++) {
            choices[i + emptyProbability] = new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT, mode, level);
        }
        for (int i = 0; i < normalItemProbability; i++) {
            choices[i + emptyProbability + brickProbability] = new NormalItem(x, y, BRICK_WIDTH, BRICK_HEIGHT, mode);
        }
        return choices[randomNumber];
    }

    private void initializeGrid() {
        int xPos = PADDING;
        int yPos = PADDING;
//        for (int c = 0; c < grid[0].length; c++){
//            grid[0][c] = new EmptyObject(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
//            xPos += BRICK_WIDTH + PADDING;
//        }
//        xPos = PADDING;
//        yPos += BRICK_HEIGHT + PADDING;
        //initialize top rows with random blocks
        for (int c = 0; c < grid[0].length; c++) {
            if (c == grid[0].length - 1) {
                boolean noBrick = true;
                boolean allBrick = true;
                for (GameObject obj : grid[0]) {
                    if (obj instanceof Brick) {
                        noBrick = false;
                        break;
                    } else {
                        allBrick = false;
                    }
                }
                if (noBrick) {
                    grid[0][c] = new Brick(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT, mode, level);
                } else if (allBrick) {
                    grid[0][c] = new EmptyObject(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
                } else {
                    grid[0][c] = selectRandomBlock(xPos, yPos);
                }
            } else {
                grid[0][c] = selectRandomBlock(xPos, yPos);
            }
            xPos += BRICK_WIDTH + PADDING;
        }
        xPos = PADDING;
        yPos += BRICK_HEIGHT + PADDING;
        //leave the bottom few rows empty on game start
        for (int r = 1; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = new EmptyObject(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
                xPos += BRICK_WIDTH + PADDING;
            }
            xPos = PADDING;
            yPos += BRICK_HEIGHT + PADDING;
        }
    }

    public void nextRound(boolean shift) {
        if (shift) {
            //shift all the elements in the grid down by 1 row
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    int currentY = grid[r][c].getY();
                    grid[r][c].setY(currentY + BRICK_HEIGHT + PADDING);
                }
            }
        }
        //store all the shifted elements in a temp 2d array
        GameObject[][] copy = new GameObject[grid.length][grid[0].length];
        for (int r = 0; r < copy.length; r++) {
            for (int c = 0; c < copy[r].length; c++) {
                copy[r][c] = grid[r][c];
            }
        }
        //shift the actual 2d array down by 1 row, leaving out the bottom row
        for (int r = 1; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = copy[r - 1][c];
            }
        }

        //fill in the top row with new randomly generated blocks
        int xPos = PADDING;
        int yPos = PADDING;
        for (int c = 0; c < grid[0].length; c++) {
            if (c == grid[0].length - 1) {
                boolean noBrick = true;
                boolean allBrick = true;
                for (GameObject obj : grid[0]) {
                    if (obj instanceof Brick) {
                        noBrick = false;
                        break;
                    } else {
                        allBrick = false;
                    }
                }
                if (noBrick) {
                    grid[0][c] = new Brick(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT, mode, level);
                } else if (allBrick) {
                    grid[0][c] = new EmptyObject(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
                } else {
                    grid[0][c] = selectRandomBlock(xPos, yPos);
                }
            } else {
                grid[0][c] = selectRandomBlock(xPos, yPos);
            }
            xPos += BRICK_WIDTH + PADDING;
        }
//        incrementScore();
    }


    public void moveObjects(int speed) {
        int i = 0;
        for (GameObject[] row : grid) {
            for (GameObject object : row) {
                object.setY(object.getY() + speed);
                if (object instanceof Brick && object.getY() >= 536) {
                    if (PlayingPanel.heart) {
                        for (int j = i - 3; j < GRID_HEIGHT; j++) {
                            for (int k = 0; k < GRID_WIDTH; k++) {
                                if (grid[j][k] instanceof Brick) {
                                    grid[j][k] = new EmptyObject(grid[j][k].getX(), grid[j][k].getY(), BRICK_WIDTH, BRICK_HEIGHT);
                                }
                            }
                        }
                        PlayingPanel.heart = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "You lost", "Game over", JOptionPane.PLAIN_MESSAGE);
                        setReadyToEnd(true);
                    }
                }
            }
            i++;
        }
        if (grid[0][0].getY() >= 64) {
            nextRound(false);
        }
    }

    public void shiftUp() {
//        for (int i = 0; i < 2; i++){
//            for (int j = 0; j < GRID_WIDTH; j++){
//                int x = grid[i][j].getX(), y = grid[i][j].getY();
//                grid[i][j] = new EmptyObject(x, y, BRICK_WIDTH, BRICK_HEIGHT);
//            }
//        }
        for (int r = 0; r < GRID_HEIGHT; r++) {
            for (int c = 0; c < GRID_WIDTH; c++) {
                int currentY = grid[r][c].getY();
                grid[r][c].setY(currentY - 2 * (BRICK_HEIGHT + PADDING));
                if (grid[r][c].getY() < 5) {
                    int x = grid[r][c].getX(), y = grid[r][c].getY();
                    grid[r][c] = new EmptyObject(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }
//        //store all the shifted elements in a temp 2d array
//        GameObject[][] copy = new GameObject[grid.length][grid[0].length];
//        for (int r = 0; r < copy.length; r++) {
//            for (int c = 0; c < copy[r].length; c++) {
//                copy[r][c] = grid[r][c];
//            }
//        }
//        //shift the actual 2d array down by 1 row, leaving out the bottom row
//        for (int r = 2; r < grid.length; r++) {
//            for (int c = 0; c < grid[r].length; c++) {
//                grid[r][c] = copy[r - 2][c];
//            }
//        }

    }


    public boolean isReadyToEnd() {
        return readyToEnd;
    }

    public void setReadyToEnd(boolean readyToEnd) {
        this.readyToEnd = readyToEnd;
    }
}
