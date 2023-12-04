package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean upPressed, leftPressed, downPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int key_press = e.getKeyCode();

        if (key_press == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (key_press == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (key_press == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (key_press == KeyEvent.VK_D) {
            rightPressed = true;
        }
        // TODO: Add more keys like Up, Left, Down, Right, Spacebar etc.
    }

    // Useless but needed because... Java.
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
