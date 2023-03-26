package net.darmo_creations.naissancee.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disable the rendering of hotbar and status bars if the client player is in adventure mode.
 */
@SuppressWarnings("unused")
@Mixin(InGameHud.class)
public class MixinInGameHud {
  @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
  private void onRenderHotbar(float tickDelta, MatrixStack matrixStack, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
  private void onRenderCrosshair(MatrixStack matrixStack, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
  private void onRenderStatusBars(MatrixStack matrixStack, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  @Inject(method = "renderMountHealth", at = @At("HEAD"), cancellable = true)
  private void onRenderMountHealth(MatrixStack matrixStack, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  @Inject(method = "renderMountJumpBar", at = @At("HEAD"), cancellable = true)
  private void onRenderMountJumpBar(MatrixStack matrixStack, int x, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
  private void onRenderExperienceBar(MatrixStack matrixStack, int x, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
  private void onRenderHeldItemTooltip(MatrixStack matrixStack, CallbackInfo ci) {
    disableIfAdventureMode(ci);
  }

  private static void disableIfAdventureMode(CallbackInfo ci) {
    MinecraftClient client = MinecraftClient.getInstance();
    if (client != null) {
      PlayerEntity player = client.player;
      if (player != null) {
        //noinspection DataFlowIssue
        PlayerListEntry listEntry = client.getNetworkHandler().getPlayerListEntry(player.getGameProfile().getId());
        if (listEntry != null && listEntry.getGameMode() == GameMode.ADVENTURE) {
          ci.cancel();
        }
      }
    }
  }
}
