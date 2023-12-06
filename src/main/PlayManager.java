package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayManager {

    // Variables for the different Segments of the Game such as the "Play" Window and the "Next" Window etc.
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    // Miscellaneous
    public static int dropInterval = 240; // Mino drops every 240 Frames (1 Second).
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    final int WIDTH = 360;
    final int HEIGHT = 600;
    // Variables for the Mino
    final int MINO_START_X;
    final int MINO_START_Y;
    final int NEXT_MINO_START_X;
    final int NEXT_MINO_START_Y;
    Mino currentMino;
    Mino nextMino;

    public PlayManager() {

        // Main Play Area Frame
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Starting Position for Tetrominos
        MINO_START_X = left_x + WIDTH / 2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // Starting Position for next Tetrominos
        NEXT_MINO_START_X = right_x + 175;
        NEXT_MINO_START_Y = top_y + 500;

        // Set the starting Tetromino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXT_MINO_START_X, NEXT_MINO_START_Y);
    }

    private Mino pickMino() {
        // Get a random Tetromino.
        Mino mino = null;

        int i = new Random().nextInt(7);
        switch (i) {
            case 0 -> mino = new Mino_L();
            case 1 -> mino = new Mino_J();
            case 2 -> mino = new Mino_O();
            case 3 -> mino = new Mino_I();
            case 4 -> mino = new Mino_T();
            case 5 -> mino = new Mino_Z();
            case 6 -> mino = new Mino_S();
        }

        return mino;
    }

    public void update() {
        // Check if currentMino is active.
        if (currentMino.active) {
            currentMino.update();
        } else {
            // Move all blocks from the inactive Mino to the staticBlocks Array.
            staticBlocks.addAll(Arrays.asList(currentMino.b).subList(0, 4));

            currentMino.collisionTimeMargin = false;

            // Replace currentMino with nextMino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXT_MINO_START_X, NEXT_MINO_START_Y);

            // Check if a line or multiple lines can be deleted after a Mino became inactive.
            checkDeletion();
        }
    }

    private void checkDeletion() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while (x < right_x && y < bottom_y) {

            for (Block staticBlock : staticBlocks) {
                if (staticBlock.x == x && staticBlock.y == y) {
                    blockCount++;
                }
            }

            x += Block.SIZE;

            if (x == right_x) {

                // If the blockCount reaches 12 which is the maximum number of Blocks that can be in 1 Row.
                // Then delete that row.
                if (blockCount == 12) {
                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        // Remove all Blocks in current y-Line
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    // If a line has been deleted, then all Blocks above it have to be shifted down.
                    for (Block staticBlock : staticBlocks) {
                        if (staticBlock.y < y) {
                            staticBlock.y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
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

        // Render nextMino
        nextMino.draw(g2);

        // Draw static blocks.
        for (Block staticBlock : staticBlocks) {
            staticBlock.draw(g2);
        }

        // Render Pause-Screen
        g2.setColor(Color.YELLOW);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (KeyHandler.pausePressed) {
            x = left_x + 85;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
    }
}
