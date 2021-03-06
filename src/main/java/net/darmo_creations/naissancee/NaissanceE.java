package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.blocks.BlockColor;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.commands.PushCommand;
import net.darmo_creations.naissancee.commands.SetPassableCommand;
import net.darmo_creations.naissancee.dimensions.VoidDimensionEffects;
import net.darmo_creations.naissancee.items.ModItems;
import net.darmo_creations.naissancee.network.C2SPacketFactory;
import net.darmo_creations.naissancee.network.PacketRegistry;
import net.darmo_creations.naissancee.network.packets.LightOrbControllerDataPacket;
import net.darmo_creations.naissancee.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.mixin.client.rendering.DimensionEffectsAccessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mod’s main class. Common initializer for both client and server.
 */
public class NaissanceE implements ModInitializer {
  public static final String MOD_ID = "naissancee";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  // Creative mode’s item groups
  public static final ItemGroup BLOCKS_GROUP = FabricItemGroupBuilder.build(
      new Identifier(MOD_ID, "building"),
      () -> new ItemStack(ModBlocks.COLORED_LIGHT_SENSITIVE_BARRIERS.get(BlockColor.LIGHT_GRAY))
  );
  public static final ItemGroup TECHNICAL_GROUP = FabricItemGroupBuilder.build(
      new Identifier(MOD_ID, "technical"),
      () -> new ItemStack(ModItems.INVISIBLE_LIGHT_TWEAKER)
  );
  public static final ItemGroup CREATURES_GROUP = FabricItemGroupBuilder.build(
      new Identifier(MOD_ID, "creatures"),
      () -> new ItemStack(ModBlocks.LIVING_BLOCK)
  );

  public static final Identifier VOID_DIMENSION_EFFECTS_KEY = new Identifier(MOD_ID, "void");

  @Override
  public void onInitialize() {
    ModBlocks.init();
    ModItems.init();
    ModBlockEntities.init();
    ModSounds.init();
    // Inject custom dimension effects. Custom dimension and dimension type are added through datapack.
    DimensionEffectsAccessor.getIdentifierMap().put(VOID_DIMENSION_EFFECTS_KEY, new VoidDimensionEffects());
    this.registerServerPacketHandlers();
    this.registerCommands();
  }

  /**
   * Registers all packets and associated handlers.
   */
  private void registerServerPacketHandlers() {
    PacketRegistry.registerPacket(
        C2SPacketFactory.LIGHT_ORB_CONTROLLER_DATA_PACKET_ID,
        LightOrbControllerDataPacket.class,
        new LightOrbControllerDataPacket.ServerHandler()
    );
  }

  /**
   * Registers all custom commands.
   */
  private void registerCommands() {
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> SetPassableCommand.register(dispatcher));
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> PushCommand.register(dispatcher));
  }
}
