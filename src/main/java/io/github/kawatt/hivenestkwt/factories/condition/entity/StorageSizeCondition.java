package io.github.kawatt.hivenestkwt.factories.condition.entity;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.kawatt.hivenestkwt.factories.power.EntityStoragePower;
import net.minecraft.entity.Entity;

public class StorageSizeCondition {

    public static boolean condition(SerializableData.Instance data, Entity entity) {
        PowerHolderComponent component = PowerHolderComponent.KEY.maybeGet(entity).orElse(null);
        PowerType<?> powerType = data.get("storage");

        if (component == null || powerType == null || !(component.getPower(powerType) instanceof EntityStoragePower entityStoragePower)) {
            return false;
        }

        Comparison comparison = data.get("comparison");
        int compareTo = data.get("compare_to");

        return comparison.compare(entityStoragePower.size(), compareTo);
    }

    public static ConditionFactory<Entity> getFactory() {
        return new ConditionFactory<>(
                Hivenest.identifier("storage_size"),
                new SerializableData()
                        .add("storage", ApoliDataTypes.POWER_TYPE)
                        .add("comparison", ApoliDataTypes.COMPARISON)
                        .add("compare_to", SerializableDataTypes.INT),
                StorageSizeCondition::condition
        );
    }

}
