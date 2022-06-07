package net.darmo_creations.naissancee.block_entities;

import net.darmo_creations.naissancee.NaissanceE;
import net.darmo_creations.naissancee.block_entities.renderers.InvisibleLightSourceBlockEntityRenderer;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class ModBlockEntities {
  public static final BlockEntityType<InvisibleLightBlockEntity> INVISIBLE_LIGHT_BLOCK =
      register("invisible_light_block", InvisibleLightBlockEntity::new, ModBlocks.INVISIBLE_LIGHT);

  public static <T extends BlockEntityType<U>, U extends BlockEntity> T register(
      final String name, FabricBlockEntityTypeBuilder.Factory<U> factory, final Block... blocks
  ) {
    //noinspection unchecked
    return (T) Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        new Identifier(NaissanceE.MODID, name),
        FabricBlockEntityTypeBuilder.create(factory, blocks).build()
    );
  }

  /**
   * Dummy method called from {@link NaissanceE#onInitialize()} to register block entity types:
   * it forces the class to be loaded during mod initialization, while the registries are unlocked.
   */
  public static void init() {
  }

  /**
   * Registers block entity renderers.
   */
  public static void registerRenderers() {
    BlockEntityRendererRegistry.register(INVISIBLE_LIGHT_BLOCK, InvisibleLightSourceBlockEntityRenderer::new);
  }

  private ModBlockEntities() {
  }
}
