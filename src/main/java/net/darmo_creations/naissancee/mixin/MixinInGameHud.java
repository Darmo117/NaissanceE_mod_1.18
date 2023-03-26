package net.darmo_creations.naissancee.mixin;

import net.darmo_creations.naissancee.MixinUtils;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
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
    MixinUtils.disableIfAdventureMode(ci);
  }

  @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
  private void onRenderCrosshair(MatrixStack matrixStack, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }

  @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
  private void onRenderStatusBars(MatrixStack matrixStack, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }

  @Inject(method = "renderMountHealth", at = @At("HEAD"), cancellable = true)
  private void onRenderMountHealth(MatrixStack matrixStack, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }

  @Inject(method = "renderMountJumpBar", at = @At("HEAD"), cancellable = true)
  private void onRenderMountJumpBar(MatrixStack matrixStack, int x, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }

  @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
  private void onRenderExperienceBar(MatrixStack matrixStack, int x, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }

  @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
  private void onRenderHeldItemTooltip(MatrixStack matrixStack, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }
}
