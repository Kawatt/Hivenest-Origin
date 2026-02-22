package io.github.kawatt.hivenestkwt.factories.action.entity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.kawatt.hivenestkwt.factories.power.EntityStoragePower;
import io.github.kawatt.hivenestkwt.utils.RemoveStrategy;
import net.minecraft.entity.Entity;

public class RemoveFromEntityStorageAction {

    public static void action(SerializableData.Instance data, Entity entity) {

        PowerHolderComponent component = PowerHolderComponent.KEY.getNullable(entity); //left actor
        PowerType<?> powerType = data.get("storage");

        if (component == null || powerType == null || !(component.getPower(powerType) instanceof EntityStoragePower entityStoragePower)) {
            return;
        }

        RemoveStrategy strategy = data.get("strategy");
        if (entityStoragePower.remove(strategy)) { //right target
            PowerHolderComponent.syncPower(entity, powerType); //left actor
        }

    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
                Hivenest.identifier("remove_from_entity_storage"),
                new SerializableData()
                        .add("storage", ApoliDataTypes.POWER_TYPE)
                        .add("strategy", SerializableDataType.enumValue(RemoveStrategy.class)),
                RemoveFromEntityStorageAction::action
        );
    }
}
