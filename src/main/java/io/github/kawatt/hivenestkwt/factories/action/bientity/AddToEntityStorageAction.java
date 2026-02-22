package io.github.kawatt.hivenestkwt.factories.action.bientity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.kawatt.hivenestkwt.factories.power.EntityStoragePower;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;

public class AddToEntityStorageAction {

    public static void action(SerializableData.Instance data, Pair<Entity, Entity> actorAndTarget) {

        PowerHolderComponent component = PowerHolderComponent.KEY.getNullable(actorAndTarget.getLeft());
        PowerType<?> powerType = data.get("storage");

        if (component == null || powerType == null || !(component.getPower(powerType) instanceof EntityStoragePower entityStoragePower)) {
            return;
        }

        if (entityStoragePower.add(actorAndTarget.getRight(), data.get("time_limit"))) {
            PowerHolderComponent.syncPower(actorAndTarget.getLeft(), powerType);
        }

    }

    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
                Hivenest.identifier("add_to_entity_storage"),
                new SerializableData()
                        .add("storage", ApoliDataTypes.POWER_TYPE)
                        .add("time_limit", SerializableDataTypes.POSITIVE_INT, null),
                AddToEntityStorageAction::action
        );
    }
}
