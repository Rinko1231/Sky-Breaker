package com.rinko1231.skybreaker.Mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//Potato boy the Great!
//护甲还原
@Mixin(LivingEntity.class)
public abstract class MCLivingEntityMixin extends Entity {
    public MCLivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    public abstract double getAttributeValue(Attribute attribute);

    @SuppressWarnings("ConstantValue")
    @Inject(method = "getArmorValue", at = @At("HEAD"), cancellable = true)
    private void onGetArmorValue(CallbackInfoReturnable<Integer> cir) {
        int returnedArmorValue = Mth.floor(this.getAttributeValue(Attributes.ARMOR));

            cir.setReturnValue(returnedArmorValue);

    }
}