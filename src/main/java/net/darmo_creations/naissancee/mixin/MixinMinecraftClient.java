package net.darmo_creations.naissancee.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Disables the annoying warning shown when loading a save with experimental settings.
 * <p>
 * Original code by <a href="https://github.com/rdvdev2/DisableCustomWorldsAdvice">rdvdev2</a>.
 *
 * @author rdvdev2
 * @author Darmo
 */
@SuppressWarnings("unused")
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
  @ModifyVariable(
      method = "startIntegratedServer(Ljava/lang/String;Ljava/util/function/Function;Ljava/util/function/Function;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient$WorldLoadAction;NONE:Lnet/minecraft/client/MinecraftClient$WorldLoadAction;", ordinal = 0),
      ordinal = 2,
      index = 11,
      name = "bl2",
      require = 1
  )
  private boolean replaceBl2(boolean bl2) {
    return false;
  }
}
