package net.darmo_creations.naissancee.entities;

import net.darmo_creations.naissancee.NaissanceE;
import net.darmo_creations.naissancee.entities.renderers.LightOrbEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class ModEntities {
  public static final EntityType<LightOrbEntity> LIGHT_ORB =
      register("light_orb", SpawnGroup.MISC, LightOrbEntity::new, EntityDimensions.fixed(0.25F, 0.25F), 100, 10);

  /**
   * Registers an entity type.
   *
   * @param name       Entity’s name.
   * @param spawnGroup Entity’s spawn group.
   * @param factory    A factory for the entity type.
   * @param size       Entity’s size.
   * @param <T>        Type of the entity type.
   * @param <U>        Type of the associated entity.
   * @return The registered entity type.
   */
  private static <T extends EntityType<U>, U extends Entity> T register(
      final String name,
      final SpawnGroup spawnGroup,
      final EntityType.EntityFactory<U> factory,
      final EntityDimensions size,
      final int trackRange,
      final int trackingUpdateRate
  ) {
    //noinspection unchecked
    return (T) Registry.register(
        Registry.ENTITY_TYPE,
        new Identifier(NaissanceE.MODID, name),
        FabricEntityTypeBuilder.create(spawnGroup, factory)
            .dimensions(size)
            .trackRangeBlocks(trackRange)
            .trackedUpdateRate(trackingUpdateRate)
            .build()
    );
  }

  /**
   * Dummy method called from {@link NaissanceE#onInitialize()} to register entity types:
   * it forces the class to be loaded during mod initialization, while the registries are unlocked.
   * <p>
   * Must be called on both clients and server.
   */
  public static void init() {
  }

  /**
   * Registers entity renderers.
   * Must be called on client only.
   */
  public static void registerRenderers() {
    EntityRendererRegistry.register(LIGHT_ORB, LightOrbEntityRenderer::new);
  }

  private ModEntities() {
  }
}
