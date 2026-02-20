package io.github.kawatt.hivenestkwt.factories;

import io.github.kawatt.hivenestkwt.factories.action.entity.ExampleAction;
import io.github.kawatt.hivenestkwt.factories.condition.entity.ExampleCondition;
import io.github.kawatt.hivenestkwt.factories.power.ExamplePower;
import io.github.kawatt.hivenestkwt.utils.ApoliRegistryHelper;

public class ExampleTypeRegistry {

    public static void register() {
        ApoliRegistryHelper.registerEntityAction(ExampleAction.getFactory());
        ApoliRegistryHelper.registerEntityCondition(ExampleCondition.getFactory());
        ApoliRegistryHelper.registerPowerFactory(ExamplePower.getFactory());
    }

}
