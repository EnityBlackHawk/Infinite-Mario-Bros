package com.mojang.mario;

import java.awt.Color;
import java.awt.Graphics;

import com.mojang.mario.sprites.Mario;

public class LoseScene extends Scene
{
    private MarioComponent component;
    private int tick;
    private String scrollMessage = "Game over!";
    
    public LoseScene(MarioComponent component)
    {
        this.component = component;
    }

    public void init()
    {
    }

    public void render(Graphics g, float alpha)
    {
        g.setColor(Color.decode("#a07070"));
        g.fillRect(0, 0, 320, 240);
        int f = tick/3%10;
        if (f>=6) f = 10-f;
        g.drawImage(Art.gameOver[f][0], 160-48, 100-32, null);
        drawString(g, scrollMessage, 160-scrollMessage.length()*4, 160, 0);
    }

    private void drawString(Graphics g, String text, int x, int y, int c)
    {
        Utils.drawString(g, text, x, y, c);
    }

    private boolean wasDown = true;
    public void tick()
    {
        tick++;
        if (!wasDown && keys[Mario.KEY_JUMP])
        {
            component.toTitle();
        }
        if (keys[Mario.KEY_JUMP])
        {
            wasDown = false;
        }
    }

    public float getX(float alpha)
    {
        return 0;
    }

    public float getY(float alpha)
    {
        return 0;
    }
}