package io.github.kawatt.hivenestkwt.factories;

import io.github.kawatt.hivenestkwt.factories.action.bientity.AddToEntityStorageAction;
import io.github.kawatt.hivenestkwt.factories.action.entity.RemoveFromEntityStorageAction;
import io.github.kawatt.hivenestkwt.factories.condition.entity.StorageSizeCondition;
import io.github.kawatt.hivenestkwt.factories.power.EntityStoragePower;
import io.github.kawatt.hivenestkwt.factories.power.ExamplePower;
import io.github.kawatt.hivenestkwt.utils.ApoliRegistryHelper;

public class ExampleTypeRegistry {

    public static void register() {
        ApoliRegistryHelper.registerBientityAction(AddToEntityStorageAction.getFactory());
        ApoliRegistryHelper.registerEntityAction(RemoveFromEntityStorageAction.getFactory());
        ApoliRegistryHelper.registerEntityCondition(StorageSizeCondition.getFactory());
        ApoliRegistryHelper.registerPowerFactory(EntityStoragePower.getFactory());
    }

}
