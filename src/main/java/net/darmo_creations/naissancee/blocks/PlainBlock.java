package net.darmo_creations.naissancee.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

/**
 * A simple full block with no behavior.
 */
public class PlainBlock extends Block implements ColoredBlock {
  private final BlockColor color;

  public PlainBlock(final BlockColor color) {
    super(FabricBlockSettings.of(Material.STONE, color.getMapColor()).sounds(BlockSoundGroup.STONE));
    this.color = color;
  }

  @Override
  public BlockColor getColor() {
    return this.color;
  }
}
