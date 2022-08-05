package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * A block representing a pair of “pipes“ oriented along the y axis.
 */
public class VerticalPipesBlock extends HorizontalFacingBlock implements Colored, Waterloggable {
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
      createCuboidShape(2, 0, 0, 6, 16, 8),
      createCuboidShape(10, 0, 0, 14, 16, 8));
  private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
      createCuboidShape(2, 0, 8, 6, 16, 16),
      createCuboidShape(10, 0, 8, 14, 16, 16));
  private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
      createCuboidShape(0, 0, 2, 8, 16, 6),
      createCuboidShape(0, 0, 10, 8, 16, 14));
  private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
      createCuboidShape(8, 0, 2, 16, 16, 6),
      createCuboidShape(8, 0, 10, 16, 16, 14));

  private final BlockColor color;

  public VerticalPipesBlock(final BlockColor color) {
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE)));
    this.color = color;
    this.setDefaultState(this.getDefaultState()
        .with(FACING, Direction.NORTH)
        .with(WATERLOGGED, false));
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(FACING, WATERLOGGED));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(FACING)) {
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case WEST -> WEST_SHAPE;
      case EAST -> EAST_SHAPE;
      default -> VoxelShapes.empty();
    };
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
}
