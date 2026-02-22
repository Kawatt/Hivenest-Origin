package io.github.kawatt.hivenestkwt.render;

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

import java.lang.reflect.Field;

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

        //matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));

        // Cambiar a un extensor de BeeEntityModel
        ModelPart bone = null;
        try {
            Field boneField = BeeEntityModel.class.getDeclaredField("bone");
            boneField.setAccessible(true);
            bone = (ModelPart) boneField.get(this.getContextModel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bone != null) {
            matrices.translate(bone.pivotX / 16.0F, (bone.pivotY - 2.6F) / 16.0F, (bone.pivotZ + 5.0F) / 16.0F);
        }
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(85));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
        heldItemRenderer.renderItem(bee, stack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, matrices, vertexConsumers, light);

        matrices.pop();
    }
}
