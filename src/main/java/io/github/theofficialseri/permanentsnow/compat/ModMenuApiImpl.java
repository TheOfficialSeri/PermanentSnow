package io.github.theofficialseri.permanentsnow.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.theofficialseri.permanentsnow.config.Configs;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Configs::generateConfigScreen;
    }
}
