package io.github.kawatt.hivenestkwt.render;

import io.github.kawatt.hivenestkwt.mixin.BeeEntityModelMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class BeeHeldItemFeatureRenderer extends FeatureRenderer<BeeEntity, BeeEntityModel<BeeEntity>> {
    private final HeldItemRenderer heldItemRenderer;

    public BeeHeldItemFeatureRenderer(
            FeatureRendererContext<BeeEntity, BeeEntityModel<BeeEntity>> context,
            HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BeeEntity bee,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {

        ItemStack stack = bee.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isEmpty()) return;

        matrices.push();

        ModelPart bone = ((BeeEntityModelMixin) this.getContextModel()).getBone();
        bone.rotate(matrices);
        if (bee.isBaby()) {
            matrices.translate(0f,0.09f,0.2f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(80));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrices.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrices.translate(0f,-0.15f,0.3f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(80));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        }
        heldItemRenderer.renderItem(bee, stack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, matrices, vertexConsumers, light);

        matrices.pop();
    }
}
