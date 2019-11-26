package com.wtbw.block;

import com.wtbw.tile.furnace.TileEntityBaseFurnace;
import net.minecraft.item.crafting.IRecipeType;

/*
  @author: Naxanria
*/
public class IronFurnaceBlock extends BaseFurnaceBlock<TileEntityBaseFurnace>
{
  public IronFurnaceBlock(Properties properties)
  {
    super(properties, (world, state) -> new TileEntityBaseFurnace(1.2f, IRecipeType.SMELTING));
  }
}
