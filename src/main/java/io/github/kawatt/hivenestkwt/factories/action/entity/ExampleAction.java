package io.github.kawatt.hivenestkwt.factories.action.entity;

import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class ExampleAction {

    public static void action(SerializableData.Instance data, Entity entity) {
        entity.sendMessage(Text.literal("Action performed!"));
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
            Hivenest.identifier("example_action"),
            new SerializableData(),
            ExampleAction::action
        );
    }

}
