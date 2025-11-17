package io.github.theofficialseri.permanentsnow.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.theofficialseri.permanentsnow.config.Configs;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends MixinLevel {
    @Override
    public float getRainLevel(float f, Operation<Float> original) {
        return Configs.overridePrecipitationLevel ? Configs.precipitationLevel : super.getRainLevel(f, original);
    }
}
