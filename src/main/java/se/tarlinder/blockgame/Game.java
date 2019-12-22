package se.tarlinder.blockgame;

import se.tarlinder.blockgame.ui.Canvas;
import se.tarlinder.blockgame.ui.ScorePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

public class Game extends JFrame {

    public static final int EDGE_BLOCK = 2;
    public static final Font FONT = loadFont();

    private Canvas canvas;
    private ScorePanel scorePanel;

    private int totalRowsCollapsed;
    private int level = 0;

    int[][] board;
    private boolean lost = false;
    private Block block;

    public Game(int width, int height) {
        board = new int[height + 1][width + 2]; // Add sides and a bottom row that block
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (y < board.length - 1) {
                    board[y][0] = board[y][board[0].length - 1] = EDGE_BLOCK;
                } else {
                    board[y][x] = EDGE_BLOCK;
                }
            }
        }

        canvas = new Canvas(40, board);
        scorePanel = new ScorePanel(canvas.getPreferredSize());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!lost) {
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
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("West", canvas);
        getContentPane().add("East", scorePanel);
        setResizable(false);
        setUndecorated(true);
        pack();
        // Non-obvious way of centering the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addBlock() {
        block = new Block(getCenterX(), 0, board);
        lost = !block.canDrop();
    }

    private void checkFullRow() {
        int y = board.length - 2;
        int rowsCollapsed = 0;
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
                rowsCollapsed++;
            } else {
                y--;
            }
        } while (y > 0);

        if (rowsCollapsed > 0) {
            updateState(rowsCollapsed);
        }
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

    private void moveBlockLeft() {
        if (block.canMoveLeft()) {
            block.moveLeft();
            canvas.repaint();
        }
    }

    private void moveBlockRight() {
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

    private void updateState(int rowsCollapsed) {
        totalRowsCollapsed += rowsCollapsed;

        if (totalRowsCollapsed % 10 == 0) {
            scorePanel.updateLevel(++level);
        }

        scorePanel.updateRowsCollapsed(totalRowsCollapsed);
        scorePanel.repaint();
    }

    private int getCenterX() {
        return board[0].length / 2 - 1;
    }

    @Override
    public void paint(Graphics g) {
        if (lost) {
            g.setColor(Color.black);
            g.fillRoundRect(50, 400, getWidth() - 100, 100, 20, 20);

            g.setFont(FONT);
            int textWidth = g.getFontMetrics().stringWidth("Game Over!");
            g.setColor(Color.white);
            g.drawString("Game Over!", getWidth() / 2 - textWidth / 2, 465);
        } else {
            super.paint(g);
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

    private static Font loadFont() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(Game.class.getClassLoader().getResource("Firstborn-Yzz78.ttf").getFile()))
                    .deriveFont(96f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load font", e);
        }
    }


    public static void main(String[] args) {
        Game game = new Game(10, 24);
        game.addBlock();
        new Thread(() -> {
            try {
                while (!game.lost) {
                    game.tick();
                    Thread.sleep(1000 - Math.max(50, game.level * 50));
                }
                game.repaint();
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {

            }
        }).start();
    }
}