package main;

import mino.Block;
import mino.Mino;
import mino.Mino_L;

import java.awt.*;

public class PlayManager {

    // Variables for the different Segments of the Game such as the "Play" Window and the "Next" Window etc.
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    final int WIDTH = 360;
    final int HEIGHT = 600;

    // Variables for the Mino
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino currentMino;

    // Miscellaneous
    public static int dropInterval = 240; // Mino drops every 240 Frames (1 Second).

    public PlayManager() {

        // Main Play Area Frame
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Starting Position for Tetrominos
        MINO_START_X = left_x + WIDTH / 2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // Set the starting Tetromino
        currentMino = new Mino_L();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    public void update() {
        currentMino.update();
    }

    public void draw(Graphics2D g2) {
        // Render Main Area
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // Render "NEXT" Area
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        // Render currentMino
        if (currentMino != null) {
            currentMino.draw(g2);
        }
    }
}
