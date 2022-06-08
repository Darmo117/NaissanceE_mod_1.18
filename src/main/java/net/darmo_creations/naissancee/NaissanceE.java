package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.LightOrbControllerBlockEntity;
import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.block_entities.PathCheckpoint;
import net.darmo_creations.naissancee.blocks.BlockColor;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.commands.SetPassableCommand;
import net.darmo_creations.naissancee.dimensions.VoidDimensionEffects;
import net.darmo_creations.naissancee.entities.ModEntities;
import net.darmo_creations.naissancee.items.ModItems;
import net.darmo_creations.naissancee.network.ClientToServerPacketFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.mixin.client.rendering.DimensionEffectsAccessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Mod’s main class. Common initializer for both client and server.
 */
// TODO mod icon
public class NaissanceE implements ModInitializer {
  public static final String MODID = "naissancee";
  public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

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
    ModEntities.init();
    // Inject custom dimension effects. Custom dimension and dimension type are added through datapack.
    DimensionEffectsAccessor.getIdentifierMap().put(VOID_DIMENSION_EFFECTS_KEY, new VoidDimensionEffects());
    this.registerServerPacketReceivers();
    this.registerCommands();
  }

  /**
   * Registers all custom commands.
   */
  private void registerCommands() {
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> SetPassableCommand.register(dispatcher));
  }

  private void registerServerPacketReceivers() {
    ServerPlayNetworking.registerGlobalReceiver(ClientToServerPacketFactory.LIGHT_ORB_CONTROLLER_DATA_PACKET_ID,
        NaissanceE::handleLightOrbControllerBEPacket);
  }

  private static void handleLightOrbControllerBEPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
    BlockPos pos = buf.readBlockPos();
    boolean active = buf.readBoolean();
    boolean loops = buf.readBoolean();
    boolean invisible = buf.readBoolean();
    int lightLevel = buf.readInt();
    double speed = buf.readDouble();
    final int checkpointsNb = buf.readInt();
    List<PathCheckpoint> checkpoints = new LinkedList<>();
    for (int i = 0; i < checkpointsNb; i++) {
      //noinspection ConstantConditions
      checkpoints.add(new PathCheckpoint(buf.readNbt()));
    }

    server.execute(() -> Utils.getBlockEntity(LightOrbControllerBlockEntity.class, player.world, pos)
        .ifPresent(controller -> {
          controller.setActive(active);
          controller.setLoops(loops);
          controller.setEntityInvisible(invisible);
          try {
            controller.setLightLevel(lightLevel);
            controller.setSpeed(speed);
            controller.setCheckpoints(checkpoints);
          } catch (IllegalArgumentException e) {
            NaissanceE.LOGGER.error(e.getMessage(), e);
            return;
          }
          controller.spawnOrb();
        })
    );
  }
}
