package net.darmo_creations.naissancee.items;

import net.darmo_creations.naissancee.NaissanceE;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Declares all items added by this mod.
 */
@SuppressWarnings("unused")
public final class ModItems {
  // Tools
  public static final Item BARRIER_STATE_TOGGLER =
      register("barrier_state_toggler", new PassableStateTogglerItem(new FabricItemSettings().group(NaissanceE.TECHNICAL_GROUP)));
  public static final Item INVISIBLE_LIGHT_TWEAKER =
      register("invisible_light_tweaker", new InvisibleLightTweakerItem(new FabricItemSettings().group(NaissanceE.TECHNICAL_GROUP)));
  public static final Item LIGHT_ORB_TWEAKER =
      register("light_orb_tweaker", new LightOrbTweakerItem(new FabricItemSettings().group(NaissanceE.TECHNICAL_GROUP)));

  /**
   * Registers an item.
   *
   * @param name Item’s registry name.
   * @param item Item instance.
   * @param <T>  Item’s type.
   * @return The registered item.
   */
  private static <T extends Item> T register(final String name, final T item) {
    return Registry.register(Registry.ITEM, new Identifier(NaissanceE.MODID, name), item);
  }

  /**
   * Dummy method called from {@link NaissanceE#onInitialize()} to register items:
   * it forces the class to be loaded during mod initialization, while the registries are unlocked.
   * <p>
   * Must be called on both clients and server.
   */
  public static void init() {
  }

  private ModItems() {
  }
}
