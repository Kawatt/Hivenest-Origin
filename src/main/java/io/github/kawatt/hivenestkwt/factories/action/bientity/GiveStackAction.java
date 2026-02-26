package io.github.kawatt.hivenestkwt.factories.action.bientity;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.Set;

import static io.github.apace100.apoli.util.InventoryUtil.*;

public class GiveStackAction {

    public static void action(SerializableData.Instance data, Pair<Entity, Entity> actorAndTarget) {

        Entity actor = actorAndTarget.getLeft();
        Entity target = actorAndTarget.getRight();

        if (!(actor instanceof LivingEntity)) {
            return;
        }

        Set<Integer> slots = getSlots(data);
        slots.removeIf(slot -> slotNotWithinBounds(actor, null, slot));
        data.set("merge_nbt", false);
        for (int slot : slots) {

            StackReference stackref = getStackReference(actor, null, slot);
            ItemStack stack = stackref.get();
            data.set("stack", stack);
        }

        replaceInventory(data, target, null);

    }

    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
                Hivenest.identifier("give_stack"),
                new SerializableData()
                        .add("actor_slot", ApoliDataTypes.ITEM_SLOT, null)
                        .add("target_slot", ApoliDataTypes.ITEM_SLOT, null),
                GiveStackAction::action
        );
    }
}
