package io.github.theofficialseri.permanentsnow.mixin;

import io.github.theofficialseri.permanentsnow.config.Configs;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public abstract class MixinGui {
    @Redirect(method = "renderCameraOverlays(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At(value = "INVOKE", target
            = "Lnet/minecraft/client/player/LocalPlayer;getTicksFrozen ()I"))
    private static int getTicksFrozen(LocalPlayer instance) {
        int ticksFrozen = instance.getTicksFrozen();
        return Configs.powderSnowOverlayOpacity > 0 && ticksFrozen <= 0 ? 1 : ticksFrozen;
    }

    @Redirect(method = "renderCameraOverlays(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At(value = "INVOKE", target
            = "net/minecraft/client/player/LocalPlayer.getPercentFrozen ()F"))
    private static float getPercentFrozen(LocalPlayer instance) {
        return Configs.powderSnowOverlayOpacity > 0 && instance.getTicksFrozen() <= 0 ? Configs.powderSnowOverlayOpacity : instance.getPercentFrozen();
    }
}
