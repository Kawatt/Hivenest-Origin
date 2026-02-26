package io.github.kawatt.hivenestkwt.factories.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.block.Block;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ApplySusEffectFromHeldItemAction {

    public static void action(SerializableData.Instance data, Entity entity) {

        if (!(entity instanceof LivingEntity livingEntity) || entity.getWorld().isClient) {
            return;
        }

        EquipmentSlot slot = data.get("equipment_slot");
        if (slot == null) {
            return;
        }
        ItemStack stack = livingEntity.getEquippedStack(slot);
        Item item = stack.getItem();
        Block block = Block.getBlockFromItem(item);
        if (!(block instanceof SuspiciousStewIngredient)) {
            return;
        }
        List<SuspiciousStewIngredient.StewEffect> stewEffects = ((SuspiciousStewIngredient) block).getStewEffects();
        for (SuspiciousStewIngredient.StewEffect stewEffect : stewEffects) {
            if(!entity.getWorld().isClient) {
                livingEntity.addStatusEffect(stewEffect.createStatusEffectInstance());
            }
        }

    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
                Hivenest.identifier("apply_sus_effect_from_held_item"),
                new SerializableData()
                        .add("equipment_slot", SerializableDataTypes.EQUIPMENT_SLOT),
                ApplySusEffectFromHeldItemAction::action
        );
    }
}
