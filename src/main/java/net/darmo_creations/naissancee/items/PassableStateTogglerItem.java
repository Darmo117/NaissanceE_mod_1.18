package net.darmo_creations.naissancee.items;

import net.darmo_creations.naissancee.NaissanceE;
import net.darmo_creations.naissancee.blocks.LightSensitiveBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Tool used to toggle the state of right-clicked light-sensitive blocks.
 */
public class PassableStateTogglerItem extends Item {
  public PassableStateTogglerItem() {
    super(new FabricItemSettings().group(NaissanceE.TECHNICAL_GROUP));
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    World world = context.getWorld();
    BlockPos pos = context.getBlockPos();
    BlockState blockState = world.getBlockState(pos);
    if (blockState.getBlock() instanceof LightSensitiveBlock b) {
      b.toggleState(blockState, world, pos);
      return ActionResult.SUCCESS;
    }
    return ActionResult.FAIL;
  }
}
