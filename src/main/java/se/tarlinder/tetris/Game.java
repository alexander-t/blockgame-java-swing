package se.tarlinder.tetris;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Game extends JFrame {
    private Canvas canvas;
    int[][] board;
    private boolean lost = false;
    private Block block;

    public Game(int width, int height) {
        board = new int[height + 1][width + 2]; // Add sides and a bottom row that block
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
                    moveBlockLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    rotateBlock();
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(canvas);
        pack();
        setVisible(true);
    }

    public void addBlock() {
        block = new Block(getCenterX(), 0, board);
        lost = !block.canDrop();
    }

    private void checkFullRow() {
        int y = board.length - 2;
        do {
            boolean rowFull = true;
            for (int x = 1; x < board[0].length - 1; x++) {
                if (board[y][x] == 0) {
                    rowFull = false;
                    break;
                }
            }

            if (rowFull) {
                collapse(y);
                board[0][0] = board[0][board[0].length - 1] = 2; // Reset edges
            } else {
                y--;
            }
        } while (y > 0);
    }

    public void collapse(int row) {
        for (int y = Math.max(row - 1, 0); y >= 0; y--) {
            System.arraycopy(board[y], 0, board[y + 1], 0, board[0].length);
        }
        Arrays.fill(board[0], 0);
    }

    public void tick() {
        if (block.canDrop()) {
            block.drop();
        } else {
            checkFullRow();
            addBlock();
        }
        canvas.repaint();
    }

    public void moveBlockLeft() {
        if (block.canMoveLeft()) {
            block.moveLeft();
            canvas.repaint();
        }
    }

    public void moveBlockRight() {
        if (block.canMoveRight()) {
            block.moveRight();
            canvas.repaint();
        }
    }

    private void rotateBlock() {
        if (block.canRotate()) {
            block.rotate();
            canvas.repaint();
        }
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
        Game g = new Game(10, 24);
        g.addBlock();
        new Thread(() -> {
            try {
                while (!g.lost) {
                    g.tick();
                    Thread.sleep(250);
                }
                System.exit(0);
            } catch (InterruptedException e) {

            }
        }).start();
    }

    private int getCenterX() {
        return board[0].length / 2 - 1;
    }
}