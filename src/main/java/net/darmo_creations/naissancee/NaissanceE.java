package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.blocks.BlockColor;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.commands.SetPassableCommand;
import net.darmo_creations.naissancee.dimensions.VoidDimensionEffects;
import net.darmo_creations.naissancee.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.mixin.client.rendering.DimensionEffectsAccessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/**
 * Mod’s main class. Common initializer for both client and server.
 */
// TODO mod icon
public class NaissanceE implements ModInitializer {
  public static final String MODID = "naissancee";

  // Creative mode’s item groups
  public static final ItemGroup BLOCKS_GROUP = FabricItemGroupBuilder.build(
      new Identifier(MODID, "building"),
      () -> new ItemStack(ModBlocks.COLORED_LIGHT_SENSITIVE_BARRIERS.get(BlockColor.LIGHT_GRAY))
  );
  public static final ItemGroup TECHNICAL_GROUP = FabricItemGroupBuilder.build(
      new Identifier(MODID, "technical"),
      () -> new ItemStack(ModItems.INVISIBLE_LIGHT_TWEAKER)
  );
  public static final ItemGroup CREATURES_GROUP = FabricItemGroupBuilder.build(
      new Identifier(MODID, "creatures"),
      () -> new ItemStack(ModBlocks.LIVING_BLOCK)
  );

  public static final Identifier VOID_DIMENSION_EFFECTS_KEY = new Identifier(MODID, "void");

  @Override
  public void onInitialize() {
    ModBlocks.init();
    ModItems.init();
    ModBlockEntities.init();
    // Inject custom dimension effects. Custom dimension and dimension type are added through datapack.
    DimensionEffectsAccessor.getIdentifierMap().put(VOID_DIMENSION_EFFECTS_KEY, new VoidDimensionEffects());
    this.registerCommands();
  }

  /**
   * Registers all custom commands.
   */
  private void registerCommands() {
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> SetPassableCommand.register(dispatcher));
  }
}
