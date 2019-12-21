package se.tarlinder.tetris;

import java.util.Arrays;
import java.util.List;

// A block's x and y are counted from the top left corner.
public class Block {
    //private static final int[][] shape = new int[][]{{1, 1}, {1, 1}};
    private static final List<int[][]> Z = Arrays.asList(
            new int[][]{{1, 1, 0}, {0, 1, 1}},
            new int[][]{{0, 1}, {1, 1}, {1, 0}});

    private int shapeIndex = 0;
    private int[][] shape = Z.get(shapeIndex);

    private int x, y;
    private int width, height;

    private int[][] board;

    public Block(int x, int y, int[][] board) {
        this.x = x;
        this.y = y;
        this.board = board;

        height = shape.length;
        width = shape[0].length;

        place(x, y, 1);
    }

    private void place(int boardX, int boardY, int value) {
         for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (shape[y][x] > 0) {
                    board[boardY + y][boardX + x] = value;
                }
            }
        }
    }

    public boolean canDrop() {
        for (int squareX = 0; squareX < width; squareX++) {
            if (shape[height - 1][squareX] == 1 && board[y + height][x + squareX] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight() {
        for (int squareY = 0; squareY < height; squareY++) {
            if (shape[squareY][width - 1] == 1 && board[y + squareY][x + width] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft() {
        for (int squareY = 0; squareY < height; squareY++) {
            if (shape[squareY][0] == 1 && board[y + squareY][x - 1] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canRotate() {
        int futureShapeIndex = (shapeIndex + 1) % Z.size();
        int[][] futureShape = Z.get(futureShapeIndex);
        place(x, y, 0);
        for (int squareY = 0; squareY < futureShape.length; squareY++) {
            for (int squareX = 0; squareX < futureShape[0].length; squareX++) {
                if (futureShape[squareY][squareX] == 1 && board[y + squareY][x + squareX] > 0) {
                    place(x, y, 1);
                    return false;
                }
            }
        }
        place(x, y, 1);
        return true;
    }

    public void drop() {
        place(x, y++, 0);
        place(x, y, 1);
    }

    public void moveRight() {
        place(x++, y, 0);
        place(x, y, 1);
    }

    public void moveLeft() {
        place(x--, y, 0);
        place(x, y, 1);
    }

    public void rotate() {
        place(x, y, 0);
        shapeIndex = (shapeIndex + 1) % Z.size();
        shape = Z.get(shapeIndex);
        height = shape.length;
        width = shape[0].length;
        place(x, y, 1);
    }
}
