package io.github.kawatt.hivenestkwt.factories.action.block;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.kawatt.hivenestkwt.mixin.BeehiveBlockEntityInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.function.Consumer;

public class SpawnAllFromBeehiveAction {
    public static void action(SerializableData.Instance data, Triple<World, BlockPos, Direction> block) {
        World world = block.getLeft();
        BlockPos blockPos = block.getMiddle();

        BlockState blockState = world.getBlockState(blockPos);
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (blockEntity instanceof BeehiveBlockEntity beehive) {
            if (beehive.hasNoBees()) {
                return;
            }
            List<Entity> bees = ((BeehiveBlockEntityInvoker) beehive)
                    .callTryReleaseBee(blockState, BeehiveBlockEntity.BeeState.EMERGENCY);
            Consumer<Entity> action = data.get("entity_action");
            for (Entity entity : bees) {
                if (entity instanceof BeeEntity bee) {
                    bee.setCannotEnterHiveTicks(data.get("cannot_enter_hive_ticks"));
                }
                if (action != null){
                    action.accept(entity);
                }
            }
        }
    }

    public static ActionFactory<Triple<World, BlockPos, Direction>> getFactory() {
        return new ActionFactory<>(Hivenest.identifier("spawn_all_from_beehive"),
                new SerializableData()
                        .add("entity_action", ApoliDataTypes.ENTITY_ACTION, null)
                        .add("cannot_enter_hive_ticks", SerializableDataTypes.INT, 400),
                SpawnAllFromBeehiveAction::action
        );
    }
}