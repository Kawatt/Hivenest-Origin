package io.github.kawatt.hivenestkwt.render;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.*;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static io.github.kawatt.hivenestkwt.render.BeeArmorModel.BEE_ARMOR;

@Environment(EnvType.CLIENT)
public class BeeArmorFeatureRenderer extends FeatureRenderer<BeeEntity, BeeEntityModel<BeeEntity>> {
    private final BeeEntityModel<BeeEntity> model;
    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();
    private final SpriteAtlasTexture armorTrimsAtlas;

    public BeeArmorFeatureRenderer(FeatureRendererContext<BeeEntity, BeeEntityModel<BeeEntity>> featureRenderer, EntityModelLoader modelLoader, BakedModelManager bakery) {
        super(featureRenderer);
        this.model = new BeeEntityModel<>(modelLoader.getModelPart(BEE_ARMOR));
        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumer, int light, BeeEntity entity, float f, float g, float h, float j, float k, float l) {

        EquipmentSlot armorSlot = EquipmentSlot.HEAD;
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        Item item = itemStack.getItem();
        if (item instanceof ArmorItem armorItem) {
            if (armorItem.getSlotType() == armorSlot) {
                this.getContextModel().copyStateTo(this.model);
                this.model.animateModel(entity, f, g, h);
                this.model.setAngles(entity, f, g, j, k, l);
                boolean bl = this.usesInnerModel(armorSlot);
                if (armorItem instanceof DyeableArmorItem dyeableArmorItem) {
                    int color = dyeableArmorItem.getColor(itemStack);
                    float a = (float)(color >> 16 & 255) / 255.0F;
                    float b = (float)(color >> 8 & 255) / 255.0F;
                    float c = (float)(color & 255) / 255.0F;
                    this.renderArmorParts(matrixStack, vertexConsumer, light, armorItem, model, bl, a, b, c, null);
                    this.renderArmorParts(matrixStack, vertexConsumer, light, armorItem, model, bl, 1.0F, 1.0F, 1.0F, "overlay");
                } else {
                    this.renderArmorParts(matrixStack, vertexConsumer, light, armorItem, model, bl, 1.0F, 1.0F, 1.0F, null);
                }

                ArmorTrim.getTrim(entity.getWorld().getRegistryManager(), itemStack, true).ifPresent((trim) -> this.renderTrim(armorItem.getMaterial(), matrixStack, vertexConsumer, light, trim, model, bl));
                if (itemStack.hasGlint()) {
                    this.renderGlint(matrixStack, vertexConsumer, light, model);
                }

            }
        }
    }
    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Model model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, Model model, boolean secondTextureLayer, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, secondTextureLayer, overlay)));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private Identifier getArmorTexture(ArmorItem item, boolean secondLayer, @Nullable String overlay) {
        String name = item.getMaterial().getName();
        String string = "textures/models/armor/" + name + "_layer_" + (secondLayer ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new);
    }
    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
    private void renderTrim(ArmorMaterial material, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, Model model, boolean leggings) {
        Sprite sprite = this.armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(material) : trim.getGenericModelId(material));
        VertexConsumer vertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims(trim.getPattern().value().decal())));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}