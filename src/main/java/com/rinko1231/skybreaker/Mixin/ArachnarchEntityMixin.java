package com.rinko1231.skybreaker.Mixin;


import com.legacy.blue_skies.entities.hostile.boss.ArachnarchEntity;
import com.legacy.blue_skies.entities.util.base.SkiesBossEntity;
import com.legacy.blue_skies.entities.util.interfaces.IStunnableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(value = ArachnarchEntity.class, remap = false)
public abstract class ArachnarchEntityMixin extends SkiesBossEntity implements RangedAttackMob, IStunnableMob {

    public ArachnarchEntityMixin(EntityType<? extends SkiesBossEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    @Shadow
    public abstract boolean isAlliedTo(@NotNull Entity entityIn);

    @Shadow
    public abstract void setStunned(boolean stunned);

    @Shadow
    public static Set<Item> SHIELDS;


    @Inject(method = "doHurtTarget", at = @At("HEAD"), cancellable = true)
    private void anyShieldBlock(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (this.isAlliedTo(entityIn)) {
            cir.setReturnValue(false);
        } else {
            if (entityIn instanceof Player player) {
                ItemStack playerItem = player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY;
                if (!playerItem.isEmpty() && !this.level().isClientSide) {
                    if (SHIELDS.contains(playerItem.getItem()) || playerItem.canPerformAction(ToolActions.SHIELD_BLOCK)) {
                        this.level().broadcastEntityEvent(this, (byte) 4);
                        this.setStunned(true);
                        this.level().broadcastEntityEvent(player, (byte) 29);
                        this.level().broadcastEntityEvent(player, (byte) 30);
                        player.disableShield(true);
                        player.getCooldowns().addCooldown(playerItem.getItem(), 300);
                        player.getUsedItemHand();
                        playerItem.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(player.getUsedItemHand()));

                        cir.setReturnValue(false);
                    }

                }
            }

            cir.setReturnValue(super.doHurtTarget(entityIn));
        }
    }


}
