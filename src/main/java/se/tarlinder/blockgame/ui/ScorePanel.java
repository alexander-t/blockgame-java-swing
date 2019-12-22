package se.tarlinder.blockgame.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ScorePanel extends JPanel {

    private int collapsedRows;
    private int level;
    private int centerX;
    private Font valueFont;
    private Font labelFont;

    public ScorePanel(Dimension dimension) {
        setPreferredSize(dimension);
        centerX = dimension.width / 2;
        valueFont = loadFont();
        labelFont = valueFont.deriveFont(24f);
    }

    public void updateRowsCollapsed(int collapsedRows) {
        this.collapsedRows = collapsedRows;
    }

    public void updateLevel(int level) {
        this.level = level;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        drawBox(getHeight() - 240, g, "Level", "" + level);
        drawBox(getHeight() - 120, g, "Rows", "" + collapsedRows);
    }

    private void drawBox(int y, Graphics g, String label, String value) {
        // Yes, lots of hardcoding here
        int boxWidth = (int) (0.8 * getWidth());
        int boxHeight = 100;
        int x = (getWidth() - boxWidth) / 2;
        g.setFont(valueFont);
        int textWidth = g.getFontMetrics().stringWidth(value);
        g.setColor(Color.black);
        g.fillRoundRect(x, y, boxWidth, boxHeight, 15, 15);
        g.setColor(Color.white);
        g.drawRoundRect(x + 3, y + 3, boxWidth - 6, boxHeight - 6, 14, 14);
        g.setFont(labelFont);
        g.drawString(label, x + 10, y + 20);
        g.setFont(valueFont);
        g.setColor(Color.green);
        g.drawString(value, centerX - textWidth / 2, y + 70);
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
