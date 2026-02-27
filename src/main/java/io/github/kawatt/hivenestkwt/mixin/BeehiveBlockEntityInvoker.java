package io.github.kawatt.hivenestkwt.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public interface BeehiveBlockEntityInvoker {

    @Invoker("tryReleaseBee")
    List<Entity> hivenest$callTryReleaseBee(BlockState state, BeehiveBlockEntity.BeeState beeState);
}