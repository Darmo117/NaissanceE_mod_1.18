package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * A block representing the grid of a small to medium vent.
 */
public class VentGridBlock extends HorizontalFacingBlock implements Colored, Waterloggable {
  public static final EnumProperty<WallMountLocation> FACE = Properties.WALL_MOUNT_LOCATION;
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  private static final VoxelShape NORTH_WALL_SHAPE = VoxelShapes.union(
      createCuboidShape(0, 2, 4, 16, 6, 8),
      createCuboidShape(0, 10, 4, 16, 14, 8));
  private static final VoxelShape SOUTH_WALL_SHAPE = VoxelShapes.union(
      createCuboidShape(0, 2, 8, 16, 6, 12),
      createCuboidShape(0, 10, 8, 16, 14, 12));
  private static final VoxelShape WEST_WALL_SHAPE = VoxelShapes.union(
      createCuboidShape(4, 2, 0, 8, 6, 16),
      createCuboidShape(4, 10, 0, 8, 14, 16));
  private static final VoxelShape EAST_WALL_SHAPE = VoxelShapes.union(
      createCuboidShape(8, 2, 0, 12, 6, 16),
      createCuboidShape(8, 10, 0, 12, 14, 16));

  private static final VoxelShape NS_FLOOR_SHAPE = VoxelShapes.union(
      createCuboidShape(0, 4, 2, 16, 8, 6),
      createCuboidShape(0, 4, 10, 16, 8, 14));
  private static final VoxelShape EW_FLOOR_SHAPE = VoxelShapes.union(
      createCuboidShape(2, 4, 0, 6, 8, 16),
      createCuboidShape(10, 4, 0, 14, 8, 16));

  private static final VoxelShape NS_CEILING_SHAPE = VoxelShapes.union(
      createCuboidShape(0, 8, 2, 16, 12, 6),
      createCuboidShape(0, 8, 10, 16, 12, 14));
  private static final VoxelShape EW_CEILING_SHAPE = VoxelShapes.union(
      createCuboidShape(2, 8, 0, 6, 12, 16),
      createCuboidShape(10, 8, 0, 14, 12, 16));

  private final BlockColor color;

  public VentGridBlock(final BlockColor color) {
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE)));
    this.color = color;
    this.setDefaultState(this.getDefaultState()
        .with(FACING, Direction.NORTH)
        .with(FACE, WallMountLocation.WALL)
        .with(WATERLOGGED, false));
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(FACING, FACE, WATERLOGGED));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    Direction facing = state.get(FACING);
    return switch (state.get(FACE)) {
      case WALL -> switch (facing) {
        case NORTH -> NORTH_WALL_SHAPE;
        case SOUTH -> SOUTH_WALL_SHAPE;
        case WEST -> WEST_WALL_SHAPE;
        case EAST -> EAST_WALL_SHAPE;
        default -> VoxelShapes.empty();
      };
      case FLOOR -> {
        if (facing.getAxis() == Direction.Axis.Z) {
          yield NS_FLOOR_SHAPE;
        } else {
          yield EW_FLOOR_SHAPE;
        }
      }
      case CEILING -> {
        if (facing.getAxis() == Direction.Axis.Z) {
          yield NS_CEILING_SHAPE;
        } else {
          yield EW_CEILING_SHAPE;
        }
      }
    };
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    Direction facing = ctx.getSide();
    BlockState state = this.getDefaultState();
    if (facing.getAxis() != Direction.Axis.Y) {
      state = state
          .with(FACING, facing.getOpposite())
          .with(FACE, WallMountLocation.WALL);
    } else {
      state = state
          .with(FACING, ctx.getPlayerFacing())
          .with(FACE, facing == Direction.DOWN ? WallMountLocation.CEILING : WallMountLocation.FLOOR);
    }
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    return state.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
  }

  @SuppressWarnings("deprecation")
  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }
}
