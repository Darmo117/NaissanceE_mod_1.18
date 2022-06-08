package net.darmo_creations.naissancee.entities;

import net.darmo_creations.naissancee.Utils;
import net.darmo_creations.naissancee.block_entities.LightOrbControllerBlockEntity;
import net.darmo_creations.naissancee.block_entities.PathCheckpoint;
import net.darmo_creations.naissancee.blocks.LightOrbControllerBlock;
import net.darmo_creations.naissancee.blocks.LightOrbSourceBlock;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.network.ServerToClientPacketFactory;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * This class represents the moving light orbs found throughout NaissanceE.
 * <p>
 * Orbs place invisible light-emitting blocks wherever they pass through.
 * If the block an orb is currentry in is not air, no light block is placed.
 * <p>
 * Orbs are controlled by controller blocks (one for each) that hold all necessary data.
 * As such, orb entities do not hold any data except for the next checkpoint they must
 * go to and the position of their controller block.
 * <p>
 * Orbs move in straight lines at constant speed between each checkpoint in their path.
 * <p>
 * Light orbs do not have a rendered model but spawn white smoke particles instead.
 *
 * @see LightOrbSourceBlock
 * @see LightOrbControllerBlock
 * @see LightOrbControllerBlockEntity
 */
public class LightOrbEntity extends Entity {
  public static final String CONTROLLER_POS_TAG_KEY = "ControllerPos";
  public static final String NEXT_CP_INDEX_TAG_KEY = "NextCheckpointIndex";

