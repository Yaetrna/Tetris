package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean upPressed, leftPressed, downPressed, rightPressed, pausePressed, dropPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int key_press = e.getKeyCode();

        if (key_press == KeyEvent.VK_W || key_press == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (key_press == KeyEvent.VK_A || key_press == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (key_press == KeyEvent.VK_S || key_press == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (key_press == KeyEvent.VK_D || key_press == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (key_press == KeyEvent.VK_ESCAPE) {
            pausePressed = !pausePressed;
        }
        if (key_press == KeyEvent.VK_SPACE) {
            dropPressed = true;
        }
    }

    // Useless but needed because... Java.
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
