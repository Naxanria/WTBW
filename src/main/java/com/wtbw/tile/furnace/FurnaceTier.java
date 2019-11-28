package com.wtbw.tile.furnace;

import com.wtbw.config.CommonConfig;
import com.wtbw.config.WTBWConfig;

/*
  @author: Naxanria
*/
public class FurnaceTier
{
  public static final FurnaceTier IRON = new FurnaceTier(180, "Iron");
  public static final FurnaceTier GOLD = new FurnaceTier(140, "Gold");
  public static final FurnaceTier DIAMOND = new FurnaceTier(100, "Diamond");
  public static final FurnaceTier END = new FurnaceTier(60, "End");
  
  private int cookTime;
  public final String name;
  
  public FurnaceTier(int cookTime, String name)
  {
    this.cookTime = cookTime;
    this.name = name;
  }
  
  public int getCookTime()
  {
    return cookTime;
  }
  
  public FurnaceTier setCookTime(int cookTime)
  {
    this.cookTime = cookTime;
    return this;
  }
}
