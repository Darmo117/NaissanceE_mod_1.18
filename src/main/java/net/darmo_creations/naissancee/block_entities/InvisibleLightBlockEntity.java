package net.darmo_creations.naissancee.block_entities;

import net.darmo_creations.naissancee.block_entities.renderers.InvisibleLightBlockEntityRenderer;
import net.darmo_creations.naissancee.blocks.InvisibleLightBlock;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Dummy block entity for {@link ModBlocks#INVISIBLE_LIGHT}.
 * Its only purpose is to render the block’s current light level when a player holds specific items.
 *
 * @see InvisibleLightBlockEntityRenderer
 * @see InvisibleLightBlock
 */
public class InvisibleLightBlockEntity extends BlockEntity {
  public InvisibleLightBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.INVISIBLE_LIGHT_BLOCK, pos, state);
  }
}
