package io.github.kawatt.hivenestkwt.factories.power;

import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class ExamplePower extends Power {
    public ExamplePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory getFactory() {
        return new PowerFactory<>(
                Hivenest.identifier("example_power"),
                new SerializableData(),
                data -> (powerType, livingEntity) -> new ExamplePower(powerType,livingEntity)
        ).allowCondition();
    }
}
