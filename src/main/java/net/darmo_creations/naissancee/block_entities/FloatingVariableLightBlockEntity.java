package net.darmo_creations.naissancee.block_entities;

import net.darmo_creations.naissancee.blocks.FloatingVariableLightBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

/**
 * Tile entity for {@link FloatingVariableLightBlock}.
 * Handles light level change.
 */
public class FloatingVariableLightBlockEntity extends BlockEntity {
  private static final String TIME_TAG_KEY = "Time";
  private static final String INCREASING_TAG_KEY = "Increasing";
  private static final String STOPPED_TAG_KEY = "Stopped";
  private static final String WAIT_NO_COLLISION_TAG_KEY = "WaitForNoCollision";
  private static final String PLAYER_COLLIDING_TAG_KEY = "PlayerColliding";

  public static final int MIN_LIGHT_LEVEL = 4;
  public static final int DELAY = 3;

  private int time;
  private boolean increasing;
  private boolean stopped;
  private boolean waitForNoCollision;
  private boolean playerColliding;

  public FloatingVariableLightBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.FLOATING_VARIABLE_LIGHT_BLOCK, pos, state);
    this.time = DELAY;
    this.increasing = true;
    this.stopped = true;
    this.waitForNoCollision = true;
    this.playerColliding = false;
  }

  /**
   * Called when a player collides the associated block.
   */
  public void onPlayerColliding() {
    this.playerColliding = true;
  }

  public void update() {
    if (this.waitForNoCollision && !this.playerColliding) {
      this.waitForNoCollision = false;
    } else if (this.playerColliding && !this.waitForNoCollision) {
      this.stopped = false;
    }
    if (this.stopped || this.waitForNoCollision) {
      this.playerColliding = false;
      return;
    }

    BlockPos pos = this.getPos();
    //noinspection ConstantConditions
    BlockState state = this.world.getBlockState(pos);

    if (state.getBlock() instanceof FloatingVariableLightBlock) {
      int lightLevel = state.get(FloatingVariableLightBlock.LIGHT_LEVEL);

      if (this.time == 0) {
        if (this.increasing) {
          if (lightLevel < 15) { // Prevent crash when BE is loading while player is colliding block
            this.world.setBlockState(pos, state.with(FloatingVariableLightBlock.LIGHT_LEVEL, ++lightLevel));
          }
          if (lightLevel == 15) {
            this.increasing = false;
            this.stopped = true;
            if (this.playerColliding) {
              this.waitForNoCollision = true;
            }
          }
        } else {
          if (lightLevel > MIN_LIGHT_LEVEL) { // Prevent crash when BE is loading while player is colliding block
            this.world.setBlockState(pos, state.with(FloatingVariableLightBlock.LIGHT_LEVEL, --lightLevel));
          }
          if (lightLevel == MIN_LIGHT_LEVEL) {
            this.increasing = true;
            this.stopped = true;
            if (this.playerColliding) {
              this.waitForNoCollision = true;
            }
          }
        }
        this.time = DELAY;
      } else {
        if (((this.increasing && lightLevel == MIN_LIGHT_LEVEL) || (!this.increasing && lightLevel == 15)) && this.time == DELAY) {
          this.world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
              SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5f, 1, false);
        }
        this.time--;
      }
    }
    this.playerColliding = false;
  }

  @Override
  public void readNbt(NbtCompound compound) {
    super.readNbt(compound);
    this.time = compound.getInt(TIME_TAG_KEY);
    this.increasing = compound.getBoolean(INCREASING_TAG_KEY);
    this.stopped = compound.getBoolean(STOPPED_TAG_KEY);
    this.waitForNoCollision = compound.getBoolean(WAIT_NO_COLLISION_TAG_KEY);
    this.playerColliding = compound.getBoolean(PLAYER_COLLIDING_TAG_KEY);
  }

  @Override
  public void writeNbt(NbtCompound compound) {
    super.writeNbt(compound);
    compound.putInt(TIME_TAG_KEY, this.time);
    compound.putBoolean(INCREASING_TAG_KEY, this.increasing);
    compound.putBoolean(STOPPED_TAG_KEY, this.stopped);
    compound.putBoolean(WAIT_NO_COLLISION_TAG_KEY, this.waitForNoCollision);
    compound.putBoolean(PLAYER_COLLIDING_TAG_KEY, this.playerColliding);
  }
}