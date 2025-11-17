package io.github.theofficialseri.permanentsnow.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public abstract class MixinLevel {
    @WrapMethod(method = "getRainLevel")
    public float getRainLevel(float f, Operation<Float> original) {
        return original.call(f);
    }
}
