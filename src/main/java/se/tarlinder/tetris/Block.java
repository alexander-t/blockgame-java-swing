package se.tarlinder.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A block's x and y are counted from the top left corner.
public class Block {

    // Base shapes
    private static final List<int[][]> Z = Arrays.asList(
            new int[][]{{1, 1, 0}, {0, 1, 1}},
            new int[][]{{0, 1}, {1, 1}, {1, 0}});

    private static final List<int[][]> O = new ArrayList<>();
    static {
        // Special case for O's one-element list
        O.add(new int[][]{{1, 1}, {1, 1}});
    }

    private static int nextBlockIndex = 0;

    private List<int[][]> baseShape;
    private int shapeRotationIndex = 0;
    private int[][] rotatedShape;

    private int x, y;
    private int width, height;

    private int[][] board;

    public Block(int x, int y, int[][] board) {
        this.x = x;
        this.y = y;
        this.board = board;

        baseShape = nextBlockIndex == 0 ? O : Z;
        rotatedShape = baseShape.get(0);
        height = rotatedShape.length;
        width = rotatedShape[0].length;

        place(x, y, 1);
        nextBlockIndex = ++nextBlockIndex % 2;
    }

    private void place(int boardX, int boardY, int value) {
         for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (rotatedShape[y][x] > 0) {
                    board[boardY + y][boardX + x] = value;
                }
            }
        }
    }

    public boolean canDrop() {
        for (int squareX = 0; squareX < width; squareX++) {
            if (rotatedShape[height - 1][squareX] == 1 && board[y + height][x + squareX] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight() {
        for (int squareY = 0; squareY < height; squareY++) {
            if (rotatedShape[squareY][width - 1] == 1 && board[y + squareY][x + width] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft() {
        for (int squareY = 0; squareY < height; squareY++) {
            if (rotatedShape[squareY][0] == 1 && board[y + squareY][x - 1] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canRotate() {
        int futureShapeRotationIndex = (shapeRotationIndex + 1) % baseShape.size();
        int[][] futureRotatedShape = Z.get(futureShapeRotationIndex);
        place(x, y, 0);
        for (int squareY = 0; squareY < futureRotatedShape.length; squareY++) {
            for (int squareX = 0; squareX < futureRotatedShape[0].length; squareX++) {
                if (futureRotatedShape[squareY][squareX] == 1 && board[y + squareY][x + squareX] > 0) {
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
        shapeRotationIndex = (shapeRotationIndex + 1) % baseShape.size();
        rotatedShape = baseShape.get(shapeRotationIndex);
        height = rotatedShape.length;
        width = rotatedShape[0].length;
        place(x, y, 1);
    }
}
