package com.wtbw.tile.furnace;

import com.wtbw.config.WTBWConfig;

/*
  @author: Naxanria
*/
public class FurnaceTier
{
  public static final FurnaceTier IRON = new FurnaceTier(WTBWConfig.common.ironFurnaceSpeed.get(), "Iron");
  
  public final int cookTime;
  public final String name;
  
  public FurnaceTier(int cookTime, String name)
  {
    this.cookTime = cookTime;
    this.name = name;
  }
}
