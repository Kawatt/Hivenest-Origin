package io.github.kawatt.hivenestkwt.render;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Set;

import static io.github.kawatt.hivenestkwt.Hivenest.MODID;

@Environment(EnvType.CLIENT)
public class BeeArmorModel {

    public static final EntityModelLayer BEE_ARMOR =
            new EntityModelLayer(new Identifier(MODID, "bee_armor"), "main");

    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(BEE_ARMOR, BeeArmorModel::BeeArmor);
    }

    public static TexturedModelData BeeArmor() {
        ModelData modelData = new ModelData();
        Set<Direction> directions = Set.of(Direction.NORTH, Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP);
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData2.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.5F, -5.5F, 8.0F, 8.0F, 8.0F, directions), ModelTransform.NONE);
        modelPartData3.addChild("stinger", ModelPartBuilder.create(), ModelTransform.NONE);
        modelPartData3.addChild("left_antenna", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -5.0F));
        modelPartData3.addChild("right_antenna", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -5.0F));

        modelPartData2.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.of(-1.5F, -4.0F, -3.0F, 0.0F, -0.2618F, 0.0F));
        modelPartData2.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.of(1.5F, -4.0F, -3.0F, 0.0F, 0.2618F, 0.0F));
        modelPartData2.addChild("front_legs", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 3.0F, -2.0F));
        modelPartData2.addChild("middle_legs", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 3.0F, 0.0F));
        modelPartData2.addChild("back_legs", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 3.0F, 2.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }
}