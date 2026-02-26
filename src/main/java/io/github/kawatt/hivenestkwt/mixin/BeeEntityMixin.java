package io.github.kawatt.hivenestkwt.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.kawatt.hivenestkwt.factories.power.BeeFriend;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import static io.github.kawatt.hivenestkwt.Hivenest.LOGGER;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {

    @Shadow
    private void setHasStung(boolean hasStung) {}

    @Inject(method = "tryAttack", at = @At("RETURN"))
    private void restoreStinger(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity)(Object)this;
        ItemStack itemStack = living.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            this.setHasStung(false);
        }
    }

    @Inject(method = "setAngryAt", at = @At("HEAD"), cancellable = true)
    private void preventAnger(UUID angryAt, CallbackInfo ci) {
        BeeEntity bee = (BeeEntity)(Object)this;
        ServerWorld serverWorld = (ServerWorld) bee.getWorld();
        Entity entity = serverWorld.getEntity(angryAt);
        if (entity != null) {
            if(PowerHolderComponent.hasPower((Entity)(Object)entity, BeeFriend.class)) {
                ci.cancel();
            }
        }
    }

}
