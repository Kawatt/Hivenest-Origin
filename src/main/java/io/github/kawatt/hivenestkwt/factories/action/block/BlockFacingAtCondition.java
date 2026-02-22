package io.github.kawatt.hivenestkwt.factories.action.block;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.kawatt.hivenestkwt.mixin.BeehiveBlockEntityInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class BlockFacingAtCondition {
    public static void action(SerializableData.Instance data, Triple<World, BlockPos, Direction> block) {
        World world = block.getLeft();
        BlockPos blockPos = block.getMiddle();

        BlockState state = world.getBlockState(blockPos);

        if (!state.contains(Properties.FACING)) {
            return;
        }

        Direction facing = state.get(Properties.FACING);

        BlockPos newPos = blockPos.offset(facing, data.get("distance"));

        ActionFactory<Triple<World, BlockPos, Direction>>.Instance action =data.get("action");

        action.accept(Triple.of(world, newPos, block.getRight()));
    }

    public static ActionFactory<Triple<World, BlockPos, Direction>> getFactory() {
        return new ActionFactory<>(Hivenest.identifier("spawn_entity_from_beehive"),
                new SerializableData()
                        .add("action", ApoliDataTypes.BLOCK_ACTION)
                        .add("distance", SerializableDataTypes.INT, 1),
                BlockFacingAtCondition::action
        );
    }
}