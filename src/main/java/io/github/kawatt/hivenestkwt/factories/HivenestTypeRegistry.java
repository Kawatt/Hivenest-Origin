package io.github.kawatt.hivenestkwt.factories;

import io.github.kawatt.hivenestkwt.factories.action.bientity.AddToEntityStorageAction;
import io.github.kawatt.hivenestkwt.factories.action.bientity.AngerTargetAction;
import io.github.kawatt.hivenestkwt.factories.action.bientity.CopyStackToAction;
import io.github.kawatt.hivenestkwt.factories.action.block.BlockFacingAtAction;
import io.github.kawatt.hivenestkwt.factories.action.block.SpawnAllFromBeehiveAction;
import io.github.kawatt.hivenestkwt.factories.action.entity.ApplySusEffectFromHeldItemAction;
import io.github.kawatt.hivenestkwt.factories.action.entity.RemoveFromEntityStorageAction;
import io.github.kawatt.hivenestkwt.factories.condition.block.FacingAtBlockCondition;
import io.github.kawatt.hivenestkwt.factories.condition.block.IsBeehiveCondition;
import io.github.kawatt.hivenestkwt.factories.condition.entity.EntityStorageSizeCondition;
import io.github.kawatt.hivenestkwt.factories.condition.item.IsHelmetCondition;
import io.github.kawatt.hivenestkwt.factories.condition.item.IsSuspiciousIngredientCondition;
import io.github.kawatt.hivenestkwt.factories.condition.item.IsToolCondition;
import io.github.kawatt.hivenestkwt.factories.power.BeeFriend;
import io.github.kawatt.hivenestkwt.factories.power.EntityStoragePower;
import io.github.kawatt.hivenestkwt.utils.ApoliRegistryHelper;

public class HivenestTypeRegistry {

    public static void register() {
        ApoliRegistryHelper.registerBientityAction(AddToEntityStorageAction.getFactory());
        ApoliRegistryHelper.registerBientityAction(CopyStackToAction.getFactory());
        ApoliRegistryHelper.registerBientityAction(AngerTargetAction.getFactory());
        ApoliRegistryHelper.registerEntityAction(RemoveFromEntityStorageAction.getFactory());
        ApoliRegistryHelper.registerEntityAction(ApplySusEffectFromHeldItemAction.getFactory());
        ApoliRegistryHelper.registerEntityCondition(EntityStorageSizeCondition.getFactory());
        ApoliRegistryHelper.registerBlockAction(SpawnAllFromBeehiveAction.getFactory());
        ApoliRegistryHelper.registerBlockCondition(IsBeehiveCondition.getFactory());
        ApoliRegistryHelper.registerBlockAction(BlockFacingAtAction.getFactory());
        ApoliRegistryHelper.registerBlockCondition(FacingAtBlockCondition.getFactory());
        ApoliRegistryHelper.registerItemCondition(IsHelmetCondition.getFactory());
        ApoliRegistryHelper.registerItemCondition(IsToolCondition.getFactory());
        ApoliRegistryHelper.registerItemCondition(IsSuspiciousIngredientCondition.getFactory());
        ApoliRegistryHelper.registerPowerFactory(EntityStoragePower.getFactory());
        ApoliRegistryHelper.registerPowerFactory(BeeFriend.getFactory());
    }

}
