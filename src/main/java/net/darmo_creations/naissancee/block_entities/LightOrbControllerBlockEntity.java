package net.darmo_creations.naissancee.block_entities;

import net.darmo_creations.naissancee.blocks.LightOrbControllerBlock;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.entities.LightOrbEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Tile entity for light orb controller.
 * <p>
 * Creates a light orb entity when the tile entity is created, specifically when {@link #init()} is called.
 * It defines the behavior its associated light orb.
 *
 * @see LightOrbControllerBlock
 * @see ModBlocks#LIGHT_ORB_CONTROLLER
 */
public class LightOrbControllerBlockEntity extends BlockEntity {
  private static final String ORB_ID_TAG_KEY = "OrbID";
  private static final String ACTIVE_TAG_KEY = "Active";
  private static final String LOOPS_TAG_KEY = "Loops";
  private static final String INVISIBLE_TAG_KEY = "Invisible";
  private static final String LIGHT_LEVEL_TAG_KEY = "LightLevel";
  private static final String SPEED_TAG_KEY = "Speed";
  private static final String CHECKPOINTS_TAG_KEY = "Checkpoints";

  /**
   * UUID of associated orb.
   */
  private UUID orbID;
  /**
   * Whether the orb is active, i.e. whether the player can interact with it.
   */
  private boolean active;
  /**
   * Path the light orb must follow.
   */
  private List<PathCheckpoint> checkpoints;
  /**
   * Whether the path loops, i.e. whether the orb should go back
   * to start checkpoint after it has reached the last one.
   */
  private boolean loops;
  /**
   * Light level of the light blocks placed by the entity.
   */
  private int lightLevel;
  /**
   * Movement speed of orb in blocks per second.
   */
  private double speed;
  /**
   * Whether the orb should be invisible, i.e. not emit any particles.
   */
  private boolean invisible;

  /**
   * Create a tile entity with empty path.
   */
  public LightOrbControllerBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.LIGHT_ORB_CONTROLLER, pos, state);
    this.checkpoints = new LinkedList<>();
  }

  /**
   * Initialize this tile entity.
   * The path is initialized with a single stop checkpoint positionned right above the controller block.
   * Light level is set to 15 and speed to 0.25 blocks per second. Path does not loop by default.
   */
  public void init() {
    this.setActive(false);
    this.setLightLevel(15);
    this.setLoops(false);
    this.setSpeed(0.25);
    this.checkpoints = new LinkedList<>();
    this.addCheckpoint(this.pos.up(), true, 0);
    this.spawnOrb();
  }

  /**
   * Spawns a new light orb after deleting the already existing one if it exists.
   * Does nothing when called client-side.
   */
  public void spawnOrb() {
    //noinspection ConstantConditions
    if (!this.world.isClient()) {
      LightOrbEntity currentOrb = this.getOrb();
      if (currentOrb != null) {
        currentOrb.remove(Entity.RemovalReason.DISCARDED);
      }
      LightOrbEntity newOrb = new LightOrbEntity(this.world, this);
      BlockPos pos = this.checkpoints.get(0).getPos();
      newOrb.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
      this.world.spawnEntity(newOrb);
      this.orbID = newOrb.getUuid();
      newOrb.init();
      this.markDirty();
    }
  }

  /**
   * Kills the associated light orb.
   * Does nothing when called client-side.
   */
  public void killOrb() {
    //noinspection ConstantConditions
    if (!this.world.isClient()) {
      LightOrbEntity currentOrb = this.getOrb();
      if (currentOrb != null) {
        currentOrb.remove(Entity.RemovalReason.DISCARDED);
        this.markDirty();
      }
    }
  }

  /**
   * Get the associated orb entity, based on its UUID.
   *
   * @return The entity object or null if it doesn’t exist.
   * @see #orbID
   */
  private LightOrbEntity getOrb() {
    //noinspection ConstantConditions
    List<LightOrbEntity> orbs = this.world.getEntitiesByClass(LightOrbEntity.class, VoxelShapes.UNBOUNDED.getBoundingBox(), e -> e.getUuid().equals(this.orbID));
    return !orbs.isEmpty() ? orbs.get(0) : null;
  }

  /**
   * Whether this controller is active, i.e. the light orb should react to player.
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * Set active state.
   *
   * @param active Whether the orb should interact with the player.
   */
  public void setActive(boolean active) {
    this.active = active;
    this.markDirty();
  }

  /**
   * Adds a new checkpoint for the given position at the end of the path.
   *
   * @param pos         Checkpoint’s position.
   * @param stop        Whether the orb should stop at this checkpoint.
   * @param ticksToWait Number of ticks the entity has to wait for before moving again.
   */
  public void addCheckpoint(BlockPos pos, boolean stop, int ticksToWait) {
    this.checkpoints.add(new PathCheckpoint(pos, stop, ticksToWait));
    this.spawnOrb();
  }

  /**
   * Remove all checkpoints at the given position.
   *
   * @param pos The position to delete checkpoints from.
   * @return The number of checkpoints that were removed.
   */
  public int removeCheckpoint(BlockPos pos) {
    int oldSize = this.checkpoints.size();
    List<PathCheckpoint> checkpoints = new LinkedList<>(this.checkpoints);
    boolean anyRemoved = checkpoints.removeIf(pc -> pc.getPos().equals(pos));
    if (checkpoints.isEmpty()) { // If list becomes empty, abort
      return 0;
    }
    this.checkpoints = checkpoints;
    if (anyRemoved) {
      this.spawnOrb();
    }
    return oldSize - this.checkpoints.size();
  }

  /**
   * Get the list of checkpoints.
   */
  public List<PathCheckpoint> getCheckpoints() {
    return this.checkpoints.stream().map(PathCheckpoint::clone).collect(Collectors.toList());
  }

  /**
   * Set the list of checkpoints. Should only be used for syncing.
   *
   * @param checkpoints The list of checkpoints.
   * @throws IllegalArgumentException If the list is empty.
   */
  public void setCheckpoints(List<PathCheckpoint> checkpoints) {
    if (checkpoints.size() == 0) {
      throw new IllegalArgumentException("checkpoints list is empty");
    }
    this.checkpoints = checkpoints.stream().map(PathCheckpoint::clone).collect(Collectors.toCollection(LinkedList::new));
    this.markDirty();
  }

  /**
   * Whether the path has a checkpoint at the given position.
   *
   * @param pos A block position.
   * @return True if there is at least one checkpoint at this position, false if there are none.
   */
  public boolean hasCheckpointAt(BlockPos pos) {
    return this.checkpoints.stream().anyMatch(pc -> pc.getPos().equals(pos));
  }

  /**
   * Return the checkpoint that immediatly follows the one at the given index.
   *
   * @param index A checkpoint index.
   * @return The next checkpoint with its index; an empty value if the checkpoint
   * at the given index is the last one and the path does not loop.
   */
  public Optional<Pair<Integer, PathCheckpoint>> getNextCheckpoint(int index) {
    if (index < this.checkpoints.size() - 1) {
      return Optional.of(new ImmutablePair<>(index + 1, this.checkpoints.get(index + 1).clone()));
    } else if (this.loops) {
      return Optional.of(new ImmutablePair<>(0, this.checkpoints.get(0).clone()));
    }
    return Optional.empty();
  }

  /**
   * Whether the path loops.
   */
  public boolean loops() {
    return this.loops;
  }

  public void setLoops(boolean loops) {
    this.loops = loops;
    this.markDirty();
  }

  /**
   * Get the light orb’s light level.
   */
  public int getLightLevel() {
    return this.lightLevel;
  }

  /**
   * Sets the orb’s light level.
   *
   * @param lightLevel The new light level.
   * @throws IllegalArgumentException If light level is outside [0, 15] range.
   */
  public void setLightLevel(int lightLevel) {
    if (lightLevel < 0 || lightLevel > 15) {
      throw new IllegalArgumentException("invalid light value " + lightLevel);
    }
    this.lightLevel = lightLevel;
    this.markDirty();
  }

  /**
   * Get light orb’s speed (blocks per second).
   */
  public double getSpeed() {
    return this.speed;
  }

  /**
   * Set light orb’s speed (blocks per second).
   *
   * @param speed The new speed.
   * @throws IllegalArgumentException If the speed is negative.
   */
  public void setSpeed(double speed) {
    if (speed < 0) {
      throw new IllegalArgumentException("negative speed");
    }
    this.speed = speed;
    this.markDirty();
  }

  /**
   * Whether the orb is invisible, i.e. should not emit any particles.
   */
  public boolean isEntityInvisible() {
    return this.invisible;
  }

  /**
   * Set light orb’s visibility.
   *
   * @param invisible True to make it invisible, false to make it visible.
   */
  public void setEntityInvisible(boolean invisible) {
    this.invisible = invisible;
  }

  @Override
  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    nbt.put(ORB_ID_TAG_KEY, NbtHelper.fromUuid(this.orbID));
    nbt.putBoolean(ACTIVE_TAG_KEY, this.active);
    nbt.putBoolean(LOOPS_TAG_KEY, this.loops);
    nbt.putBoolean(INVISIBLE_TAG_KEY, this.invisible);
    nbt.putInt(LIGHT_LEVEL_TAG_KEY, this.lightLevel);
    nbt.putDouble(SPEED_TAG_KEY, this.speed);
    NbtList list = new NbtList();
    for (PathCheckpoint checkpoint : this.checkpoints) {
      list.add(checkpoint.toNBT());
    }
    nbt.put(CHECKPOINTS_TAG_KEY, list);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    //noinspection ConstantConditions
    this.orbID = NbtHelper.toUuid(nbt.get(ORB_ID_TAG_KEY));
    this.active = nbt.getBoolean(ACTIVE_TAG_KEY);
    this.loops = nbt.getBoolean(LOOPS_TAG_KEY);
    this.invisible = nbt.getBoolean(INVISIBLE_TAG_KEY);
    this.lightLevel = nbt.getInt(LIGHT_LEVEL_TAG_KEY);
    this.speed = nbt.getDouble(SPEED_TAG_KEY);
    this.checkpoints = new LinkedList<>();
    for (NbtElement tag : nbt.getList(CHECKPOINTS_TAG_KEY, NbtElement.COMPOUND_TYPE)) {
      this.checkpoints.add(new PathCheckpoint((NbtCompound) tag));
    }
  }

  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt() {
    return this.createNbt();
  }
}
