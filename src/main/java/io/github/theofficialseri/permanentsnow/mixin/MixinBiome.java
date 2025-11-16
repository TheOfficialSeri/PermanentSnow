package io.github.theofficialseri.permanentsnow.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.theofficialseri.permanentsnow.config.Configs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Biome.class)
public abstract class MixinBiome {
    @ModifyReturnValue(at = @At("TAIL"), method = "getPrecipitationAt")
    private static Precipitation getPrecipitationAt(Precipitation original) {
        return Configs.overridePrecipitationTypes.getOrDefault(original, original);
    }
}
