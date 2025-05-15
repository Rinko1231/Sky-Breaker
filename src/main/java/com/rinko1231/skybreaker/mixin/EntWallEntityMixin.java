package com.rinko1231.skybreaker.mixin;

import com.legacy.blue_skies.entities.hostile.boss.summons.ent.EntWallEntity;
import com.rinko1231.skybreaker.config.SkyBreakerConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(value = EntWallEntity.class)
public abstract class EntWallEntityMixin extends LivingEntity {

    protected EntWallEntityMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Shadow
    public abstract void playDamageEffect();

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void wallBreaker(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == this.damageSources().fellOutOfWorld()) {
            cir.setReturnValue(super.hurt(source, amount));

        } else {
            if (source.getDirectEntity() instanceof LivingEntity) {
                ItemStack stack = ((LivingEntity) source.getDirectEntity()).getMainHandItem();
                String itemId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem())).toString();
                if (stack.getItem() instanceof AxeItem || SkyBreakerConfig.itemWhitelist.get().contains(itemId)) {
                    this.playDamageEffect();
                    cir.setReturnValue(super.hurt(source, amount));
                    return;
                }
                if (source.getDirectEntity() instanceof Player) {
                    ((Player) source.getDirectEntity()).displayClientMessage(Component.translatable("gui.blue_skies.tooltip.invalid_ent_weapon"), true);
                }
            }
            // 默认返回 false
            cir.setReturnValue(false);
        }
    }




}