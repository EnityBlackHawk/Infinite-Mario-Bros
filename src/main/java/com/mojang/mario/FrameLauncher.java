package com.mojang.mario;

import java.awt.*;
import javax.swing.*;

public class FrameLauncher
{
    public static void main(String[] args)
    {
        MarioComponent mario = new MarioComponent(640, 480);
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        JFrame frame = new JFrame("Infinite Mario Bros.");
        frame.setUndecorated(true);
        frame.setContentPane(mario);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        device.setFullScreenWindow(frame);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);
        
        frame.setVisible(true);
        
        mario.start();
//        frame.addKeyListener(mario);
//        frame.addFocusListener(mario);
    }
}