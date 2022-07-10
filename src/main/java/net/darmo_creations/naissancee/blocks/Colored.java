package net.darmo_creations.naissancee.blocks;

/**
 * Blocks implementing this interface indicate that they are colored.
 *
 * @see BlockColor
 */
public interface Colored extends NaissanceEBlock {
  /**
   * Returns the color of this block.
   */
  BlockColor getColor();
}
