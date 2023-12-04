package mino;

import java.awt.*;

public class Block extends Rectangle {
    public static final int SIZE = 30; // 30x30 Block
    public int x;
    public int y;
    public Color c;

    public Block(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.fillRect(x, y, SIZE, SIZE);
    }
}
