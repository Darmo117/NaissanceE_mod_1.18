package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.block_entities.InvisibleLightBlockEntity;
import net.darmo_creations.naissancee.items.InvisibleLightTweakerItem;
import net.darmo_creations.naissancee.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
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
  private static final VoxelShape SHAPE = Block.createCuboidShape(4, 4, 4, 12, 12, 12);

  public InvisibleLightBlock() {
    super(FabricBlockSettings.of(Material.AIR)
        .sounds(BlockSoundGroup.GLASS)
        .nonOpaque()
        .noCollision());
    this.setDefaultState(this.getDefaultState().with(LIGHT_LEVEL, 15));
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
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new InvisibleLightBlockEntity(pos, state);
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }
}
