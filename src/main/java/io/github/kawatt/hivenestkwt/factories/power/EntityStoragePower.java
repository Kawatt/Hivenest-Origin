package io.github.kawatt.hivenestkwt.factories.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.kawatt.hivenestkwt.Hivenest;
import io.github.kawatt.hivenestkwt.utils.RemoveStrategy;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.*;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

import static io.github.kawatt.hivenestkwt.Hivenest.LOGGER;

public class EntityStoragePower extends Power {
    // Could add some more
    private static final List<String> IRRELEVANT_ENTITY_NBT_KEYS = Arrays.asList("Air", "Brain", "DeathTime", "HurtByTimestamp", "HurtTime", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID");

    private final Consumer<Entity> actionOnAdd;
    private final Consumer<Pair<Entity, Entity>> actionOnRemove;
    private final int tickRate;

    private final List<StoredEntity> entities = new ArrayList<>();

    private Integer startTicks = null;

    private boolean wasActive = false;
    // Temporary logic has to be completely rewritten
    private boolean removedTemps = false;

    public EntityStoragePower(PowerType<?> type, LivingEntity entity, Consumer<Entity> actionOnAdd, Consumer<Pair<Entity, Entity>> actionOnRemove, int tickRate) {
        super(type, entity);
        this.actionOnAdd = actionOnAdd;
        this.actionOnRemove = actionOnRemove;
        this.tickRate = tickRate;
        this.setTicking(true);
    }

    /*@Override
    public void onAdded() {
        removedTemps = entityUuids.removeIf(tempUuids::contains);
        tempUuids.clear();
    }*/

    @Override
    public void tick() {

        if (removedTemps) {

            this.removedTemps = false;
            PowerHolderComponent.syncPower(this.entity, this.type);

            return;

        }

        if (!this.entities.isEmpty() && this.isActive()) {

            if (startTicks == null) {
                this.startTicks = entity.age % tickRate;
                return;
            }

            if (entity.age % tickRate == startTicks) {
                //this.tickTempEntities();
            }

            this.wasActive = true;

        }

        else if (wasActive) {
            this.startTicks = null;
            this.wasActive = false;
        }

    }

    protected void tickTempEntities() {

        long time = entity.age;

        for (StoredEntity storedEntity : entities) {
            if (time < storedEntity.ticksInStorage) { //or the other one?
                continue;
            }

            entities.remove(storedEntity);
            spawnAndAction(storedEntity, true);
        }

    }

    public boolean add(Entity entity) {
        return add(entity, null);
    }

    public boolean add(Entity entity, @Nullable Integer time) {

        if (entity == null || entity.isRemoved() || entity.getWorld().isClient || entity.isPlayer()) {
            return false;
        }

        entity.stopRiding();
        entity.removeAllPassengers();
        NbtCompound nbtCompound = new NbtCompound();
        entity.saveNbt(nbtCompound);
        int tempTime = time != null ? time : 0;
        this.entities.add(new StoredEntity(nbtCompound, 0, tempTime));
        entity.discard();

        //entity.getWorld().getTime()
        if (actionOnAdd != null) {
            actionOnAdd.accept(this.entity);
        }

        return true;

    }

    static void removeIrrelevantNbtKeys(NbtCompound compound) {
        for(String string : IRRELEVANT_ENTITY_NBT_KEYS) {
            compound.remove(string);
        }
    }

    static class StoredEntity {
        final NbtCompound entityData;
        int ticksInStorage;
        final int minOccupationTicks;

        StoredEntity(NbtCompound entityData, int ticksInStorage, int minOccupationTicks) {
            removeIrrelevantNbtKeys(entityData);
            this.entityData = entityData;
            this.ticksInStorage = ticksInStorage;
            this.minOccupationTicks = minOccupationTicks;
        }
    }

    public boolean remove(RemoveStrategy strategy) {
        return this.remove(strategy, true);
    }

    // Selects an entity to remove from the storage and spawn following a strategy
    public boolean remove(RemoveStrategy strategy, boolean executeRemoveAction) {

        /*if (entity == null || entity.getWorld().isClient) {
            return false;
        }*/

        Pair<StoredEntity, Boolean> pair = RemoveStrategy.remove(strategy, entities, 0);
        StoredEntity storedEntity = pair.getLeft();
        Boolean removed = pair.getRight();
        LOGGER.info("Removing entity {} from storage", storedEntity);
        if (removed) {
            spawnAndAction(storedEntity, executeRemoveAction);
        }

        return removed;

    }

    // Attempts to spawn the entity and executes the action afterwards
    private void spawnAndAction(StoredEntity storedEntity, boolean executeRemoveAction) {
        boolean spawned = spawnStoredEntity(storedEntity);

        if (executeRemoveAction && spawned && actionOnRemove != null) {
            actionOnRemove.accept(new Pair<>(this.entity, entity));
        }

    }

    // Attempts to spawn an entity
    private boolean spawnStoredEntity(StoredEntity storedEntity) {
        NbtCompound nbtCompound = storedEntity.entityData.copy();
        removeIrrelevantNbtKeys(nbtCompound);
        World world = this.entity.getWorld();
        Entity liberatedEntity = EntityType.loadEntityWithPassengers(nbtCompound, world, (entityx) -> entityx);
        if (liberatedEntity != null) {
            if (liberatedEntity instanceof AnimalEntity animal) {
                //do I keep?
                ageEntity(storedEntity.ticksInStorage, animal);
            }

            //in case I need to pull the entity up
            double y = this.entity.getY() /*- (double)(entity.getHeight() / 2.0F)*/;
            liberatedEntity.refreshPositionAndAngles(this.entity.getX(), y, this.entity.getZ(),
                    this.entity.getYaw(), this.entity.getPitch());


            return world.spawnEntity(liberatedEntity);
        } else {
            return false;
        }
    }

    // should I check for more data? more general entity stuff?
    private static void ageEntity(int ticks, AnimalEntity entity) {
        int i = entity.getBreedingAge();
        if (i < 0) {
            entity.setBreedingAge(Math.min(0, i + ticks));
        } else if (i > 0) {
            entity.setBreedingAge(Math.max(0, i - ticks));
        }

        entity.setLoveTicks(Math.max(0, entity.getLoveTicks() - ticks));
    }

    public int size() {
        return entities.size();
    }

    public void clear() {

        if (actionOnRemove != null) {

            for (StoredEntity storedEntity : entities) {
                spawnAndAction(storedEntity,true);
            }

        }

        boolean wasNotEmpty = !entities.isEmpty();
        entities.clear();

        if (wasNotEmpty) {
            PowerHolderComponent.syncPower(this.entity, this.type);
        }

    }

    private NbtList getStoredEntitiesNBT() {
        NbtList nbtList = new NbtList();

        for(StoredEntity storedEntity : this.entities) {
            NbtCompound nbtCompound = storedEntity.entityData.copy();
            nbtCompound.remove("UUID");
            NbtCompound nbtCompound2 = new NbtCompound();
            nbtCompound2.put("EntityData", nbtCompound);
            nbtCompound2.putInt("TicksInStorage", storedEntity.ticksInStorage);
            nbtCompound2.putInt("MinOccupationTicks", storedEntity.minOccupationTicks);
            nbtList.add(nbtCompound2);
        }

        return nbtList;
    }

    //From variables to tag
    @Override
    public NbtElement toTag() {

        NbtCompound rootNbt = new NbtCompound();
        rootNbt.put("Entities", this.getStoredEntitiesNBT());

        return rootNbt;

    }

    //From tag to variables
    @Override
    public void fromTag(NbtElement tag) {

        if (!(tag instanceof NbtCompound rootNbt)) {
            return;
        }

        entities.clear();

        NbtList storedEntitiesNbt = rootNbt.getList("Entities", NbtElement.COMPOUND_TYPE);
        for(int i = 0; i < storedEntitiesNbt.size(); ++i) {
            NbtCompound nbtCompound = storedEntitiesNbt.getCompound(i);
            StoredEntity storedEntity = new StoredEntity(
                    nbtCompound.getCompound("EntityData"),
                    nbtCompound.getInt("TicksInStorage"),
                    nbtCompound.getInt("MinOccupationTicks")
            );
            this.entities.add(storedEntity);
        }

        removedTemps = rootNbt.getBoolean("RemovedTemps");

    }

    public static PowerFactory getFactory() {
        return new PowerFactory<>(
                Hivenest.identifier("entity_storage"),
                new SerializableData()
                        .add("action_on_add", ApoliDataTypes.ENTITY_ACTION, null)
                        .add("action_on_remove", ApoliDataTypes.BIENTITY_ACTION, null)
                        .add("tick_rate", SerializableDataTypes.POSITIVE_INT, 1),
                data -> (powerType, livingEntity) -> new EntityStoragePower(
                        powerType,
                        livingEntity,
                        data.get("action_on_add"),
                        data.get("action_on_remove"),
                        data.get("tick_rate")
                )
        ).allowCondition();
    }
}
