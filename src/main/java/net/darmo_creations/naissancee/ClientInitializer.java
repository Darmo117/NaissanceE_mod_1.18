package net.darmo_creations.naissancee;

import net.darmo_creations.naissancee.block_entities.ModBlockEntities;
import net.darmo_creations.naissancee.blocks.ModBlocks;
import net.darmo_creations.naissancee.gui.hud.HurtOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

/**
 * Client-side mod initializer.
 */
@SuppressWarnings("unused")
public class ClientInitializer implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ModBlocks.registerBlockRenderLayers();
    ModBlockEntities.registerRenderers();
    HudRenderCallback.EVENT.register(new HurtOverlay());
  }
}
