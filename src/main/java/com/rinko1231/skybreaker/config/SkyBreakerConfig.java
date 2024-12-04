package com.rinko1231.skybreaker.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;


public class SkyBreakerConfig
{
    public static ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> itemWhitelist;

    static
    {
        BUILDER.push("Sky Breaker Config");

        itemWhitelist = BUILDER
                .comment("Besides vanilla AxeItem, the additional Items that can be used to break the Starlit Wall")
                .defineList("Item Whitelist", List.of("mekanism:atomicdisassembler"),
                        element -> element instanceof String);

        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "SkyBreaker.toml");
    }


}