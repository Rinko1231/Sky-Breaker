package com.rinko1231.skybreaker.Mixin;

import com.legacy.blue_skies.entities.util.SkiesEntityHooks;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//伤害还原
@Mixin(value = SkiesEntityHooks.class, remap = false)
public class SkiesEntityHooksMixin {
    @Inject(method = "nerfDamage", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private static void noNerfDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
         cir.setReturnValue(amount);
    }

}