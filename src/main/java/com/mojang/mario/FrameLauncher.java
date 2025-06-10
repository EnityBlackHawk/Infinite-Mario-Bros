package com.mojang.mario;

import java.awt.*;
import javax.swing.*;

public class FrameLauncher
{
    public static void main(String[] args) throws Exception {

        var binding = KeyBindingImporter.importKeyBindings("keys.txt"); // Importa o arquivo que contém a função de conversão das teclas

        int width = 640;
        int height = 480;
        boolean fullscreen = false;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        for(String arg : args) {
            if(arg.equals("--fullscreen")) {
                width = screenSize.width;
                height = screenSize.height;
                fullscreen = true;
            }
        }

        MarioComponent mario = new MarioComponent(width, height, binding); // Passa as configurações de teclas para o MarioComponent(Responsável pela coordenação das cenas, funções do jogo, etc)
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        JFrame frame = new JFrame("Infinite Mario Bros.");
        frame.setUndecorated(fullscreen);
        frame.setContentPane(mario);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (fullscreen) {
            device.setFullScreenWindow(frame);
        }

        frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);
        
        frame.setVisible(true);
        
        mario.start();
//        frame.addKeyListener(mario);
//        frame.addFocusListener(mario);
    }
}