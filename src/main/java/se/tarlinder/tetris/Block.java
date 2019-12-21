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

    public boolean canMoveRight() {
        return intersect(tetromino.matrix(), x + 1, y);
    }

    public boolean canMoveLeft() {
        return intersect(tetromino.matrix(), x - 1, y);
    }

    public boolean canDrop() {
        return intersect(tetromino.matrix(), x, y + 1);
    }

    public boolean canRotate() {
        return intersect(tetromino.rotatedMatrix(), x, y);
    }

    private boolean intersect(int[][] matrix, int futureX, int futureY) {
        place(x, y, 0);
        for (int squareY = 0; squareY < matrix.length; squareY++) {
            for (int squareX = 0; squareX < matrix[0].length; squareX++) {
                if (matrix[squareY][squareX] == 1 && board[futureY + squareY][futureX + squareX] > 0) {
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
