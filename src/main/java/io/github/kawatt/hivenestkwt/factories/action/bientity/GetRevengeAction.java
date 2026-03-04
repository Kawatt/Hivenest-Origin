package io.github.kawatt.hivenestkwt.factories.action.bientity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Pair;

public class GetRevengeAction {

    public static void action(SerializableData.Instance data, Pair<Entity, Entity> actorAndTarget) {

        Entity actor = actorAndTarget.getLeft();
        Entity target = actorAndTarget.getRight();

        if (target instanceof MobEntity mobEntity) {
            if (actor instanceof LivingEntity livingEntity) {
                mobEntity.setTarget(livingEntity);
            }
        }

    }

    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
                Hivenest.identifier("anger_target"),
                new SerializableData(),
                GetRevengeAction::action
        );
    }
}
