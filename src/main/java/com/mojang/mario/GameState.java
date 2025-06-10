package com.mojang.mario;

import com.mojang.sonar.SonarSoundEngine;

public class GameState { // Classe responsável pelos estados do jogo

    private Scene scene;
    private Boolean focus;
    private SonarSoundEngine sound;
    private Boolean isPaused = false;
    private Boolean easyMode = false;

    public GameState(boolean easyMode) {
        this.easyMode = easyMode;
    }

    public Scene getScene() {
        return scene;
    }

    public void tick(float alpha) {
        if (scene != null) {
            scene.tick();
            sound.clientTick(alpha);
        }
    }

    public void render(java.awt.Graphics g, float alpha) {
        if (scene != null) {
            scene.render(g, alpha);
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.setSound(sound);
        scene.init();
    }

    public Boolean getPaused() {
        return isPaused;
    }

    public void setPaused(Boolean paused) {
        isPaused = paused;
    }

    public Boolean getFocus() {
        return focus;
    }
    public void setFocus(Boolean focus) {
        this.focus = focus;
    }

    public SonarSoundEngine getSound() {
        return sound;
    }

    public void setSound(SonarSoundEngine sound) {
        this.sound = sound;
    }



    public Boolean getEasyMode() {
        return easyMode;
    }
}
