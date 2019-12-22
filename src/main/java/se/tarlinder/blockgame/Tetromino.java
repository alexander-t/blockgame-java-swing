package se.tarlinder.blockgame;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * Describes a tetromino and its matrix.
 */
public class Tetromino {

    private final int id;
    protected final List<int[][]> shape = new ArrayList<>();
    protected int rotationIndex = 0;

    public Tetromino(int id, int[][]... rotations) {
        this.id = id;
        stream(rotations).forEach(r -> shape.add(r));
    }

    public int[][] matrix() {
        return shape.get(rotationIndex);
    }

    public int getWidth() {
        return matrix()[0].length;
    }

    public int getHeight() {
        return matrix().length;
    }

    public int getId() {
        return id;
    }

    public void rotate() {
        rotationIndex = ++rotationIndex % shape.size();
    }

    public int[][] rotatedMatrix() {
        return shape.get((rotationIndex + 1) % shape.size());
    }
}
