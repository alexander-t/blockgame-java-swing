package se.tarlinder.tetris;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private static final Color BACKGROUND = new Color(245, 235, 255);
    private static final Color DARK_GREEN = new Color(0, 150, 0);
    private static final Color DARK_YELLOW = new Color(200, 200, 0);

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
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                boolean drawingBlock = false;
                if (board[y][x] == 2) {
                    g.setColor(Color.black);
                } else if (board[y][x] == 10) {
                    g.setColor(DARK_YELLOW);
                    drawingBlock = true;
                } else if (board[y][x] == 11) {
                    g.setColor(new Color(200, 0, 0));
                    drawingBlock = true;
                } else if (board[y][x] == 12) {
                    g.setColor(DARK_GREEN);
                    drawingBlock = true;
                } else if (board[y][x] == 13) {
                    g.setColor(Color.blue);
                    drawingBlock = true;
                } else if (board[y][x] == 14) {
                    g.setColor(new Color(200, 100, 0));
                    drawingBlock = true;
                } else if (board[y][x] == 15) {
                    g.setColor(Color.magenta);
                    drawingBlock = true;
                } else if (board[y][x] == 16) {
                    g.setColor(new Color(0, 150, 150));
                    drawingBlock = true;
                } else {
                    g.setColor(BACKGROUND);
                }
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

                if (drawingBlock) {
                    Color c = g.getColor();
                    for (int i = 0; i < 20; i++) {
                        g.setColor(c);
                        g.drawRect(x * squareSize + i, y * squareSize + i, squareSize - 1 - 2 * i, squareSize - 1 - 2 * i);
                        c = new Color(Math.min(c.getRed() + 8, 255), Math.min(c.getGreen() + 8, 255), Math.min(c.getBlue() + 8, 255));
                    }

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
