package com.mojang.mario;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import com.mojang.mario.sprites.*;
import com.mojang.sonar.FakeSoundEngine;
import com.mojang.sonar.SonarSoundEngine;


public class MarioComponent extends JComponent implements Runnable, KeyListener, FocusListener
{
    private static final long serialVersionUID = 739318775993206607L;
    public static final int TICKS_PER_SECOND = 24;

    private boolean running = false;
    private int width, height;
    private GraphicsConfiguration graphicsConfiguration;
    private Scene scene;
    private SonarSoundEngine sound;
    @SuppressWarnings("unused")
	private boolean focused = false;
    private boolean useScale2x = false;
    private MapScene mapScene;
    int delay;

    private static final int DEFAULT_WIDTH = 320;
    private static final int DEFAULT_HEIGHT = 240;
    private static final int MAX_SOUND_CHANNELS = 64;
    private static final int BLINK_INTERVAL = 4;
    private static final String CLICK_TO_PLAY_MESSAGE = "CLICK TO PLAY";
    private static final int MESSAGE_X_OFFSET = 160 - CLICK_TO_PLAY_MESSAGE.length() * 4;
    private static final int MESSAGE_Y_OFFSET = 110;
    private static final int SHADOW_COLOR = 0;
    private static final int TEXT_COLOR = 7;

    private Scale2x scale2x = new Scale2x(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public MarioComponent(int width, int height)
    {
        this.setFocusable(true);
        this.setEnabled(true);
        this.width = width;
        this.height = height;

        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        try
        {
            sound = new SonarSoundEngine(MAX_SOUND_CHANNELS);
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
            sound = new FakeSoundEngine();
        }

        setFocusable(true);
    }

    private void toggleKey(int keyCode, boolean isPressed)
    {
        if (keyCode == KeyEvent.VK_LEFT)
        {
            scene.toggleKey(Mario.KEY_LEFT, isPressed);
        }
        if (keyCode == KeyEvent.VK_RIGHT)
        {
            scene.toggleKey(Mario.KEY_RIGHT, isPressed);
        }
        if (keyCode == KeyEvent.VK_DOWN)
        {
            scene.toggleKey(Mario.KEY_DOWN, isPressed);
        }
        if (keyCode == KeyEvent.VK_UP)
        {
            scene.toggleKey(Mario.KEY_UP, isPressed);
        }
        if (keyCode == KeyEvent.VK_A)
        {
            scene.toggleKey(Mario.KEY_SPEED, isPressed);
        }
        if (keyCode == KeyEvent.VK_S)
        {
            scene.toggleKey(Mario.KEY_JUMP, isPressed);
        }
        if (isPressed && keyCode == KeyEvent.VK_F1)
        {
            useScale2x = !useScale2x;
        }
    }

    public void paint(Graphics g)
    {
    }

    public void update(Graphics g)
    {
    }

    public void start()
    {
        if (!running)
        {
            running = true;
            new Thread(this, "Game Thread").start();
        }
    }

    public void stop()
    {
        Art.stopMusic();
        running = false;
    }

    public void initializeGame()
    {
        graphicsConfiguration = getGraphicsConfiguration();

        mapScene = new MapScene(graphicsConfiguration, this, new Random().nextLong());
        scene = mapScene;
        scene.setSound(sound);

        Art.init(graphicsConfiguration, sound);

        addKeyListener(this);
        addFocusListener(this);

        toTitle();
        adjustFPS();
    }

    public void run()
    {
        initializeGame();
        gameLoop();
        Art.stopMusic();
    }

    private void gameLoop() {
        VolatileImage image = createVolatileImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Graphics g = getGraphics();
        Graphics og = image.getGraphics();

        int renderedFrames = 0;

        double time = System.nanoTime() / 1000000000.0;
        double now = time;
        long tm = System.currentTimeMillis();
        long lTick = tm;


        while (running)
        {
            scene.tick();

            float alpha = (float) (System.currentTimeMillis() - lTick);
            sound.clientTick(alpha);

            //og.setColor(Color.WHITE);
            renderScene(og, lTick, g, image);

            if (delay > 0) {
                try {
                    tm += delay;
                    Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    break;
                }
            }

            renderedFrames++;
        }
    }

    private void renderScene(Graphics og, long lTick, Graphics g, VolatileImage image) {
        float alpha;
        og.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        alpha = 0;
        scene.render(og, alpha);

        if (!this.hasFocus() && lTick / BLINK_INTERVAL % 2 == 0)
        {
            String msg = CLICK_TO_PLAY_MESSAGE;

            drawString(og, msg, MESSAGE_X_OFFSET + 1, MESSAGE_Y_OFFSET + 1, SHADOW_COLOR);
            drawString(og, msg, MESSAGE_X_OFFSET, MESSAGE_Y_OFFSET, TEXT_COLOR);
        }
        og.setColor(Color.BLACK);
            /*          drawString(og, "FPS: " + fps, 5, 5, 0);
             drawString(og, "FPS: " + fps, 4, 4, 7);*/

        if (width != DEFAULT_WIDTH || height != DEFAULT_HEIGHT)
        {
            if (useScale2x)
            {
                g.drawImage(scale2x.scale(image), 0, 0, null);
            }
            else
            {
                g.drawImage(image, 0, 0, 2 * DEFAULT_WIDTH, 2 * DEFAULT_HEIGHT, null);
            }
        }
        else
        {
            g.drawImage(image, 0, 0, null);
        }
    }

    private void drawString(Graphics g, String text, int x, int y, int c)
    {
        Utils.drawString(g,text,x,y,c);
    }

    public void keyPressed(KeyEvent arg0)
    {
        toggleKey(arg0.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent arg0)
    {
        toggleKey(arg0.getKeyCode(), false);
    }

    public void startLevel(long seed, int difficulty, int type)
    {
        scene = new LevelScene(graphicsConfiguration, this, seed, difficulty, type);
        scene.setSound(sound);
        scene.init();
    }

    public void levelFailed()
    {
        scene = mapScene;
        mapScene.startMusic();
        Mario.lives--;
        if (Mario.lives == 0)
        {
            lose();
        }
    }

    public void keyTyped(KeyEvent arg0)
    {
    }

    public void focusGained(FocusEvent arg0)
    {
        focused = true;
    }

    public void focusLost(FocusEvent arg0)
    {
        focused = false;
    }

    public void levelWon()
    {
        scene = mapScene;
        mapScene.startMusic();
        mapScene.levelWon();
    }
    
    public void win()
    {
        scene = new WinScene(this);
        scene.setSound(sound);
        scene.init();
    }
    
    public void toTitle()
    {
        Mario.resetStatic();
        scene = new TitleScene(this, graphicsConfiguration);
        scene.setSound(sound);
        scene.init();
    }
    
    public void lose()
    {
        scene = new LoseScene(this);
        scene.setSound(sound);
        scene.init();
    }

    public void startGame()
    {
        scene = mapScene;
        mapScene.startMusic();
        mapScene.init();
   }
    
    public void adjustFPS() {
        int fps = 24;
        delay = (fps > 0) ? (fps >= 100) ? 0 : (1000 / fps) : 100;
//        System.out.println("Delay: " + delay);
    }
}