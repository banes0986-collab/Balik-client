package com.balikclient.mixin;

import com.balikclient.BalikClientMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    // CPS hesaplamak için tıklama zamanlarını tutacak liste
    private static final List<Long> leftClicks = new ArrayList<>();

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Eğer F3 açık veya HUD gizliyse çizdirme yapma
        if (client.options.hudHidden || client.options.debugEnabled) return;
        if (client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;
        int yOffset = 5; // Ekranın en üstünden başla

        // 1. FPS GÖSTERGESİ
        if (BalikClientMenuScreen.showFps) {
            String fpsText = "🐟 FPS: §a" + client.getCurrentFps();
            textRenderer.drawWithShadow(matrices, fpsText, 5, yOffset, 0xFFFFFF);
            yOffset += 12; // Bir alt satıra geç
        }

        // 2. MS (PING) GÖSTERGESİ
        if (BalikClientMenuScreen.showMs) {
            long ping = 0;
            if (client.getNetworkHandler() != null && client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()) != null) {
                ping = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()).getLatency();
            }
            String msText = "⚡ MS: §e" + ping + "ms";
            textRenderer.drawWithShadow(matrices, msText, 5, yOffset, 0xFFFFFF);
            yOffset += 12;
        }

        // 3. CPS GÖSTERGESİ
        long currentTime = System.currentTimeMillis();
        // 1 saniyeden (1000ms) eski tıklamaları listeden temizle
        leftClicks.removeIf(time -> currentTime - time > 1000);
        
        // Oyun içi sol tık algılama (CPS için)
        if (client.options.attackKey.wasPressed()) {
            leftClicks.add(currentTime);
        }

        String cpsText = "⚔️ CPS: §c" + leftClicks.size();
        textRenderer.drawWithShadow(matrices, cpsText, 5, yOffset, 0xFFFFFF);
        yOffset += 12;

        // 4. SERVER IP GÖSTERGESİ
        String ip = client.isInSingleplayer() ? "Tek Oyunculu" : (client.getCurrentServerEntry() != null ? client.getCurrentServerEntry().address : "Bilinmiyor");
        String ipText = "🌐 IP: §b" + ip;
        textRenderer.drawWithShadow(matrices, ipText, 5, yOffset, 0xFFFFFF);
    }
}
