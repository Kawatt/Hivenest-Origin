package io.github.kawatt.hivenestkwt.factories;

import io.github.kawatt.hivenestkwt.factories.action.bientity.AddToEntityStorageAction;
import io.github.kawatt.hivenestkwt.factories.action.block.SpawnEntityFromBeehiveAction;
import io.github.kawatt.hivenestkwt.factories.action.entity.RemoveFromEntityStorageAction;
import io.github.kawatt.hivenestkwt.factories.condition.block.IsBeehiveCondition;
import io.github.kawatt.hivenestkwt.factories.condition.entity.StorageSizeCondition;
import io.github.kawatt.hivenestkwt.factories.power.EntityStoragePower;
import io.github.kawatt.hivenestkwt.utils.ApoliRegistryHelper;

public class HivenestTypeRegistry {

    public static void register() {
        ApoliRegistryHelper.registerBientityAction(AddToEntityStorageAction.getFactory());
        ApoliRegistryHelper.registerEntityAction(RemoveFromEntityStorageAction.getFactory());
        ApoliRegistryHelper.registerEntityCondition(StorageSizeCondition.getFactory());
        ApoliRegistryHelper.registerBlockAction(SpawnEntityFromBeehiveAction.getFactory());
        ApoliRegistryHelper.registerBlockCondition(IsBeehiveCondition.getFactory());
        ApoliRegistryHelper.registerPowerFactory(EntityStoragePower.getFactory());
    }

}
