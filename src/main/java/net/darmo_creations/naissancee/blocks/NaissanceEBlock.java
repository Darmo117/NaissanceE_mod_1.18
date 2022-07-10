package net.darmo_creations.naissancee.blocks;

import net.minecraft.block.AbstractBlock;

/**
 * Interface used to mark blocks added by this mod.
 */
public interface NaissanceEBlock {
  /**
   * Adds specific settings related to the hardness of the block.
   *
   * @param settings Base block settings.
   * @return The new settings.
   */
  static AbstractBlock.Settings getSettings(AbstractBlock.Settings settings) {
    return settings.strength(-1, 3_600_000).dropsNothing();
  }
}
