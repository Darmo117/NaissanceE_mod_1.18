package net.darmo_creations.naissancee.blocks;

import net.darmo_creations.naissancee.NaissanceE;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public final class ModBlocks {
  // Plain blocks
  public static final ColoredBlock BLACK_BLOCK = register("black_block", new ColoredBlock(BlockColor.BLACK));
  public static final ColoredBlock GRAY_BLOCK = register("gray_block", new ColoredBlock(BlockColor.GRAY));
  public static final ColoredBlock LIGHT_GRAY_BLOCK = register("light_gray_block", new ColoredBlock(BlockColor.LIGHT_GRAY));
  public static final ColoredBlock WHITE_BLOCK = register("white_block", new ColoredBlock(BlockColor.WHITE));

  // Plain stairs
  public static final Block BLACK_STAIRS = register("black_stairs", new ColoredStairsBlock(BLACK_BLOCK));
  public static final Block GRAY_STAIRS = register("gray_stairs", new ColoredStairsBlock(GRAY_BLOCK));
  public static final Block LIGHT_GRAY_STAIRS = register("light_gray_stairs", new ColoredStairsBlock(LIGHT_GRAY_BLOCK));
  public static final Block WHITE_STAIRS = register("white_stairs", new ColoredStairsBlock(WHITE_BLOCK));

  // Plain slabs
  public static final Block BLACK_SLAB = register("black_slab", new ColoredSlabBlock(BlockColor.BLACK));
  public static final Block GRAY_SLAB = register("gray_slab", new ColoredSlabBlock(BlockColor.GRAY));
  public static final Block LIGHT_GRAY_SLAB = register("light_gray_slab", new ColoredSlabBlock(BlockColor.LIGHT_GRAY));
  public static final Block WHITE_SLAB = register("white_slab", new ColoredSlabBlock(BlockColor.WHITE));

  // Plain vertical slabs
  public static final Block BLACK_VSLAB = register("black_vslab", new ColoredVerticalSlabBlock(BlockColor.BLACK));
  public static final Block GRAY_VSLAB = register("gray_vslab", new ColoredVerticalSlabBlock(BlockColor.GRAY));
  public static final Block LIGHT_GRAY_VSLAB = register("light_gray_vslab", new ColoredVerticalSlabBlock(BlockColor.LIGHT_GRAY));
  public static final Block WHITE_VSLAB = register("white_vslab", new ColoredVerticalSlabBlock(BlockColor.WHITE));

  // Light-sensitive blocks
  public static final LightSensitiveBarrierBlock BLACK_LIGHT_SENSITIVE_BARRIER =
      register("black_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.BLACK, false));
  public static final LightSensitiveBarrierBlock BLACK_LIGHT_SENSITIVE_BARRIER_PASSABLE =
      register("black_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.BLACK, true));
  public static final LightSensitiveBarrierBlock GRAY_LIGHT_SENSITIVE_BARRIER =
      register("gray_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.GRAY, false));
  public static final LightSensitiveBarrierBlock GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE =
      register("gray_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.GRAY, true));
  public static final LightSensitiveBarrierBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER =
      register("light_gray_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.LIGHT_GRAY, false));
  public static final LightSensitiveBarrierBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_PASSABLE =
      register("light_gray_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.LIGHT_GRAY, true));
  public static final LightSensitiveBarrierBlock WHITE_LIGHT_SENSITIVE_BARRIER =
      register("white_light_sensitive_barrier", new LightSensitiveBarrierBlock(BlockColor.WHITE, false));
  public static final LightSensitiveBarrierBlock WHITE_LIGHT_SENSITIVE_BARRIER_PASSABLE =
      register("white_light_sensitive_barrier_passable", new LightSensitiveBarrierBlock(BlockColor.WHITE, true));

  // Light-sensitive slabs
  public static final LightSensitiveBarrierSlabBlock BLACK_LIGHT_SENSITIVE_BARRIER_SLAB =
      register("black_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.BLACK, false));
  public static final LightSensitiveBarrierSlabBlock BLACK_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE =
      register("black_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.BLACK, true));
  public static final LightSensitiveBarrierSlabBlock GRAY_LIGHT_SENSITIVE_BARRIER_SLAB =
      register("gray_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.GRAY, false));
  public static final LightSensitiveBarrierSlabBlock GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE =
      register("gray_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.GRAY, true));
  public static final LightSensitiveBarrierSlabBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB =
      register("light_gray_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.LIGHT_GRAY, false));
  public static final LightSensitiveBarrierSlabBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE =
      register("light_gray_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.LIGHT_GRAY, true));
  public static final LightSensitiveBarrierSlabBlock WHITE_LIGHT_SENSITIVE_BARRIER_SLAB =
      register("white_light_sensitive_barrier_slab", new LightSensitiveBarrierSlabBlock(BlockColor.WHITE, false));
  public static final LightSensitiveBarrierSlabBlock WHITE_LIGHT_SENSITIVE_BARRIER_SLAB_PASSABLE =
      register("white_light_sensitive_barrier_slab_passable", new LightSensitiveBarrierSlabBlock(BlockColor.WHITE, true));

  // Light-sensitive vertical slabs
  public static final LightSensitiveBarrierVerticalSlabBlock BLACK_LIGHT_SENSITIVE_BARRIER_VSLAB =
      register("black_light_sensitive_barrier_vslab", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.BLACK, false));
  public static final LightSensitiveBarrierVerticalSlabBlock BLACK_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE =
      register("black_light_sensitive_barrier_vslab_passable", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.BLACK, true));
  public static final LightSensitiveBarrierVerticalSlabBlock GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB =
      register("gray_light_sensitive_barrier_vslab", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.GRAY, false));
  public static final LightSensitiveBarrierVerticalSlabBlock GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE =
      register("gray_light_sensitive_barrier_vslab_passable", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.GRAY, true));
  public static final LightSensitiveBarrierVerticalSlabBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB =
      register("light_gray_light_sensitive_barrier_vslab", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.LIGHT_GRAY, false));
  public static final LightSensitiveBarrierVerticalSlabBlock LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE =
      register("light_gray_light_sensitive_barrier_vslab_passable", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.LIGHT_GRAY, true));
  public static final LightSensitiveBarrierVerticalSlabBlock WHITE_LIGHT_SENSITIVE_BARRIER_VSLAB =
      register("white_light_sensitive_barrier_vslab", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.WHITE, false));
  public static final LightSensitiveBarrierVerticalSlabBlock WHITE_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE =
      register("white_light_sensitive_barrier_vslab_passable", new LightSensitiveBarrierVerticalSlabBlock(BlockColor.WHITE, true));

  // Creatures
  public static final Block LIVING_BLOCK = register("living_block", new LivingBlock(), NaissanceE.CREATURES_GROUP);
  public static final Block[] CREATURE_BLOCKS = new Block[16];

  static {
    for (int i = 0; i < 16; i++) {
      CREATURE_BLOCKS[i] = register(
          "creature_block_" + i,
          new Block(FabricBlockSettings.of(Material.STONE, MapColor.WHITE).luminance(i)),
          NaissanceE.CREATURES_GROUP
      );
    }
  }

  // Lights
  public static final Block FLOATING_VARIABLE_LIGHT_BLOCK =
      register("floating_variable_light_block", new FloatingVariableLightBlock(), NaissanceE.TECHNICAL_GROUP);
  public static final Block INVISIBLE_LIGHT =
      register("invisible_light", new InvisibleLightBlock(), NaissanceE.TECHNICAL_GROUP);
  public static final Block[] LIGHT_BLOCKS = new Block[15];

  static {
    for (int i = 1; i < 16; i++) {
      LIGHT_BLOCKS[i - 1] = register("light_block_" + i, new Block(FabricBlockSettings.of(Material.STONE, MapColor.WHITE).luminance(i)));
    }
  }

  // Ladders
  public static final Block BLACK_LADDER = register("black_ladder", new TwoPartLadderBlock(BlockColor.BLACK));
  public static final Block GRAY_LADDER = register("gray_ladder", new TwoPartLadderBlock(BlockColor.GRAY));
  public static final Block LIGHT_GRAY_LADDER = register("light_gray_ladder", new TwoPartLadderBlock(BlockColor.LIGHT_GRAY));
  public static final Block WHITE_LADDER = register("white_ladder", new TwoPartLadderBlock(BlockColor.WHITE));
  public static final Block BLACK_HALF_LADDER = register("black_half_ladder", new HalfLadderBlock(BlockColor.BLACK));
  public static final Block GRAY_HALF_LADDER = register("gray_half_ladder", new HalfLadderBlock(BlockColor.GRAY));
  public static final Block LIGHT_GRAY_HALF_LADDER = register("light_gray_half_ladder", new HalfLadderBlock(BlockColor.LIGHT_GRAY));
  public static final Block WHITE_HALF_LADDER = register("white_half_ladder", new HalfLadderBlock(BlockColor.WHITE));

  public static final Block ACTIVATOR_LAMP = register("activator_lamp", new BlockActivatorLamp());

  // TODO corners, posts, walls, light orb controller, etc.
  // TODO doors (make partially openable)

  /**
   * Registers a block and puts it in the Blocks item group.
   *
   * @param name  Block’s name.
   * @param block Block to register.
   * @param <T>   Type of the block to register.
   * @return The registered block.
   */
  private static <T extends Block> T register(final String name, final T block) {
    return register(name, block, NaissanceE.BLOCKS_GROUP);
  }

  /**
   * Registers a block and puts it in the given item group.
   *
   * @param name      Block’s name.
   * @param block     Block to register.
   * @param itemGroup Item group to put the block in.
   * @param <T>       Type of the block to register.
   * @return The registered block.
   */
  private static <T extends Block> T register(final String name, final T block, final ItemGroup itemGroup) {
    Registry.register(Registry.BLOCK, new Identifier(NaissanceE.MODID, name), block);
    Registry.register(Registry.ITEM, new Identifier(NaissanceE.MODID, name), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
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

    BLACK_LIGHT_SENSITIVE_BARRIER_VSLAB.setCounterpartBlock(BLACK_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE);
    GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB.setCounterpartBlock(GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE);
    LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB.setCounterpartBlock(LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE);
    WHITE_LIGHT_SENSITIVE_BARRIER_VSLAB.setCounterpartBlock(WHITE_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE);
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

    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIGHT_GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRAY_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_LIGHT_SENSITIVE_BARRIER_VSLAB_PASSABLE, RenderLayer.getTranslucent());
  }

  private ModBlocks() {
  }
}
