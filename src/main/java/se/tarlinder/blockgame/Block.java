package se.tarlinder.blockgame;

import java.util.Random;

// A block's x and y are counted from the top left corner.
public class Block {

    // The tetrominos use the SRS rotation system. See https://strategywiki.org/wiki/Tetris/Rotation_systems
    static final Tetromino O = new Tetromino(10,
            new int[][]{{1, 1}, {1, 1}});
    static final Tetromino Z = new Tetromino(11,
            new int[][]{{1, 1, 0}, {0, 1, 1}, {0, 0, 0}},
            new int[][]{{0, 0, 1}, {0, 1, 1}, {0, 1, 0}},
            new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 1, 1}},
            new int[][]{{0, 1, 0}, {1, 1, 0}, {1, 0, 0}}
    );
    static final Tetromino S = new Tetromino(12,
            new int[][]{{0, 1, 1}, {1, 1, 0}, {0, 0, 0}},
            new int[][]{{0, 1, 0}, {0, 1, 1}, {0, 0, 1}},
            new int[][]{{0, 0, 0}, {0, 1, 1}, {1, 1, 0}},
            new int[][]{{1, 0, 0}, {1, 1, 0}, {0, 1, 0}}
    );
    static final Tetromino J = new Tetromino(13,
            new int[][]{{1, 0, 0}, {1, 1, 1}, {0, 0, 0}},
            new int[][]{{0, 1, 1}, {0, 1, 0}, {0, 1, 0}},
            new int[][]{{0, 0, 0}, {1, 1, 1}, {0, 0, 1}},
            new int[][]{{0, 1, 0}, {0, 1, 0}, {1, 1, 0}}
    );
    static final Tetromino L = new Tetromino(14,
            new int[][]{{0, 0, 1}, {1, 1, 1}, {0, 0, 0}},
            new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 1}},
            new int[][]{{0, 0, 0}, {1, 1, 1}, {1, 0, 0}},
            new int[][]{{1, 1, 0}, {0, 1, 0}, {0, 1, 0}}
    );
    static final Tetromino T = new Tetromino(15,
            new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 0, 0}},
            new int[][]{{0, 1, 0}, {0, 1, 1}, {0, 1, 0}},
            new int[][]{{0, 0, 0}, {1, 1, 1}, {0, 1, 0}},
            new int[][]{{0, 1, 0}, {1, 1, 0}, {0, 1, 0}}
    );
    static final Tetromino I = new Tetromino(16,
            new int[][]{{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}},
            new int[][]{{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}},
            new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}},
            new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}}
    );

    private static final Tetromino[] tetrominos = new Tetromino[]{O, Z, S, J, L, T, I};
    private static Random random = new Random();
    private static int nextTetrominoIndex = random.nextInt(tetrominos.length);

    private Tetromino tetromino;
    private int x, y;

    private int[][] board;


    public Block(int centerX, int y, int[][] board) {
        tetromino = tetrominos[nextTetrominoIndex];
        nextTetrominoIndex = random.nextInt(tetrominos.length);

        this.x = centerX - tetromino.getWidth() / 2 + 1;
        this.y = y;
        this.board = board;

        place(x, y, tetromino.getId());
    }

    // Only for testing
    Block(int centerX, int y, int[][] board, Tetromino tetromino) {
        this.x = centerX - tetromino.getWidth() / 2 + 1;
        this.y = y;
        this.board = board;
        this.tetromino = tetromino;
        nextTetrominoIndex = random.nextInt(tetrominos.length);

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
