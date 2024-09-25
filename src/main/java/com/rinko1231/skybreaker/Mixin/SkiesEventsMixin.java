package com.rinko1231.skybreaker.Mixin;

import com.legacy.blue_skies.events.SkiesEvents;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SkiesEvents.class, remap = false)
public abstract class SkiesEventsMixin {
    @Inject(method = "onLivingHurt", at = @At("HEAD"), cancellable = true)
    private static void noDamageFix(LivingDamageEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}