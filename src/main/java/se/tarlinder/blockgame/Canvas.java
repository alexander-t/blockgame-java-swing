package se.tarlinder.blockgame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Canvas extends JPanel {

    private static final Color BACKGROUND = new Color(245, 235, 255);
    private static final Color DARK_GREEN = new Color(0, 150, 0);
    private static final Color DARK_YELLOW = new Color(200, 200, 0);
    private static final Color DARK_ORANGE = new Color(200, 100, 0);
    private static final Color DARK_RED = new Color(200, 0, 0);
    private static final Color DARK_CYAN = new Color(0, 150, 150);

    private static final Map<Integer, Color> BLOCK_COLORS = Map.of(
            10, DARK_YELLOW,
            11, DARK_RED,
            12, DARK_GREEN,
            13, Color.blue,
            14, DARK_ORANGE,
            15, Color.magenta,
            16, DARK_CYAN
    );

    private final int squareSize;
    private final int columns, rows;
    private final int[][] board;

    public Canvas(int squareSize, int[][] board) {
        this.squareSize = squareSize;
        this.board = board;
        this.columns = board[0].length;
        this.rows = board.length;
        setPreferredSize(new Dimension(columns * squareSize, rows * squareSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color drawColor;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                boolean drawingBlock = false;
                drawColor = BLOCK_COLORS.get(board[y][x]);
                if (drawColor != null) {
                    drawingBlock = true;
                } else if (board[y][x] == 2) {
                    drawColor = Color.black;
                } else {
                    drawColor = BACKGROUND;
                }

                g.setColor(drawColor);
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

                if (drawingBlock) {
                    for (int i = 0; i < squareSize / 2; i++) {
                        g.setColor(drawColor);
                        g.drawRect(x * squareSize + i, y * squareSize + i, squareSize - 1 - 2 * i, squareSize - 1 - 2 * i);
                        drawColor = new Color(
                                Math.min(drawColor.getRed() + 8, 255),
                                Math.min(drawColor.getGreen() + 8, 255),
                                Math.min(drawColor.getBlue() + 8, 255)
                        );
                    }

                    // Border and soft corners
                    g.setColor(BACKGROUND);
                    g.drawRect(x * squareSize, y * squareSize, squareSize - 1, squareSize - 1);
                    g.drawRect(x * squareSize + 1, y * squareSize + 1, 1, 1);
                    g.drawRect((x + 1) * squareSize - 2, y * squareSize + 1, 1, 1);
                    g.drawRect(x * squareSize + 1, (y + 1) * squareSize - 2, 1, 1);
                    g.drawRect((x + 1) * squareSize - 2, (y + 1) * squareSize - 2, 1, 1);
                }
            }
        }
    }
}
