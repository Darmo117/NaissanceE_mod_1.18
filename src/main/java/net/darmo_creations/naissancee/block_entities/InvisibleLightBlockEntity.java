package net.darmo_creations.naissancee.block_entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class InvisibleLightBlockEntity extends BlockEntity {
  public InvisibleLightBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.INVISIBLE_LIGHT_BLOCK, pos, state);
  }
}
