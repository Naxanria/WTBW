package com.wtbw;

import com.wtbw.config.CommonConfig;

/*
  @author: Naxanria
*/
public class Flags
{
  public static boolean debug()
  {
    return CommonConfig.get().debugOutput.get();
  }
  
  static boolean itemFiltersLoaded;
  public static boolean isItemFiltersLoaded()
  {
    return itemFiltersLoaded;
  }
  
  static boolean jeiLoaded;
  
  public static boolean isJeiLoaded()
  {
    return jeiLoaded;
  }
}
