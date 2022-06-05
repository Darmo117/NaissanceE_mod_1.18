package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.NaissanceE;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public final class ModBlocks {
  // Plain blocks
  public static final Block BLACK_BLOCK = register("black_block", new PlainBlock(BlockColor.BLACK));
  public static final Block GRAY_BLOCK = register("gray_block", new PlainBlock(BlockColor.GRAY));
  public static final Block LIGHT_GRAY_BLOCK = register("light_gray_block", new PlainBlock(BlockColor.LIGHT_GRAY));
  public static final Block WHITE_BLOCK = register("white_block", new PlainBlock(BlockColor.WHITE));

  // Light-sensitive blocks
  public static final LightSensitiveBarrierBlock BLACK_LIGHT_SENSITIVE_BARRIER = register("black_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.BLACK, false));
  public static final LightSensitiveBarrierBlock BLACK_LIGHT_SENSITIVE_BARRIER_PASSABLE = register("black_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.BLACK, true));
  public static final LightSensitiveBarrierBlock GRAY_LIGHT_SENSITIVE_BARRIER = register("gray_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.GRAY, false));
  public static final LightSensitiveBarrierBlock GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE = register("gray_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.GRAY, true));
  public static final LightSensitiveBarrierBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER = register("light_gray_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.LIGHT_GRAY, false));
  public static final LightSensitiveBarrierBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE = register("light_gray_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.LIGHT_GRAY, true));
  public static final LightSensitiveBarrierBlock WHITE_LIGHT_SENSITIVE_BARRIER = register("white_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.WHITE, false));
  public static final LightSensitiveBarrierBlock WHITE_LIGHT_SENSITIVE_BARRIER_PASSABLE = register("white_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.WHITE, true));

  // Light-sensitive horizontal slabs
  public static final LightSensitiveBarrierSlabBlock BLACK_LIGHT_SENSITIVE_BARRIER_SLAB = register("black_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.BLACK, false));
  public static final LightSensitiveBarrierSlabBlock BLACK_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE = register("black_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.BLACK, true));
  public static final LightSensitiveBarrierSlabBlock GRAY_LIGHT_SENSITIVE_BARRIER_SLAB = register("gray_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.GRAY, false));
  public static final LightSensitiveBarrierSlabBlock GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE = register("gray_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.GRAY, true));
  public static final LightSensitiveBarrierSlabBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB = register("light_gray_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.LIGHT_GRAY, false));
  public static final LightSensitiveBarrierSlabBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE = register("light_gray_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.LIGHT_GRAY, true));
  public static final LightSensitiveBarrierSlabBlock WHITE_LIGHT_SENSITIVE_BARRIER_SLAB = register("white_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.WHITE, false));
  public static final LightSensitiveBarrierSlabBlock WHITE_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE = register("white_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.WHITE, true));

  // Light-sensitive vertical slabs

  // TODO vertical slabs, slabs, stairs, corners, ladders, light orb controller, etc.
  // TODO doors
  // TODO light blocks

  public static <T extends Block> T register(final String name, final T block) {
    return register(name, block, true);
  }

  public static <T extends Block> T register(final String name, final T block, final boolean generateItem) {
    Registry.register(Registry.BLOCK, new Identifier(NaissanceE.MODID, name), block);
    if (generateItem) {
      Registry.register(Registry.ITEM, new Identifier(NaissanceE.MODID, name), new BlockItem(block, new FabricItemSettings().group(NaissanceE.BLOCKS_GROUP)));
    }
    return block;
  }

  public static void init() {
    BLACK_LIGHT_SENSITIVE_BARRIER.setCounterpartBlock(BLACK_LIGHT_SENSITIVE_BARRIER_PASSABLE);
    GRAY_LIGHT_SENSITIVE_BARRIER.setCounterpartBlock(GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE);
    LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER.setCounterpartBlock(LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE);
    WHITE_LIGHT_SENSITIVE_BARRIER.setCounterpartBlock(WHITE_LIGHT_SENSITIVE_BARRIER_PASSABLE);

    BLACK_LIGHT_SENSITIVE_BARRIER_SLAB.setCounterpartBlock(BLACK_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE);
    GRAY_LIGHT_SENSITIVE_BARRIER_SLAB.setCounterpartBlock(GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE);
    LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB.setCounterpartBlock(LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE);
    WHITE_LIGHT_SENSITIVE_BARRIER_SLAB.setCounterpartBlock(WHITE_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE);
  }

  public static void registerBlockRenderLayers() {
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_LIGHT_SENSITIVE_BARRIER_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_LIGHT_SENSITIVE_BARRIER_PASSABLE, RenderLayer.getTranslucent());

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE, RenderLayer.getTranslucent());
  }

  private ModBlocks() {
  }
}
