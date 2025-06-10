package com.mojang.mario;

import com.mojang.mario.sprites.Mario;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class KeyBindingImporter {

    public static Map<Integer, Integer> importKeyBindings(String filePath) throws Exception {
        Map<Integer, Integer> keyBindings = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || !line.contains("=")) continue;
                String[] parts = line.split("=");
                String action = parts[0].trim();
                String keyName = parts[1].trim();
                // Use reflection to get the VK_* value
                int keyCode = KeyEvent.class.getField(keyName).getInt(null);
                int actionCode = Mario.class.getField("KEY_" + action).getInt(null);
                keyBindings.put(keyCode, actionCode);
            }
        }
        return keyBindings;
    } // Função de conversão dos arquivos para ler as teclas setadas no txt

}
