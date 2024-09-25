package com.rinko1231.skybreaker.Mixin;

import com.legacy.blue_skies.asm_hooks.PlayerHooks;
import net.minecraft.core.BlockPos;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

//工具破坏方块还原
@Mixin(value = PlayerHooks.class, remap = false)
public abstract class PlayerHooksMixin {

    @Inject(method = "isBreakingNerfed", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private static void noNerfBreaking(Item item, Player player, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(item != null && !TierSortingRegistry.isCorrectTierForDrops(Tiers.WOOD, state));
    }

    @Inject(method = "modifyBreakSpeed", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private static void noModifyBreakSpeed(float speed, BlockState state, @Nullable BlockPos pos, Player player, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(speed);
    }

}