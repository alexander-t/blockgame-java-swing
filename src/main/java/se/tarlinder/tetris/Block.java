package se.tarlinder.tetris;

// A block's x and y are counted from the top left corner.
public class Block {

    private static final Tetromino Z = new Z();
    private static final Tetromino O = new O();

    private static int nextBlockIndex = 0;

    private Tetromino tetromino;

    private int x, y;

    private int[][] board;

    public Block(int x, int y, int[][] board) {
        this.x = x;
        this.y = y;
        this.board = board;

        tetromino = nextBlockIndex == 0 ? O : Z;
        nextBlockIndex = ++nextBlockIndex % 2;

        place(x, y, tetromino.getId());
    }

    private void place(int boardX, int boardY, int value) {
         for (int y = 0; y < tetromino.getHeight(); y++) {
            for (int x = 0; x < tetromino.getWidth(); x++) {
                if (tetromino.matrix()[y][x] > 0) {
                    board[boardY + y][boardX + x] = value;
                }
            }
        }
    }

    public boolean canDrop() {
        for (int squareX = 0; squareX < tetromino.getWidth(); squareX++) {
            if (tetromino.matrix()[tetromino.getHeight() - 1][squareX] == 1
                    && board[y + tetromino.getHeight()][x + squareX] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight() {
        for (int squareY = 0; squareY < tetromino.getHeight(); squareY++) {
            if (tetromino.matrix()[squareY][tetromino.getWidth() - 1] == 1
                    && board[y + squareY][x + tetromino.getWidth()] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft() {
        for (int squareY = 0; squareY < tetromino.getHeight(); squareY++) {
            if (tetromino.matrix()[squareY][0] == 1 && board[y + squareY][x - 1] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canRotate() {
        int[][] futureRotatedShape = tetromino.rotatedMatrix();
        place(x, y, 0);
        for (int squareY = 0; squareY < futureRotatedShape.length; squareY++) {
            for (int squareX = 0; squareX < futureRotatedShape[0].length; squareX++) {
                if (futureRotatedShape[squareY][squareX] == 1 && board[y + squareY][x + squareX] > 0) {
                    place(x, y, tetromino.getId());
                    return false;
                }
            }
        }
        place(x, y, tetromino.getId());
        return true;
    }

    public void drop() {
        place(x, y++, 0);
        place(x, y, tetromino.getId());
    }

    public void moveRight() {
        place(x++, y, 0);
        place(x, y, tetromino.getId());
    }

    public void moveLeft() {
        place(x--, y, 0);
        place(x, y, tetromino.getId());
    }

    public void rotate() {
        place(x, y, 0);
        tetromino.rotate();
        place(x, y, tetromino.getId());
    }
}
