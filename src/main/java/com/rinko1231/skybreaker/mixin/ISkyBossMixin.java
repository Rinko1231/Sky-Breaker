package com.rinko1231.skybreaker.mixin;


import com.legacy.blue_skies.entities.util.interfaces.ISkyBoss;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( value = ISkyBoss.class, remap = false)
public interface ISkyBossMixin {
    /**
     * @author Rinko1231
     * @reason No Cap
     */
    @Overwrite
    default int getDamageCap() {
        return 114514;
    }
}