package net.darmo_creations.naissancee.items;

import net.darmo_creations.naissancee.NaissanceE;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public final class ModItems {
  public static final Item PASSABLE_STATE_TOGGLER = register("passable_state_toggler", new PassableStateTogglerItem());

  public static <T extends Item> T register(final String name, T item) {
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
