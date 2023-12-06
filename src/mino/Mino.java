package mino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public class Mino {
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    public boolean active = true;
    public boolean collisionTimeMargin;
    int autoDropCounter = 0;
    int direction = 1;
    boolean leftCollision, rightCollision, bottomCollision;
    int collisionTimeCounter = 0;

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {

    }

    public void updateXY(int direction) {

        checkRotationCollision();
        if (!(leftCollision || rightCollision || bottomCollision)) {
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }

    public void getDirection1() {

    }

    public void getDirection2() {

    }

    public void getDirection3() {

    }

    public void getDirection4() {

    }

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // Check static block collision
        checkStaticBlockCollision();

        // Check Collision

        // Left Wall
        for (Block block : b) {
            if (block.x == PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }

        // Right Wall
        for (Block block : b) {
            if (block.x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }

        // Bottom Floor
        for (Block block : b) {
            if (block.y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // Check static block collision
        checkStaticBlockCollision();

        // Check Collision

        // Left Wall
        for (Block block : tempB) {
            if (block.x < PlayManager.left_x) {
                leftCollision = true;
                break;
            }
        }

        // Right Wall
        for (Block block : tempB) {
            if (block.x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
                break;
            }
        }

        // Bottom Floor
        for (Block block : tempB) {
            if (block.y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
                break;
            }
        }
    }

    public void checkStaticBlockCollision() {
        for (Block staticBlock : PlayManager.staticBlocks) {
            int targetX = staticBlock.x;
            int targetY = staticBlock.y;
            // Check below
            for (Block block : b) {
                if (block.y + Block.SIZE == targetY && block.x == targetX) {
                    bottomCollision = true;
                    break;
                }
            }
            // Check left
            for (Block block : b) {
                if (block.x - Block.SIZE == targetX && block.y == targetY) {
                    leftCollision = true;
                    break;
                }
            }
            // Check right
            for (Block block : b) {
                if (block.x + Block.SIZE == targetX && block.y == targetY) {
                    rightCollision = true;
                    break;
                }
            }
        }
    }

    public void update() {

        if (collisionTimeMargin) {
            collisionTimer();
        }

        // Movement for the Mino
        if (KeyHandler.upPressed) {
            switch (direction) {
                case 1 -> getDirection2();
                case 2 -> getDirection3();
                case 3 -> getDirection4();
                case 4 -> getDirection1();
            }
            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if (KeyHandler.leftPressed) {
            if (!leftCollision) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.downPressed) {
            if (!bottomCollision) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.rightPressed) {
            if (!rightCollision) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) {
            collisionTimeMargin = true;
        } else {
            autoDropCounter++; // Increase the counter every Frame until it reaches 240 (FPS).
            if (autoDropCounter >= PlayManager.dropInterval) {
                // Every 240 Frames the Mino goes down by 1. Block (30 Pixel).
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    private void collisionTimer() {
        collisionTimeCounter++;

        // Wait FPS * 0.5 (120FPS)
        if (collisionTimeCounter == 120) {
            collisionTimeCounter = 0;
            // Check if the piece still touches something.
            checkMovementCollision();
            if (bottomCollision) {
                active = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // The Margin is used to make it easier to distinguish between the block segments.
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g2.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g2.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g2.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
    }
}
