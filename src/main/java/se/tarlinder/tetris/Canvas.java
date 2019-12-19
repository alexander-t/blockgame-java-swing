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
    /*    for (int x = 0; x < columns; x++) {
            g.drawLine(x * squareSize, 0, x * squareSize, rows * squareSize);
        }

        for (int y = 0; y < rows; y++) {
            g.drawLine(0, y * squareSize, columns * squareSize, y * squareSize);
        }*/
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (board[y][x] == 2) {
                    g.setColor(Color.black);
                } else if (board[y][x] == 1) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(x * squareSize, y * squareSize, (x + 1) * squareSize, (y + 1) * squareSize);
            }
        }
    }
}
