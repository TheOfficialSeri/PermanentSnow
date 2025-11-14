package io.github.theofficialseri.permanentsnow.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ConfigCategory.Builder;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import io.github.theofficialseri.permanentsnow.PermanentSnow;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome.Precipitation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class Configs {
    private static final ConfigClassHandler<Configs> HANDLER = ConfigClassHandler.createBuilder(Configs.class)
            .id(ResourceLocation.fromNamespaceAndPath(PermanentSnow.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("permanentsnow.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "Map any type of precipitation to another type of precipitation")
    public static Map<Precipitation, Precipitation> precipitationToPrecipitation = new EnumMap<>(Map.of(Precipitation.RAIN, Precipitation.SNOW));

    public static void initialize() {
        HANDLER.load();
    }

    public static Screen generateConfigScreen(Screen screen) {
        Builder precipitationBuilder = ConfigCategory.createBuilder().name(Component.translatable("config.permanentsnow.precipitation"));

        Arrays.stream(Precipitation.values())
                .forEach(precipitation -> precipitationBuilder.option(Option.<Precipitation>createBuilder()
                        .name(Component.translatable("config.permanentsnow.precipitation." + precipitation.getSerializedName()))
                        .binding(
                                precipitation,
                                () -> precipitationToPrecipitation.getOrDefault(precipitation, precipitation),
                                value -> precipitationToPrecipitation.put(precipitation, value)
                        )
                        .controller(option -> EnumControllerBuilder.create(option)
                                .enumClass(Precipitation.class)
                                .formatValue(value -> Component.translatable("config.permanentsnow.precipitation." + value.getSerializedName())))
                        .build()));

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.permanentsnow.title"))
                .category(precipitationBuilder.build())
                .save(HANDLER::save)
                .build()
                .generateScreen(screen);
    }
}
