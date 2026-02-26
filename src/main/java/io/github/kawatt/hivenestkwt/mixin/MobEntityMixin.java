package io.github.kawatt.hivenestkwt.mixin;


import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.kawatt.hivenestkwt.factories.power.BeeFriend;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    protected MobEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "setTarget", at = @At("HEAD"), argsOnly = true)
    private LivingEntity changeTarget(LivingEntity target) {
        if(getWorld().isClient || !(target instanceof PlayerEntity)) {
            return target;
        }
        if ((Object)this instanceof BeeEntity && PowerHolderComponent.hasPower(target, BeeFriend.class)) {
            return null;
        }
        return target;
    }
}
