package com.mojang.mario;

import com.mojang.mario.sprites.Mario;

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



    private void toggleKey(int keyCode, boolean isPressed) // Função responsável por verificar a ação da tecla e passar para a cena atual.
    {

        if (keyBindings != null && keyBindings.containsKey(keyCode)) {

            final int action = keyBindings.get(keyCode);
            if(action == Mario.KEY_PAUSE) {
                if(isPressed) {
                    gameState.setPaused(!gameState.getPaused());
                }
                return;
            }

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
