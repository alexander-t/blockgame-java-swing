package se.tarlinder.tetris;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
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
                    g.setColor(Color.yellow);
                    drawingBlock = true;
                } else if (board[y][x] == 11) {
                    g.setColor(Color.red);
                    drawingBlock = true;
                } else if (board[y][x] == 12) {
                    g.setColor(Color.green);
                    drawingBlock = true;
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

                if (drawingBlock) {
                    g.setColor(Color.white);
                    g.drawRect(x * squareSize, y * squareSize, squareSize - 1, squareSize - 1);
                }
            }
        }
    }
}
