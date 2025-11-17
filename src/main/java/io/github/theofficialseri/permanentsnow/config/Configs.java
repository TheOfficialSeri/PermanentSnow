package io.github.theofficialseri.permanentsnow.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.OptionGroup.Builder;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
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

    @SerialEntry(comment = "Override precipitation types to render as other precipitation types.")
    public static Map<Precipitation, Precipitation> overridePrecipitationTypes = new EnumMap<>(Precipitation.class);

    @SerialEntry(comment = "Override the level of precipitation to render.")
    public static boolean overridePrecipitationLevel = false;

    @SerialEntry(comment = "The higher the level of precipitation, the more intense the precipitation will be.")
    public static float precipitationLevel = 1.0f;

    public static void load() {
        HANDLER.load();
    }

    public static Screen generateConfigScreen(Screen screen) {
        Builder precipitationLevelGroupBuilder = OptionGroup.createBuilder()
                .name(Component.translatable("config.permanentsnow.precipitation.group.level"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("config.permanentsnow.precipitation.group.level.description"))
                        .build());
        precipitationLevelGroupBuilder.option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.permanentsnow.precipitation.group.level.option.override"))
                        .binding(false, () -> overridePrecipitationLevel, value -> overridePrecipitationLevel = value)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                .option(Option.<Float>createBuilder()
                        .name(Component.translatable("config.permanentsnow.precipitation.group.level.option.level"))
                        .description(OptionDescription.createBuilder()
                                .text(Component.translatable("config.permanentsnow.precipitation.group.level.option.level.description"))
                                .build())
                        .binding(1.0f, () -> precipitationLevel, value -> precipitationLevel = value)
                        .controller(option -> FloatSliderControllerBuilder.create(option)
                                .range(0.0f, 1.0f)
                                .step(0.01f)
                                .formatValue(value -> Component.literal(String.format("%,.2f", value).replaceAll("[  ]", " "))))
                        .build());

        Builder precipitationTypesGroupBuilder = OptionGroup.createBuilder()
                .name(Component.translatable("config.permanentsnow.precipitation.group.types"))
                .description(OptionDescription.createBuilder()
                        .text(Component.translatable("config.permanentsnow.precipitation.group.types.description"))
                        .build());
        Arrays.stream(Precipitation.values())
                .forEach(precipitation -> precipitationTypesGroupBuilder.option(Option.<Precipitation>createBuilder()
                        .name(Component.translatable("config.permanentsnow.precipitation.group.types.option." + precipitation.getSerializedName()))
                        .description(OptionDescription.createBuilder()
                                .text(Component.translatable(
                                        "config.permanentsnow.precipitation.group.types.option." + precipitation.getSerializedName() + ".description"))
                                .build())
                        .binding(
                                precipitation == Precipitation.RAIN ? Precipitation.SNOW : precipitation,
                                () -> overridePrecipitationTypes.getOrDefault(precipitation,
                                        precipitation == Precipitation.RAIN ? Precipitation.SNOW : precipitation
                                ),
                                value -> overridePrecipitationTypes.put(precipitation, value)
                        )
                        .controller(option -> EnumControllerBuilder.create(option)
                                .enumClass(Precipitation.class)
                                .formatValue(value -> Component.translatable(
                                        "config.permanentsnow.precipitation.group.types.option." + value.getSerializedName())))
                        .build()));

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.permanentsnow.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.permanentsnow.precipitation"))
                        .group(precipitationLevelGroupBuilder.build())
                        .group(precipitationTypesGroupBuilder.build())
                        .build())
                .save(HANDLER::save)
                .build()
                .generateScreen(screen);
    }
}
