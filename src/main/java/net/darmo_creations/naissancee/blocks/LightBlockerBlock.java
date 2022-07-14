package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

/**
 * Invisible block that entities can walk through but that blocks light.
 * <p>
 * Implemented with a thin hitbox because having a full hitbox would mess up the rendering of neighboring blocks.
 */
public class LightBlockerBlock extends HorizontalFacingBlock implements NaissanceEBlock, Waterloggable {
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  protected static final VoxelShape NORTH_SHAPE = createCuboidShape(0, 0, 0, 16, 16, 0.1);
  protected static final VoxelShape SOUTH_SHAPE = createCuboidShape(0, 0, 15.9, 16, 16, 16);
  protected static final VoxelShape WEST_SHAPE = createCuboidShape(0, 0, 0, 0.1, 16, 16);
  protected static final VoxelShape EAST_SHAPE = createCuboidShape(15.9, 0, 0, 16, 16, 16);

  public LightBlockerBlock() {
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.AIR).sounds(BlockSoundGroup.STONE)));
    this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(FACING, WATERLOGGED));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.empty();
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(FACING)) {
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case EAST -> EAST_SHAPE;
      case WEST -> WEST_SHAPE;
      default -> VoxelShapes.empty();
    };
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean hasSidedTransparency(BlockState state) {
    return true;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    return this.getDefaultState()
        .with(FACING, ctx.getPlayerFacing())
        .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
  }

  @SuppressWarnings("deprecation")
  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    // Display particle whenever player is holding this block, as with minecraft:barrier and minecraft:light
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player != null && player.isHolding(this.asItem())) {
      world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_MARKER, state),
          pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }
}
