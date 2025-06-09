package com.mojang.mario;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class GameInputHandler implements KeyListener, FocusListener {

    private GameState gameState;
    private Map<Integer, Integer> keyBindings;

    public GameInputHandler(GameState gameState, Map<Integer, Integer> keyBindings) {
        this.gameState = gameState;
        this.keyBindings = keyBindings;
    }

    private void toggleKey(int keyCode, boolean isPressed)
    {

        if (keyBindings != null && keyBindings.containsKey(keyCode)) {
            gameState.getScene().toggleKey(keyBindings.get(keyCode), isPressed);
        }
    }

    public void setKeyBindings(Map<Integer, Integer> keyBindings) {
        this.keyBindings = keyBindings;
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
