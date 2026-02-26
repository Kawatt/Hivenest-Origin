package io.github.kawatt.hivenestkwt.factories.action.bientity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.util.Pair;

public class AngerTargetAction {

    public static void action(SerializableData.Instance data, Pair<Entity, Entity> actorAndTarget) {

        Entity actor = actorAndTarget.getLeft();
        Entity target = actorAndTarget.getRight();

        if (target instanceof Angerable angy) {
            angy.setAngryAt(actor.getUuid());
            angy.setAngerTime(data.get("time"));
        }

    }

    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
                Hivenest.identifier("anger_target"),
                new SerializableData()
                        .add("time", SerializableDataTypes.POSITIVE_INT, 400),
                AngerTargetAction::action
        );
    }
}
