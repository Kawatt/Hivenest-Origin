package io.github.kawatt.hivenestkwt.factories.condition.item;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.block.Block;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

public class IsSuspiciousIngredientCondition {

    public static boolean condition(SerializableData.Instance data, Pair<World, ItemStack> worldAndStack) {
        ItemStack itemStack = worldAndStack.getRight();
        Item item = itemStack.getItem();
        Block block = Block.getBlockFromItem(item);
        return (block instanceof SuspiciousStewIngredient);
    }

    public static ConditionFactory<Pair<World, ItemStack>> getFactory() {
        return new ConditionFactory<>(
                Hivenest.identifier("is_suspicious_ingredient"),
                new SerializableData(),
                IsSuspiciousIngredientCondition::condition
        );
    }

}
