package io.github.theofficialseri.permanentsnow.mixin;

import io.github.theofficialseri.permanentsnow.config.Configs;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class MixinLevel {
    @Inject(at = @At("HEAD"), method = "getRainLevel", cancellable = true)
    private void getRainLevel(float f, CallbackInfoReturnable<Float> cir) {
        if (Configs.overridePrecipitationLevel) {
            cir.setReturnValue(Configs.precipitationLevel);
        }
    }
}
