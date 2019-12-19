package se.tarlinder.tetris;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame {
    private Canvas canvas;
    public int[][] board = new int[10][7];
    public int bx; // Block X
    public int by; // Block Y
    private boolean lost = false;

    public Game() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (y < board.length - 1) {
                    board[y][0] = board[y][board[0].length - 1] = 2;
                } else {
                    board[y][x] = 2;
                }
            }
        }

        canvas = new Canvas(40, board);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tick();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveBlockRight();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    moveBlockLEft();
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(canvas);
        pack();
        setVisible(true);
    }

    public void addBlock() {
        bx = getCenterX();
        by = 0;
        if (board[by][bx] != 0) {
            lost = true;
        } else {
            board[by][bx] = 1;
        }
    }

    public void moveBlockLEft() {
        if (board[by][bx - 1] == 0) {
            board[by][bx--] = 0;
            board[by][bx] = 1;
        }
        canvas.repaint();
    }

    public void moveBlockRight() {
        if (board[by][bx + 1] == 0) {
            board[by][bx++] = 0;
            board[by][bx] = 1;
        }
        canvas.repaint();
    }

    private void checkFullRow() {
        int y = board.length - 2;
        boolean rowFull = true;
        for (int x = 1; x < board[0].length - 1; x++) {
            if (board[y][x] == 0) {
                rowFull = false;
            }
        }

        if (rowFull) {
            for (y = board.length - 3; y > 0; y--) {
                System.arraycopy(board[y], 0, board[y + 1], 0, board[0].length);
            }
        }
    }

    public void tick() {
        checkFullRow();
        if (board[by + 1][bx] == 0) {
            board[by++][bx] = 0;
            board[by][bx] = 1;
        } else {
            addBlock();
        }
        canvas.repaint();
    }

    public String toString() {
        String b = "";
        for (int y = 0; y < board.length; y++) {
            String line = "";
            for (int x = 0; x < board[0].length; x++) {
                switch (board[y][x]) {
                    case 2:
                        line += "+";
                        break;
                    case 1:
                        line += "#";
                        break;
                    default:
                        line += " ";
                }
            }
            line += "\n";
            b += line;
        }
        return b;
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.addBlock();
        new Thread(() -> {
            try {
                while(!g.lost) {
                    g.tick();
                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {

            }
        }).start();
    }

    private int getCenterX() {
        return board[0].length / 2;
    }
}