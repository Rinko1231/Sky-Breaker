package com.rinko1231.skybreaker.mixin;

import com.legacy.blue_skies.entities.hostile.boss.ArachnarchEntity;
import com.rinko1231.skybreaker.config.SkyBreakerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArachnarchEntity.class, remap = false)
public abstract class SpiderDamageCapMixin {
    /**
     * @author Rinko1231
     * @reason I do not like that >_<
     */
    @Inject(method = "getDamageCap", at = @At("HEAD"), cancellable = true)
    public void getDamageCap2(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(SkyBreakerConfig.bossDamageCap.get());
    }
}
