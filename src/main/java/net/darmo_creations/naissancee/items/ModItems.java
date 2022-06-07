package net.darmo_creations.naissancee.items;

import net.darmo_creations.naissancee.NaissanceE;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public final class ModItems {
  public static final Item BARRIER_STATE_TOGGLER =
      register("barrier_state_toggler", new PassableStateTogglerItem(new FabricItemSettings().group(NaissanceE.TECHNICAL_GROUP)));
  public static final Item INVISIBLE_LIGHT_TWEAKER =
      register("invisible_light_tweaker", new InvisibleLightTweakerItem(new FabricItemSettings().group(NaissanceE.TECHNICAL_GROUP)));

  private static <T extends Item> T register(final String name, T item) {
    return Registry.register(Registry.ITEM, new Identifier(NaissanceE.MODID, name), item);
  }

  /**
   * Dummy method called from {@link NaissanceE#onInitialize()} to register items:
   * it forces the class to be loaded during mod initialization, while the registries are unlocked.
   */
  public static void init() {
  }

  private ModItems() {
  }
}
