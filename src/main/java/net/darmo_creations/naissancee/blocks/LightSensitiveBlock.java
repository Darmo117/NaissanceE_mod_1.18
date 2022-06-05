package net.darmo_creations.naissancee.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface LightSensitiveBlock<T extends LightSensitiveBlock<T>> {
  static AbstractBlock.Settings getSettings(AbstractBlock.Settings settings, final boolean isPassable) {
    return isPassable ? settings.nonOpaque() : settings;
  }

  void toggleState(BlockState state, World world, BlockPos pos);

  boolean isPassable();

  T getCounterpartBlock();

  void setCounterpartBlock(T block);
}
