package com.rinko1231.skybreaker.mixin;


import com.legacy.blue_skies.entities.hostile.boss.StarlitCrusherEntity;
import com.legacy.blue_skies.entities.hostile.boss.summons.ent.EntWallEntity;
import com.legacy.blue_skies.entities.projectile.SpearEntity;
import com.legacy.blue_skies.entities.util.base.SkiesBossEntity;
import com.legacy.blue_skies.entities.util.interfaces.IStunnableMob;
import com.legacy.blue_skies.items.tools.SkyAxeItem;
import com.legacy.blue_skies.registries.SkiesParticles;
import com.rinko1231.skybreaker.config.SkyBreakerConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Objects;

@Mixin(value = StarlitCrusherEntity.class)
public abstract class TreeBossMixin extends SkiesBossEntity implements IStunnableMob {


    @Shadow
    private int spearImmuneTime;

    public TreeBossMixin(EntityType<? extends SkiesBossEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    /**
     * @author Rinko1231
     * @reason MVP 13.0
     */
    @Overwrite
    public boolean hurt(DamageSource source, float amount) {
        if (source == this.damageSources().fellOutOfWorld()) {
            return super.hurt(source, amount);
        } else if (this.getInvulTime() <= 0 && !this.isSpinning() && (source.isCreativePlayer() || this.getWallsInDungeonArea((double) 5.0F).size() < 4) && source != this.damageSources().inWall()) {
            if (this.hurtTime <= 0 && this.level() instanceof ServerLevel) {
                ((ServerLevel) this.level()).sendParticles(SkiesParticles.FALLING_LEAF, this.getX(), this.getY() + (double) this.getBbHeight() + (double) 0.1F, this.getZ(), 60, this.getBoundingBox().getXsize() / (double) 2.0F, (double) 0.1F, this.getBoundingBox().getZsize() / (double) 2.0F, 0.05);
            }


            if (!this.level().isClientSide() && this.getWallsInDungeonArea((double) 5.0F).size() < 4 && this.spearImmuneTime <= 0 && source.is(DamageTypeTags.IS_PROJECTILE) && ba_painting$permittedProjectileDamage(source)) {
                if (!this.isStunned()) {
                    this.setStunned(true);
                    this.setRooted(false);
                    return super.hurt(source, Math.min(this.getHealth(), amount / 2.0F));
                } else {
                    return super.hurt(source, amount * 0.3F);
                }
            } else if (source.isCreativePlayer()) {
                if (source.getEntity() instanceof Player) {
                    ItemStack stack = ((LivingEntity) source.getEntity()).getMainHandItem();
                    if (stack.getItem() == Items.DEBUG_STICK) {
                        return false;
                    }
                }

                return super.hurt(source, amount);
            } else {
                if (source.getDirectEntity() instanceof LivingEntity) {
                    ItemStack stack = ((LivingEntity) source.getDirectEntity()).getMainHandItem();
                    String itemId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem())).toString();
                    if (stack.getItem() instanceof AxeItem || SkyBreakerConfig.itemWhitelist2.get().contains(itemId)) {
                        if (this.isStunned()) {
                            return super.hurt(source, amount);
                        }

                        return super.hurt(source, amount * SkyBreakerConfig.treeProtection.get().floatValue());
                    }
                }

                if (source.getDirectEntity() instanceof Player && SkyBreakerConfig.treeProtection2.get() < 1.0) {
                    ((Player) source.getDirectEntity()).displayClientMessage(Component.translatable("gui.blue_skies.tooltip.invalid_ent_weapon"), true);
                }

                return super.hurt(source, Math.min(5.0F, amount * SkyBreakerConfig.treeProtection2.get().floatValue()));
            }
        } else {
            return false;
        }
    }

    @Shadow
    public abstract void setRooted(boolean b);

    @Shadow
    public abstract boolean isSpinning();

    @Shadow
    public abstract List<EntWallEntity> getWallsInDungeonArea(double distance);

    @Shadow
    public abstract void setStunned(boolean b);

    @Shadow
    public abstract void kill();

    @Unique
    Boolean ba_painting$permittedProjectileDamage(DamageSource source) {
        String pID = ForgeRegistries.ENTITY_TYPES.getKey(Objects.requireNonNull(source.getDirectEntity()).getType()).toString();
        return (source.getDirectEntity() instanceof SpearEntity || SkyBreakerConfig.projectileWhitelist.get().contains(pID));
    }

}
