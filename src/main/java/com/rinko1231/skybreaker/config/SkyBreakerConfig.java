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
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> itemWhitelist2;
    public static ForgeConfigSpec.DoubleValue treeProtection;
    public static ForgeConfigSpec.DoubleValue treeProtection2;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> projectileWhitelist;
    public static ForgeConfigSpec.IntValue bossDamageCap;
    public static ForgeConfigSpec.DoubleValue treeUnwhitelistedProjectileDamageCap;

    static
    {
        BUILDER.push("Sky Breaker Config");

        itemWhitelist = BUILDER
                .comment("Besides vanilla AxeItem, the additional Items that can be used to break the Starlit Wall")
                .defineList("Item Whitelist", List.of("mekanism:atomic_disassembler"),
                        element -> element instanceof String);
        itemWhitelist2 = BUILDER
                .comment("Besides vanilla AxeItem, the additional Items that can deal full damage to Starlit Crusher")
                .defineList("Item Whitelist for the Tree", List.of("mekanism:atomic_disassembler"),
                        element -> element instanceof String);
        treeProtection =BUILDER
                .comment("When using whitelisted items, damage dealt to non-stunned Starlit Crusher will be multiplied by [coefficient].")
                .defineInRange("Coefficient",1.0,0.0,1.0);
        treeProtection2 =BUILDER
                .comment("When using non-whitelisted items, damage dealt to Starlit Crusher will be multiplied by [coefficient2].")
                .comment("When set to 1, all weapons will deal full damage without triggering the weakening notification.")
                .defineInRange("Coefficient2",1.0,0.0,1.0);
        projectileWhitelist = BUILDER
                .comment("Besides SpearEntity, the additional Projectiles that can be used to stun the Starlit Crusher")
                .defineList("Projectile Whitelist", List.of("minecraft:trident"),
                        element -> element instanceof String);
        bossDamageCap = BUILDER
                .defineInRange("The Max Damage A Boss Can Take",500,1,Integer.MAX_VALUE);
        treeUnwhitelistedProjectileDamageCap = BUILDER
                .defineInRange("Max damage The Tree Takes from Non-Projectile-Whitelisted projectiles",500,1,Double.MAX_VALUE);


        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "SkyBreaker.toml");
    }


}