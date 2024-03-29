package net.darmo_creations.naissancee.mixin;

import net.darmo_creations.naissancee.MixinUtils;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disable the rendering of the player’s hand if the client player is in adventure mode.
 */
@SuppressWarnings("unused")
@Mixin(GameRenderer.class)
public class MixinGameRenderer {
  @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
  private void onRenderHand(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo ci) {
    MixinUtils.disableIfAdventureMode(ci);
  }
}
