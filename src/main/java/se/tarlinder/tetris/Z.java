package se.tarlinder.tetris;

public class Z extends Tetromino {
    public Z() {
        super(11,
                new int[][]{{1, 1, 0}, {0, 1, 1}},
                new int[][]{{0, 1}, {1, 1}, {1, 0}}
        );
    }
}
