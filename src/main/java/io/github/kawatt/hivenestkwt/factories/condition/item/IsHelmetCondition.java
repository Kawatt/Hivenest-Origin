package io.github.kawatt.hivenestkwt.factories.condition.item;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

public class IsHelmetCondition {

    public static boolean condition(SerializableData.Instance data, Pair<World, ItemStack> worldAndStack) {
        ItemStack itemStack = worldAndStack.getRight();
        Item item = itemStack.getItem();
        return (item instanceof ArmorItem armorItem) && (armorItem.getSlotType() == EquipmentSlot.HEAD);
    }

    public static ConditionFactory<Pair<World, ItemStack>> getFactory() {
        return new ConditionFactory<>(
                Hivenest.identifier("is_helmet"),
                new SerializableData(),
                IsHelmetCondition::condition
        );
    }

}
