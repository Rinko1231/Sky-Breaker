package com.rinko1231.skybreaker.Mixin;

import com.legacy.blue_skies.asm_hooks.PlayerHooks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(value = PlayerHooks.class, remap = false)
public abstract class PlayerHooksMixin {
    @Redirect(method = "isBreakingNerfed", at = @At(value = "RETURN"))
    private static boolean noBreakingNerfed(Item item, Player player, BlockState state) {
        return item != null && !TierSortingRegistry.isCorrectTierForDrops(Tiers.WOOD, state);
    }


    @Inject(method = "isNerfableTool", at = @At("HEAD"), cancellable = true)
    private static void noToolNerfed(CallbackInfoReturnable<Boolean> cir) {
        // 强制设置每次升级
        cir.setReturnValue(false);
    }

}