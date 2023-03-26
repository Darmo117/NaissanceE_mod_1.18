package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.block_entities.InvisibleLightBlockEntity;
import net.darmo_creations.naissancee.items.InvisibleLightTweakerItem;
import net.darmo_creations.naissancee.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

/**
 * An invisible, non-tengible block that emits light. Light level can be set using
 * a {@link ModItems#INVISIBLE_LIGHT_TWEAKER} item.
 *
 * @see InvisibleLightBlockEntity
 * @see InvisibleLightTweakerItem
 */
public class InvisibleLightBlock extends VariableLightBlock implements BlockEntityProvider {
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  private static final VoxelShape SHAPE = createCuboidShape(4, 4, 4, 12, 12, 12);

  public InvisibleLightBlock() {
    super(FabricBlockSettings.of(Material.AIR)
        .sounds(BlockSoundGroup.GLASS)
        .air());
    this.setDefaultState(this.getStateManager().getDefaultState().with(LIGHT_LEVEL, 15).with(WATERLOGGED, false));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder.add(WATERLOGGED));
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.empty();
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
    //noinspection ConstantConditions
    return super.getPlacementState(ctx).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
  }

  @SuppressWarnings("deprecation")
  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new InvisibleLightBlockEntity(pos, state);
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }
}
