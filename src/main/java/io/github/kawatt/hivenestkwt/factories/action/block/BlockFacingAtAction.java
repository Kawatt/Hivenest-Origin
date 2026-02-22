package io.github.kawatt.hivenestkwt.factories.action.block;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

public class BlockFacingAtAction {
    public static void action(SerializableData.Instance data, Triple<World, BlockPos, Direction> block) {
        World world = block.getLeft();
        BlockPos blockPos = block.getMiddle();

        BlockState state = world.getBlockState(blockPos);

        Direction facing;
        if (state.contains(Properties.HORIZONTAL_FACING)) {
            facing = state.get(Properties.HORIZONTAL_FACING);
        } else if (state.contains(Properties.FACING)) {
            facing = state.get(Properties.FACING);
        } else {
            //LOGGER.info("Block at {} has no facing property, skipping.", blockPos);
            return;
        }

        BlockPos newPos = blockPos.offset(facing, data.get("distance"));

        ActionFactory<Triple<World, BlockPos, Direction>>.Instance action = data.get("action");

        action.accept(Triple.of(world, newPos, block.getRight()));
    }

    public static ActionFactory<Triple<World, BlockPos, Direction>> getFactory() {
        return new ActionFactory<>(Hivenest.identifier("block_facing_at"),
                new SerializableData()
                        .add("action", ApoliDataTypes.BLOCK_ACTION)
                        .add("distance", SerializableDataTypes.INT, 1),
                BlockFacingAtAction::action
        );
    }
}