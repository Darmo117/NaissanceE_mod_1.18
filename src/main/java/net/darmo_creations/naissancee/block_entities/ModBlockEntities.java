package net.darmo_creations.naissancee.block_entities;

import net.darmo_creations.naissancee.NaissanceE;
import net.darmo_creations.naissancee.block_entities.renderers.InvisibleLightBlockEntityRenderer;
import net.darmo_creations.naissancee.block_entities.renderers.LightOrbControllerBlockEntityRenderer;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Declares all block entity types added by this mod.
 */
public final class ModBlockEntities {
  public static final BlockEntityType<InvisibleLightBlockEntity> INVISIBLE_LIGHT_BLOCK =
      register("invisible_light_block", InvisibleLightBlockEntity::new, ModBlocks.INVISIBLE_LIGHT);
  public static final BlockEntityType<FloatingVariableLightBlockEntity> FLOATING_VARIABLE_LIGHT_BLOCK =
      register("floating_variable_light", FloatingVariableLightBlockEntity::new, ModBlocks.FLOATING_VARIABLE_LIGHT_BLOCK);
  public static final BlockEntityType<LightOrbControllerBlockEntity> LIGHT_ORB_CONTROLLER =
      register("light_orb_controller", LightOrbControllerBlockEntity::new, ModBlocks.LIGHT_ORB_CONTROLLER);

  /**
   * Registers a block entity type.
   *
   * @param name    Block entity’s name.
   * @param factory A factory for the block entity type.
   * @param blocks  Block to associate to the block entity.
   * @param <T>     Type of the block entity type.
   * @param <U>     Type of the associated block entity.
   * @return The registered block entity type.
   */
  private static <T extends BlockEntityType<U>, U extends BlockEntity> T register(
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
   * <p>
   * Must be called on both clients and server.
   */
  public static void init() {
  }

  /**
   * Registers block entity renderers.
   * Must be called on client only.
   */
  public static void registerRenderers() {
    BlockEntityRendererRegistry.register(INVISIBLE_LIGHT_BLOCK, InvisibleLightBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(LIGHT_ORB_CONTROLLER, LightOrbControllerBlockEntityRenderer::new);
  }

  private ModBlockEntities() {
  }
}
