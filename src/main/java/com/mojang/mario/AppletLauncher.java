package com.mojang.mario;

import javax.swing.JApplet;
import java.util.Map;


public class AppletLauncher extends JApplet
{
    private static final long serialVersionUID = -2238077255106243788L;

    private MarioComponent mario;
    private boolean started = false;

    public void init()
    {
    	this.setSize(640, 480);
    }

    public void start()
    {
        if (!started)
        {
            Map<Integer, Integer> binding;
            try {
                binding = KeyBindingImporter.importKeyBindings("keys.txt");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            started = true;
            mario = new MarioComponent(getWidth(), getHeight(), binding, false);
            setContentPane(mario);
            setFocusable(false);
            mario.setFocusCycleRoot(true);

            mario.start();
//            addKeyListener(mario);
//            addFocusListener(mario);
        }
    }

    public void stop()
    {
        if (started)
        {
            started = false;
            //removeKeyListener(mario);
            mario.stop();
            //removeFocusListener(mario);
        }
    }
}