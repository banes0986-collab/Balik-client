package com.balikclient;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class BalikClientMenuScreen extends Screen {
    public static boolean showFps = true;
    public static boolean showMs = true;
    public static boolean fullBright = false;

    public BalikClientMenuScreen() {
        super(Text.of("Balık Client Menü"));
    }

    @Override
    protected void init() {
        int buttonWidth = 120;
        int buttonHeight = 20;

        // FPS Aç/Kapat Butonu
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 130, 60, buttonWidth, buttonHeight, 
            Text.of("FPS: " + (showFps ? "AÇIK" : "KAPALI")), button -> {
                showFps = !showFps;
                button.setMessage(Text.of("FPS: " + (showFps ? "AÇIK" : "KAPALI")));
        }));

        // Full Bright Aç/Kapat Butonu
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 10, 60, buttonWidth, buttonHeight, 
            Text.of("Full Bright: " + (fullBright ? "AÇIK" : "KAPALI")), button -> {
                fullBright = !fullBright;
                button.setMessage(Text.of("Full Bright: " + (fullBright ? "AÇIK" : "KAPALI")));
                if (this.client != null) {
                    this.client.options.gamma = fullBright ? 12.0f : 1.0f;
                }
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, "🐟 BALIK CLIENT MENÜ (Right Shift) 🐟", this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
