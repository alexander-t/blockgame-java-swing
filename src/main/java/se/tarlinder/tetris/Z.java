package se.tarlinder.tetris;

import java.util.Arrays;
import java.util.List;

public class Z extends Tetromino {
    private static final List<int[][]> shape = Arrays.asList(
            new int[][]{{1, 1, 0}, {0, 1, 1}},
            new int[][]{{0, 1}, {1, 1}, {1, 0}});

    private int rotationIndex = 0;

    @Override
    public int[][] matrix() {
        return shape.get(rotationIndex);
    }

    public void rotate() {
        rotationIndex = ++rotationIndex % shape.size();
    }

    @Override
    public int[][] rotatedMatrix() {
        return shape.get((rotationIndex + 1) % shape.size());
    }
}
