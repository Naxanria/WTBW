package com.wtbw.item.tools;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

/*
  @author: Naxanria
*/
public interface ICycleTool
{
  /**
   * Cycle through the possible radius for the tool
   *
   * @param stack the item stack to cycle the radius on
   * @param dir   (-1 or 1) will cycle in that direction
   * @return the new radius
   */
  int cycleRadius(ItemStack stack, int dir);

  int getMaxRadius();

  default int getRadius(ItemStack stack)
  {
    CompoundNBT data = stack.getOrCreateChildTag("data");
    int maxRadius = getMaxRadius();

    if (data.contains("radius"))
    {
      return MathHelper.clamp(data.getInt("radius"), 1, maxRadius);
    }
    return maxRadius;
  }

  default int setRadius(ItemStack stack, int radius)
  {
    stack.getOrCreateChildTag("data").putInt("radius", radius);
    return radius;
  }
}
