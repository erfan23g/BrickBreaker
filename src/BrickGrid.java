
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

public class BrickGrid extends JComponent {
    private final int mode;

    private GameObject[][] grid;
    private JLabel score;
    private static final int PADDING = 5;
    private static final int BRICK_WIDTH = 94;
    private static final int BRICK_HEIGHT = 54;
    private static final int COIN_DIAMETER = 12;
    private static final int GRID_WIDTH = 5;
    private static final int GRID_HEIGHT = 10;

    public BrickGrid(int mode) {
        this.mode = mode;
        grid = new GameObject[GRID_HEIGHT][GRID_WIDTH];
        initializeGrid();
    }


    @Override
    public void paintComponent(Graphics g) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c].draw(g);
            }
        }
    }

    @Override
    public Dimension getSize() {
        return new Dimension(grid[0].length*(BRICK_WIDTH+PADDING)+PADDING,
                grid.length*(BRICK_HEIGHT+PADDING)+PADDING);
    }

    @Override
    public int getWidth() {
        return grid[0].length*(BRICK_WIDTH+PADDING)+PADDING;
    }

    @Override
    public int getHeight() {
        return grid.length*(BRICK_HEIGHT+PADDING)+PADDING;
    }

    public GameObject[][] getGrid() {
        return grid;
    }

    public GameObject[][] getGridCopy(){
        GameObject[][] copy = new GameObject[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                copy[r][c] = grid[r][c];
            }
        }
        return copy;
    }

    private GameObject selectRandomBlock(int x, int y) {
        //return either an EmptyBlock, NumBlockSprite, or CoinSprite randomly
        //CoinSprite is generated with a lower probability than the other two

        int emptyProbability = 4;
        int numBlockProbability = 4;
        int coinProbability = 0;
        int totalBasket = emptyProbability + numBlockProbability + coinProbability;
        int randomNumber = (int) (Math.random() * totalBasket);

        GameObject [] choices = new GameObject[totalBasket];
        for (int i = 0; i < emptyProbability; i++) {
            choices[i] = new EmptyObject(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        }
        for (int i = 0; i < numBlockProbability; i++) {
            choices[i+emptyProbability] = new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        }
//        for (int i = 0; i < coinProbability; i++) {
//            choices[i+emptyProbability+numBlockProbability] = new CoinSprite(x, y,
//                    COIN_DIAMETER, BLOCK_SIDE_LENGTH);
//        }
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
            if (c == grid[0].length - 1){
                boolean noBrick = true;
                boolean allBrick = true;
                for (GameObject obj : grid[0]){
                    if (obj instanceof Brick){
                        noBrick = false;
                        break;
                    } else {
                        allBrick = false;
                    }
                }
                if (noBrick){
                    grid[0][c] = new Brick(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
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

    public void nextRound() {
        //shift all the elements in the grid down by 1 row
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int currentY = grid[r][c].getY();
                grid[r][c].setY(currentY+BRICK_HEIGHT+PADDING);
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
                grid[r][c] = copy[r-1][c];
            }
        }
        if (isGameOver()){
            JOptionPane.showMessageDialog(null, "You lost", "Game over", JOptionPane.PLAIN_MESSAGE);
        }

        //fill in the top row with new randomly generated blocks
        int xPos = PADDING;
        int yPos = PADDING;
        for (int c = 0; c < grid[0].length; c++) {
            if (c == grid[0].length - 1){
                boolean noBrick = true;
                boolean allBrick = true;
                for (GameObject obj : grid[0]){
                    if (obj instanceof Brick){
                        noBrick = false;
                        break;
                    } else {
                        allBrick = false;
                    }
                }
                if (noBrick){
                    grid[0][c] = new Brick(xPos, yPos, BRICK_WIDTH, BRICK_HEIGHT);
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


    public boolean isGameOver() {
        //check if bottom row is empty
        int lastRow = grid.length - 1;
        for (int i = 0; i < grid[lastRow].length; i++) {
            if (grid[lastRow][i] instanceof Brick) {
                return true;
            }
        }
        return false;
    }

    public void moveObjects(int speed){
        for (GameObject[] row : grid){
            for (GameObject object : row){
                object.setY(object.getY() + speed);
            }
        }
    }

//    private void incrementScore() {
//        int currentScore = Integer.parseInt(score.getText());
//        score.setText(currentScore+1+"");
//    }
}
