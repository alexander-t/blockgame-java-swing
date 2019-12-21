package se.tarlinder.tetris;

public class O extends Tetromino {

    private static final int[][] matrix = new int[][]{{1, 1}, {1, 1}};

    @Override
    public int[][] matrix() {
        return matrix;
    }

    @Override
    public int getId() {
        return 10;
    }
}
