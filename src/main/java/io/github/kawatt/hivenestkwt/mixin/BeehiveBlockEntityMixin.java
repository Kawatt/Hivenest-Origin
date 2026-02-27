package io.github.kawatt.hivenestkwt.mixin;

import net.minecraft.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {

    @Final
    @Shadow
    @Mutable
    private static List<String> IRRELEVANT_BEE_NBT_KEYS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void hivenest$modifyIrrelevantKeys(CallbackInfo ci) {
        List<String> modified = new ArrayList<>(IRRELEVANT_BEE_NBT_KEYS);

        modified.remove("ArmorDropChances");
        modified.remove("ArmorItems");
        modified.remove("CanPickUpLoot");
        modified.remove("HandDropChances");
        modified.remove("HandItems");
        modified.remove("LeftHanded");
        modified.remove("NoGravity");

        IRRELEVANT_BEE_NBT_KEYS = modified;
    }

}
