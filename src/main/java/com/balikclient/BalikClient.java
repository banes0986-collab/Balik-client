package com.balikclient;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BalikClient implements ModInitializer {
    public static KeyBinding configMenuKey;

    @Override
    public void onInitialize() {
        configMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.balikclient.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.balikclient"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (configMenuKey.wasPressed() && client.currentScreen == null) {
                client.setScreen(new BalikClientMenuScreen());
            }
        });
    }
}
