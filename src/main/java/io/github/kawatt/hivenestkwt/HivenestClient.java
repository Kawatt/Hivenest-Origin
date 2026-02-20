package io.github.kawatt.hivenestkwt;

import io.github.kawatt.hivenestkwt.render.BeeArmorFeatureRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.entity.EntityType;

import static io.github.kawatt.hivenestkwt.render.BeeArmorModel.registerLayers;

public class HivenestClient implements ClientModInitializer {

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        registerLayers();
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
            (entityType, renderer, registrationHelper, context) -> {
                if (entityType == EntityType.BEE) {
                    registrationHelper.register(
                            new BeeArmorFeatureRenderer(
                                    (FeatureRendererContext) renderer,
                                    context.getModelLoader()
                            )
                    );
                }
            }
        );
    }
}
