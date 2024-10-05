package com.rinko1231.skybreaker.Mixin;

import com.legacy.blue_skies.events.SkiesEvents;
import com.legacy.blue_skies.items.tools.SkyAxeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//取消伤害削弱
@Mixin(value = SkiesEvents.class, remap = false)
public abstract class SkiesEventsMixin {
    @Inject(method = "onLivingHurt", at = @At("HEAD"), cancellable = true)
    private static void noDamageFix(LivingDamageEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}

