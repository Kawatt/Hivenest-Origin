package io.github.kawatt.hivenestkwt.factories.condition.block;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static io.github.kawatt.hivenestkwt.Hivenest.LOGGER;

public class FacingAtBlockCondition {
    public static boolean condition(SerializableData.Instance data, CachedBlockPosition cachedBlock) {

        BlockState state = cachedBlock.getBlockState();

        Direction facing;
        if (state.contains(Properties.HORIZONTAL_FACING)) {
            facing = state.get(Properties.HORIZONTAL_FACING);
        } else if (state.contains(Properties.FACING)) {
            facing = state.get(Properties.FACING);
        } else {
            //LOGGER.info("Block at {} has no facing property, skipping.", blockPos);
            return false;
        }

        BlockPos blockPos = cachedBlock.getBlockPos();
        BlockPos newPos = blockPos.offset(facing, data.get("distance"));

        ConditionFactory<CachedBlockPosition>.Instance condition =
                data.get("condition");

        CachedBlockPosition offsetPos = new CachedBlockPosition(
                cachedBlock.getWorld(),
                newPos,
                true
        );

        return condition.test(offsetPos);

    }

    public static ConditionFactory<CachedBlockPosition> getFactory() {
        return new ConditionFactory<>(
                Hivenest.identifier("facing_at_block"),
                new SerializableData()
                        .add("condition", ApoliDataTypes.BLOCK_CONDITION)
                        .add("distance", SerializableDataTypes.INT, 1),
                FacingAtBlockCondition::condition
        );
    }
}
