package com.mojang.mario;

import com.mojang.mario.sprites.Mario;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInputHandler implements KeyListener, FocusListener {

    private GameState gameState;

    public GameInputHandler(GameState gameState){
        this.gameState = gameState;
    }

    private void toggleKey(int keyCode, boolean isPressed)
    {
        if (keyCode == KeyEvent.VK_LEFT)
        {
            gameState.getScene().toggleKey(Mario.KEY_LEFT, isPressed);
        }
        if (keyCode == KeyEvent.VK_RIGHT)
        {
            gameState.getScene().toggleKey(Mario.KEY_RIGHT, isPressed);
        }
        if (keyCode == KeyEvent.VK_DOWN)
        {
            gameState.getScene().toggleKey(Mario.KEY_DOWN, isPressed);
        }
        if (keyCode == KeyEvent.VK_UP)
        {
            gameState.getScene().toggleKey(Mario.KEY_UP, isPressed);
        }
        if (keyCode == KeyEvent.VK_A)
        {
            gameState.getScene().toggleKey(Mario.KEY_SPEED, isPressed);
        }
        if (keyCode == KeyEvent.VK_S)
        {
            gameState.getScene().toggleKey(Mario.KEY_JUMP, isPressed);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        gameState.setFocus(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
        gameState.setFocus(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }
}
