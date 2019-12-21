package se.tarlinder.tetris;

public abstract class Tetromino {
    public abstract int[][] matrix();

    public int getWidth() {
        return matrix()[0].length;
    }

    public int getHeight() {
        return matrix().length;
    }

    public void rotate() {
        // Do nothing
    }

    public int[][] rotatedMatrix() {
        return matrix();
    }
}
