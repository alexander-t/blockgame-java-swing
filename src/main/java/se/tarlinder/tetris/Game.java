package se.tarlinder.tetris;

public class Game {
    public int[][] board = new int[4][5];
    public int bx; // Block X
    public int by; // Block Y

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
    }

    public void addBlock() {
        bx = 2;
        by = 0;
        if (board[by][bx] != 0) {
            throw new IllegalStateException("You lost!");
        } else {
            board[by][bx] = 1;
        }
    }

    public void tick() {
        if (board[by + 1][bx] == 0) {
            board[by++][bx] = 0;
            board[by][bx] = 1;
        } else {
            addBlock();
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
        Game g  = new Game();
        g.addBlock();
        g.tick();
        g.tick();
        g.tick();
        g.tick();
        g.tick();
        System.err.println(g);
    }
}