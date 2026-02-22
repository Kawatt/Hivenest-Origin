package io.github.kawatt.hivenestkwt.factories.condition.block;

import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IsBeehiveCondition {
    public static boolean condition(SerializableData.Instance data, CachedBlockPosition cachedBlock) {

        World world = (World) cachedBlock.getWorld();
        BlockPos blockPos = cachedBlock.getBlockPos();
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        return blockEntity instanceof BeehiveBlockEntity;

    }

    public static ConditionFactory<CachedBlockPosition> getFactory() {
        return new ConditionFactory<>(
                Hivenest.identifier("is_beehive"),
                new SerializableData(),
                IsBeehiveCondition::condition
        );
    }
}
