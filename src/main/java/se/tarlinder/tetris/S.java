package se.tarlinder.tetris;

public class S extends Tetromino {

    public S() {
        super(12,
                new int[][]{{0, 1, 1}, {1, 1, 0}},
                new int[][]{{1, 0}, {1, 1}, {0, 1}}
        );
    }
}
