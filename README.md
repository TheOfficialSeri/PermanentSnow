# PermanentSnow

PermanentSnow allows you to overwrite the current weather client side. By default, it replaces all rain with snow.

## Settings

```json5
{
    // Override precipitation types to render as other precipitation types.
    overridePrecipitationTypes: {
        NONE: "SNOW",
        RAIN: "SNOW",
        SNOW: "SNOW"
    },
    // Override the level of precipitation to render.
    overridePrecipitationLevel: true,
    // The higher the level of precipitation, the more intense the precipitation will be.
    precipitationLevel: 1.0
}
```

## FAQ

**Q: Why is there no snow although I set every precipitation type?**

Make sure you also override the level of precipitation as in some cases it might be too low!

## For developers

If you add a custom types of precipitation client side, make sure to set the following translations:

- `config.permanentsnow.precipitation.group.types.option.<custom precipitation type>`

- `config.permanentsnow.precipitation.group.types.option.<custom precipitation type>.description`
