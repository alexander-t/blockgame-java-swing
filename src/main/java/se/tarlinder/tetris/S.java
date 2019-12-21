package se.tarlinder.tetris;

import java.util.Arrays;
import java.util.List;

public class S extends Tetromino {
    private static final List<int[][]> shape = Arrays.asList(
            new int[][]{{0, 1, 1}, {1, 1, 0}},
            new int[][]{{1, 0}, {1, 1}, {0, 1}});

    private int rotationIndex = 0;

    @Override
    public int[][] matrix() {
        return shape.get(rotationIndex);
    }

    @Override
    public int getId() {
        return 12;
    }

    public void rotate() {
        rotationIndex = ++rotationIndex % shape.size();
    }

    @Override
    public int[][] rotatedMatrix() {
        return shape.get((rotationIndex + 1) % shape.size());
    }
}
