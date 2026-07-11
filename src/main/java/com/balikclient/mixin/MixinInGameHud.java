package com.balikclient.mixin;

import com.balikclient.BalikClientMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    private static final List<Long> leftClicks = new ArrayList<>();

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.options.debugEnabled) return;
        if (client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;
        int yOffset = 5;

        // 1. FPS GÖSTERGESİ (Hata veren kısım düzeltildi)
        if (BalikClientMenuScreen.showFps) {
            String fpsText = "🐟 FPS: §a" + ((MinecraftClientAccessor) client).getCurrentFps();
            textRenderer.drawWithShadow(matrices, fpsText, 5, yOffset, 0xFFFFFF);
            yOffset += 12;
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
        leftClicks.removeIf(time -> currentTime - time > 1000);
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

        // 5. INVENTORY / ARMOR HUD
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int armorX = screenWidth - 25;
        int armorY = screenHeight - 50;

        int itemIndex = 0;
        for (ItemStack stack : client.player.getArmorItems()) {
            if (!stack.isEmpty()) {
                client.getItemRenderer().renderInGui(stack, armorX, armorY - (itemIndex * 18));
                client.getItemRenderer().renderGuiItemOverlay(textRenderer, stack, armorX, armorY - (itemIndex * 18));
                itemIndex++;
            }
        }

        // 6. AKTİF EFEKTLER
        int effectY = 5;
        for (StatusEffectInstance effect : client.player.getStatusEffects()) {
            String effectName = effect.getEffectType().getName().getString();
            int duration = effect.getDuration() / 20;
            String effectText = "✨ " + effectName + " (" + duration + "s)";
            textRenderer.drawWithShadow(matrices, effectText, screenWidth - textRenderer.getWidth(effectText) - 5, effectY, 0xFFFFFF);
            effectY += 12;
        }
    }
}
