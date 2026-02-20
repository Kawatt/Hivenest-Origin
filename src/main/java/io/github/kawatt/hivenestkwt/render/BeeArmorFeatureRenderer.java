package io.github.kawatt.hivenestkwt.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static io.github.kawatt.hivenestkwt.render.BeeArmorModel.BEE_ARMOR;

@Environment(EnvType.CLIENT)
public class BeeArmorFeatureRenderer extends FeatureRenderer<BeeEntity, BeeEntityModel<BeeEntity>> {
    private final BeeEntityModel<BeeEntity> model;

    public BeeArmorFeatureRenderer(FeatureRendererContext<BeeEntity, BeeEntityModel<BeeEntity>> featureRenderer, EntityModelLoader modelLoader) {
        super(featureRenderer);
        this.model = new BeeEntityModel<>(modelLoader.getModelPart(BEE_ARMOR));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BeeEntity entity, float f, float g, float h, float j, float k, float l) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);
        Item item = stack.getItem();
        this.getContextModel().copyStateTo(this.model);
        this.model.animateModel(entity, f, g, h);
        this.model.setAngles(entity, f, g, j, k, l);
        Identifier location = new Identifier("minecraft",
                "textures/models/armor/diamond_layer_1.png");
        VertexConsumer consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(location));
        this.model.render(matrixStack, consumer, i, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
    }
}