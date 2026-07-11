package com.balikclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && target != null) {
            double distance = client.player.getEyePos().distanceTo(target.getPos());
            String formattedDistance = String.format("%.2f", distance);
            client.player.sendMessage(Text.of("§b[Balık] §fHedefe §e" + formattedDistance + " §fbloktan vurdun!"), true);
        }
    }
}
