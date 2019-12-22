package se.tarlinder.blockgame.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ScorePanel extends JPanel {

    private int collapsedRows;
    private int centerX;

    public ScorePanel(Dimension dimension) {
        setPreferredSize(dimension);
        centerX = dimension.width / 2;
        setFont(loadFont());
    }

    public void updateRowsCollapsed(int collapsedRows) {
        this.collapsedRows = collapsedRows;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        drawBox(g, "" + collapsedRows);
    }

    private void drawBox(Graphics g, String s) {
        // Yes, lots of hardcoding here
        int boxWidth = (int) (0.8 * getWidth());
        int boxHeight = 100;
        int y = getHeight() - boxHeight - 20;
        int x = (getWidth() - boxWidth) / 2;
        g.setColor(Color.black);
        g.fillRoundRect(x, y, boxWidth, boxHeight, 15, 15);
        g.setColor(Color.white);
        g.drawRoundRect(x + 3, y + 3, boxWidth - 6, boxHeight - 6, 14, 14);
        g.drawString(s, centerX - 15, y + 70);
    }


    private Font loadFont() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(getClass().getClassLoader().getResource("Firstborn-Yzz78.ttf").getFile()))
                    .deriveFont(96f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load font", e);
        }
    }
}