  /**
   * Position of the associated controller block.
   */
  private static final TrackedData<BlockPos> CONTROLLER_POS = DataTracker.registerData(LightOrbEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
  /**
   * Index of the next checkpoint; -1 if there is none.
   */
  private static final TrackedData<Integer> NEXT_CHECKPOINT_INDEX = DataTracker.registerData(LightOrbEntity.class, TrackedDataHandlerRegistry.INTEGER);
  /**
   * Whether the orb should wait for player collision to move again while stopped.
   */
  private static final TrackedData<Boolean> WAIT_FOR_PLAYER = DataTracker.registerData(LightOrbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

  private int timeToWait;

  /**
   * Required by registry, invoked on client.
   */
  public LightOrbEntity(EntityType<LightOrbEntity> entityType, World world) {
    super(entityType, world);
  }

  /**
   * Create a light orb entity for the given controller’s tile entity.
   *
   * @param world      The world this entity belongs to.
   * @param controller The controller’s tile entity.
   */
  public LightOrbEntity(World world, LightOrbControllerBlockEntity controller) {
    super(ModEntities.LIGHT_ORB, world);
    this.dataTracker.set(CONTROLLER_POS, controller.getPos());
  }

  @Override
  protected void initDataTracker() {
    this.setInvulnerable(true);
    this.setSilent(true);
    this.setNoGravity(true);
    this.dataTracker.startTracking(CONTROLLER_POS, new BlockPos(0, 0, 0));
    this.dataTracker.startTracking(NEXT_CHECKPOINT_INDEX, 0);
    this.dataTracker.startTracking(WAIT_FOR_PLAYER, true);
  }

  /**
   * Initializes this entity.
   * Actually sets the next checkpoint to the one directly after the start checkpoint if it exists.
   */
  public void init() {
    LightOrbControllerBlockEntity controller = this.controller();
    if (controller != null) { // Controller block may have been removed while world was unloaded
      this.dataTracker.set(NEXT_CHECKPOINT_INDEX, controller.getNextCheckpoint(0).map(Pair::getKey).orElse(-1));
    }
  }

  @Override
  public Packet<?> createSpawnPacket() {
    return ServerToClientPacketFactory.createEntitySpawnPacket(this);
  }

  @Override
  public void updateVelocity(float speed, Vec3d movementInput) {
    super.updateVelocity(speed, movementInput);
  }

  @Override
  public void tick() {
    this.prevX = this.getX();
    this.prevY = this.getY();
    this.prevZ = this.getZ();

    LightOrbControllerBlockEntity controller = this.controller();
    if (controller == null) { // Account for delay between server and client entity removal
      return;
    }

    if (!controller.isEntityInvisible()) {
      this.world.addParticle(ParticleTypes.END_ROD, this.getX(), this.getY() + 0.5, this.getZ(), 0, 0, 0);
    }

    BlockPos previousTilePos = this.getBlockPos();

    if (controller.isActive()) {
      Optional<PathCheckpoint> next = this.nextCheckpoint();
      if (next.isEmpty()) {
        this.stop();
      } else if (!this.isStopped()) {
        PathCheckpoint nextCheckpoint = next.get();
        double nextX = this.getX() + this.getVelocity().x;
        double nextY = this.getY() + this.getVelocity().y;
        double nextZ = this.getZ() + this.getVelocity().z;
        BlockPos nextPos = new BlockPos(nextX, nextY, nextZ).add(0.5, 0, 0.5);
        BlockPos currentPos = this.getBlockPos();
        BlockPos nextCheckpointPos = nextCheckpoint.getPos().add(0.5, 0, 0.5);
        boolean reachedNextCP = currentPos.getSquaredDistance(nextPos) > currentPos.getSquaredDistance(nextCheckpointPos);
        if (reachedNextCP) {
          nextX = nextCheckpointPos.getX() + 0.5;
          nextY = nextCheckpointPos.getY();
          nextZ = nextCheckpointPos.getZ() + 0.5;
        }

        this.setPosition(nextX, nextY, nextZ);

        if (reachedNextCP) {
          if (nextCheckpoint.isStop()) {
            this.dataTracker.set(WAIT_FOR_PLAYER, true);
            this.stop();
          }
          this.timeToWait = nextCheckpoint.getTicksToWait();
          controller.getNextCheckpoint(this.dataTracker.get(NEXT_CHECKPOINT_INDEX)).ifPresent(p -> {
            this.dataTracker.set(NEXT_CHECKPOINT_INDEX, p.getKey());
            if (!nextCheckpoint.isStop()) {
              if (this.timeToWait == 0) {
                this.updateMotion(p.getValue());
              } else {
                this.stop();
              }
            }
          });
        }
      } else if (this.timeToWait > 0) {
        this.timeToWait--;
        if (this.timeToWait == 0 && !this.dataTracker.get(WAIT_FOR_PLAYER)) {
          this.nextCheckpoint().ifPresent(this::updateMotion);
        }
      }
    } else {
      this.stop();
    }

    BlockPos tilePos = this.getBlockPos();
    this.placeLight(tilePos);
    if (!tilePos.equals(previousTilePos)) {
      this.removeLight(previousTilePos);
    }

    super.tick();
  }

  /**
   * Set this entity’s motion vector to point towards the given checkpoint from the entity’s current position.
   *
   * @param nextCheckpoint The checkpoint to go to.
   */
  private void updateMotion(PathCheckpoint nextCheckpoint) {
    BlockPos currentPos = this.getBlockPos();
    BlockPos nextCPPos = nextCheckpoint.getPos();
    Vec3i vector = nextCPPos.subtract(currentPos);
    double length = Math.sqrt(nextCPPos.getSquaredDistance(currentPos));
    double speed = this.controller().getSpeed();
    this.setVelocity(
        speed * vector.getX() / length,
        speed * vector.getY() / length,
        speed * vector.getZ() / length
    );
  }

  /**
   * Stop this entity.
   */
  private void stop() {
    this.setVelocity(Vec3d.ZERO);
  }

  /**
   * Whether this entity is not moving.
   */
  private boolean isStopped() {
    return this.getVelocity().length() == 0;
  }

  /**
   * Get next checkpoint. Returns null if there is none.
   */
  private Optional<PathCheckpoint> nextCheckpoint() {
    LightOrbControllerBlockEntity controller = this.controller();
    int i = this.dataTracker.get(NEXT_CHECKPOINT_INDEX);
    return controller != null && i != -1 && i < controller.getCheckpoints().size()
        ? Optional.of(controller.getCheckpoints().get(i)) : Optional.empty();
  }

  /**
   * Get the controller’s tile entity. May be null during entity initialization.
   */
  private LightOrbControllerBlockEntity controller() {
    return Utils.getBlockEntity(LightOrbControllerBlockEntity.class, this.world, this.dataTracker.get(CONTROLLER_POS)).orElse(null);
  }

  @Override
  public void onPlayerCollision(PlayerEntity player) {
    super.onPlayerCollision(player);
    this.nextCheckpoint().ifPresent(nextCheckpoint -> {
      if (this.dataTracker.get(WAIT_FOR_PLAYER) && this.controller().isActive() && this.isStopped() && this.timeToWait == 0) {
        this.updateMotion(nextCheckpoint);
        this.dataTracker.set(WAIT_FOR_PLAYER, false);
      }
    });
  }

  @Override
  public void onRemoved() {
    this.removeLight(this.getBlockPos());
    super.onRemoved();
  }

  @Override
  protected void readCustomDataFromNbt(NbtCompound nbt) {
    this.dataTracker.set(CONTROLLER_POS, NbtHelper.toBlockPos(nbt.getCompound(CONTROLLER_POS_TAG_KEY)));
    this.dataTracker.set(NEXT_CHECKPOINT_INDEX, nbt.getInt(NEXT_CP_INDEX_TAG_KEY));
  }

  @Override
  protected void writeCustomDataToNbt(NbtCompound nbt) {
    nbt.put(CONTROLLER_POS_TAG_KEY, NbtHelper.fromBlockPos(this.dataTracker.get(CONTROLLER_POS)));
    nbt.putInt(NEXT_CP_INDEX_TAG_KEY, this.dataTracker.get(NEXT_CHECKPOINT_INDEX));
  }

  /**
   * Place a light block at the given position.
   */
  private void placeLight(BlockPos pos) {
    if (this.world.getBlockState(pos).getBlock() == Blocks.AIR) {
      this.world.setBlockState(pos, ModBlocks.LIGHT_ORB_SOURCE.getDefaultState()
          .with(LightOrbSourceBlock.LIGHT_LEVEL, this.controller().getLightLevel()));
    }
  }

  /**
   * Remove light block at given position.
   */
  private void removeLight(BlockPos pos) {
    if (this.world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_ORB_SOURCE) {
      this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
  }
}
