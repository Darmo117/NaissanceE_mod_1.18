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
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * This class represents a horizontal quarter of a block.
 */
public class HorizontalQuarterBlock extends Block implements Colored, Waterloggable {
  public static final EnumProperty<Position> POSITION = EnumProperty.of("position", Position.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  private static final VoxelShape NORTH_TOP = createCuboidShape(0, 8, 0, 16, 16, 8);
  private static final VoxelShape NORTH_MIDDLE = createCuboidShape(0, 4, 0, 16, 12, 8);
  private static final VoxelShape NORTH_BOTTOM = createCuboidShape(0, 0, 0, 16, 8, 8);

  private static final VoxelShape SOUTH_TOP = createCuboidShape(0, 8, 8, 16, 16, 16);
  private static final VoxelShape SOUTH_MIDDLE = createCuboidShape(0, 4, 8, 16, 12, 16);
  private static final VoxelShape SOUTH_BOTTOM = createCuboidShape(0, 0, 8, 16, 8, 16);

  private static final VoxelShape WEST_TOP = createCuboidShape(0, 8, 0, 8, 16, 16);
  private static final VoxelShape WEST_MIDDLE = createCuboidShape(0, 4, 0, 8, 12, 16);
  private static final VoxelShape WEST_BOTTOM = createCuboidShape(0, 0, 0, 8, 8, 16);

  private static final VoxelShape EAST_TOP = createCuboidShape(8, 8, 0, 16, 16, 16);
  private static final VoxelShape EAST_MIDDLE = createCuboidShape(8, 4, 0, 16, 12, 16);
  private static final VoxelShape EAST_BOTTOM = createCuboidShape(8, 0, 0, 16, 8, 16);

  private static final VoxelShape X_TOP = createCuboidShape(0, 8, 4, 16, 16, 12);
  private static final VoxelShape X_BOTTOM = createCuboidShape(0, 0, 4, 16, 8, 12);
  private static final VoxelShape Z_TOP = createCuboidShape(4, 8, 0, 12, 16, 16);
  private static final VoxelShape Z_BOTTOM = createCuboidShape(4, 0, 0, 12, 8, 16);

  private final BlockColor color;

  public HorizontalQuarterBlock(final BlockColor color) {
    super(NaissanceEBlock.getSettings(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE)));
    this.color = color;
    this.setDefaultState(this.getDefaultState()
        .with(POSITION, Position.NORTH_BOTTOM)
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
      case NORTH_TOP -> NORTH_TOP;
      case SOUTH_TOP -> SOUTH_TOP;
      case WEST_TOP -> WEST_TOP;
      case EAST_TOP -> EAST_TOP;
      case NORTH_MIDDLE -> NORTH_MIDDLE;
      case SOUTH_MIDDLE -> SOUTH_MIDDLE;
      case WEST_MIDDLE -> WEST_MIDDLE;
      case EAST_MIDDLE -> EAST_MIDDLE;
      case NORTH_BOTTOM -> NORTH_BOTTOM;
      case SOUTH_BOTTOM -> SOUTH_BOTTOM;
      case WEST_BOTTOM -> WEST_BOTTOM;
      case EAST_BOTTOM -> EAST_BOTTOM;
      case X_TOP -> X_TOP;
      case X_BOTTOM -> X_BOTTOM;
      case Z_TOP -> Z_TOP;
      case Z_BOTTOM -> Z_BOTTOM;
    };
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    //noinspection ConstantConditions
    Direction facing = ctx.getPlayer().getHorizontalFacing();
    double hitX = ctx.getHitPos().x - ctx.getBlockPos().getX();
    double hitY = ctx.getHitPos().y - ctx.getBlockPos().getY();
    double hitZ = ctx.getHitPos().z - ctx.getBlockPos().getZ();
    Position position;
    if (hitY < 0.33) {
      if (facing.getAxis() == Direction.Axis.X) {
        if (hitX < 0.33) {
          position = Position.WEST_BOTTOM;
        } else if (hitX > 0.67) {
          position = Position.EAST_BOTTOM;
        } else {
          position = Position.Z_BOTTOM;
        }
      } else {
        if (hitZ < 0.33) {
          position = Position.NORTH_BOTTOM;
        } else if (hitZ > 0.67) {
          position = Position.SOUTH_BOTTOM;
        } else {
          position = Position.X_BOTTOM;
        }
      }
    } else if (hitY > 0.67) {
      if (facing.getAxis() == Direction.Axis.X) {
        if (hitX < 0.33) {
          position = Position.WEST_TOP;
        } else if (hitX > 0.67) {
          position = Position.EAST_TOP;
        } else {
          position = Position.Z_TOP;
        }
      } else {
        if (hitZ < 0.33) {
          position = Position.NORTH_TOP;
        } else if (hitZ > 0.67) {
          position = Position.SOUTH_TOP;
        } else {
          position = Position.X_TOP;
        }
      }
    } else {
      position = switch (facing) {
        case NORTH -> Position.NORTH_MIDDLE;
        case SOUTH -> Position.SOUTH_MIDDLE;
        case WEST -> Position.WEST_MIDDLE;
        case EAST -> Position.EAST_MIDDLE;
        // Should never happen, here to please the almighty compiler
        default -> throw new IllegalArgumentException("invalid player horizontal facing");
      };
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
    NORTH_TOP("north_top"),
    NORTH_MIDDLE("north_middle"),
    NORTH_BOTTOM("north_bottom"),
    SOUTH_TOP("south_top"),
    SOUTH_MIDDLE("south_middle"),
    SOUTH_BOTTOM("south_bottom"),
    WEST_TOP("west_top"),
    WEST_MIDDLE("west_middle"),
    WEST_BOTTOM("west_bottom"),
    EAST_TOP("east_top"),
    EAST_MIDDLE("east_middle"),
    EAST_BOTTOM("east_bottom"),
    X_TOP("x_top"),
    X_BOTTOM("x_bottom"),
    Z_TOP("z_top"),
    Z_BOTTOM("z_bottom");

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
