package net.darmo_creations.naissancee.items;

import net.darmo_creations.naissancee.blocks.InvisibleLightBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Tool used to change the light level of {@link InvisibleLightBlock}s.
 * <p>
 * Right-click on block to increase its light level. Sneak while right-clicking to decrease it.
 */
public class InvisibleLightTweakerItem extends Item {
  public InvisibleLightTweakerItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    World world = context.getWorld();
    BlockPos blockPos = context.getBlockPos();
    if (world.getBlockState(blockPos).getBlock() instanceof InvisibleLightBlock b) {
      //noinspection ConstantConditions
      if (context.getPlayer().isSneaking()) {
        b.decreaseLightLevel(world, blockPos);
      } else {
        b.increaseLightLevel(world, blockPos);
      }
      return ActionResult.SUCCESS;
    }
    return ActionResult.FAIL;
  }
}
