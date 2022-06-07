package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * This class represents a vertical quarter of a block.
 */
public class VerticalQuarterBlock extends Block implements Colored, Waterloggable {
  public static final EnumProperty<Position> POSITION = EnumProperty.of("position", Position.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  private static final VoxelShape NORTH_WEST = createCuboidShape(0, 0, 0, 8, 16, 8);
  private static final VoxelShape NORTH = createCuboidShape(4, 0, 0, 12, 16, 8);
  private static final VoxelShape NORTH_EAST = createCuboidShape(8, 0, 0, 16, 16, 8);

  private static final VoxelShape SOUTH_EAST = createCuboidShape(8, 0, 8, 16, 16, 16);
  private static final VoxelShape SOUTH = createCuboidShape(4, 0, 8, 12, 16, 16);
  private static final VoxelShape SOUTH_WEST = createCuboidShape(0, 0, 8, 8, 16, 16);

  private static final VoxelShape WEST = createCuboidShape(0, 0, 4, 8, 16, 12);

  private static final VoxelShape EAST = createCuboidShape(8, 0, 4, 16, 16, 12);

  private final BlockColor color;

  public VerticalQuarterBlock(final BlockColor color) {
    super(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE));
    this.color = color;
    this.setDefaultState(this.getDefaultState()
        .with(POSITION, Position.NORTH)
        .with(WATERLOGGED, false));
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(POSITION, WATERLOGGED));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(POSITION)) {
      case NORTH_WEST -> NORTH_WEST;
      case NORTH -> NORTH;
      case NORTH_EAST -> NORTH_EAST;
      case EAST -> EAST;
      case SOUTH_EAST -> SOUTH_EAST;
      case SOUTH -> SOUTH;
      case SOUTH_WEST -> SOUTH_WEST;
      case WEST -> WEST;
    };
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    double hitX = ctx.getHitPos().x - ctx.getBlockPos().getX();
    double hitZ = ctx.getHitPos().z - ctx.getBlockPos().getZ();
    Position position;
    //noinspection ConstantConditions
    switch (ctx.getPlayer().getHorizontalFacing()) {
      case NORTH -> {
        if (hitX < 0.33) {
          position = Position.NORTH_WEST;
        } else if (hitX > 0.67) {
          position = Position.NORTH_EAST;
        } else {
          position = Position.NORTH;
        }
      }
      case SOUTH -> {
        if (hitX < 0.33) {
          position = Position.SOUTH_WEST;
        } else if (hitX > 0.67) {
          position = Position.SOUTH_EAST;
        } else {
          position = Position.SOUTH;
        }
      }
      case WEST -> {
        if (hitZ < 0.33) {
          position = Position.NORTH_WEST;
        } else if (hitZ > 0.67) {
          position = Position.SOUTH_WEST;
        } else {
          position = Position.WEST;
        }
      }
      case EAST -> {
        if (hitZ < 0.33) {
          position = Position.NORTH_EAST;
        } else if (hitZ > 0.67) {
          position = Position.SOUTH_EAST;
        } else {
          position = Position.EAST;
        }
      }
      default -> throw new IllegalArgumentException("invalid player horizontal facing");
    }
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    return this.getDefaultState()
        .with(POSITION, position)
        .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
  }

  @SuppressWarnings("deprecation")
  @Override
  public FluidState getFluidState(BlockState state) {
    if (state.get(WATERLOGGED)) {
      return Fluids.WATER.getStill(false);
    }
    return super.getFluidState(state);
  }

  public enum Position implements StringIdentifiable {
    NORTH_WEST("north_west"),
    NORTH("north"),
    NORTH_EAST("north_east"),
    EAST("east"),
    SOUTH_EAST("south_east"),
    SOUTH("south"),
    SOUTH_WEST("south_west"),
    WEST("west");

    private final String name;

    Position(String name) {
      this.name = name;
    }

    @Override
    public String asString() {
      return this.name;
    }
  }
}
