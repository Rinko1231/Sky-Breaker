package com.rinko1231.skybreaker;

import com.rinko1231.skybreaker.config.SkyBreakerConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(SkyBreaker.MODID)
public class SkyBreaker
{
    public static final String MODID = "skybreaker";

    public SkyBreaker()
    {
        SkyBreakerConfig.setup();
        MinecraftForge.EVENT_BUS.register(this);
    }


}
