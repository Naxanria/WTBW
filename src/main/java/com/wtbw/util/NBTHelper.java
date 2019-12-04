package com.wtbw.util;

import net.minecraft.nbt.CompoundNBT;

/*
  @author: Naxanria
*/
public class NBTHelper
{
  public static int getInt(CompoundNBT compoundNBT, String name)
  {
    return getInt(compoundNBT, name, 0);
  }
  
  public static int getInt(CompoundNBT compoundNBT, String name, int defaultValue)
  {
    if (compoundNBT.contains(name))
    {
      return compoundNBT.getInt(name);
    }
    
    return defaultValue;
  }
  
  public static CompoundNBT getCompound(CompoundNBT compoundNBT, String name)
  {
    return getCompound(compoundNBT, name, new CompoundNBT());
  }
  
  public static CompoundNBT getCompound(CompoundNBT compoundNBT, String name, CompoundNBT defaultValue)
  {
    if (compoundNBT.contains(name))
    {
      return compoundNBT.getCompound(name);
    }
    
    return defaultValue;
  }
  
  public static float getFloat(CompoundNBT compoundNBT, String name)
  {
    return getFloat(compoundNBT, name, 0);
  }
  
  public static float getFloat(CompoundNBT compoundNBT, String name, float defaultValue)
  {
    if (compoundNBT.contains(name))
    {
      return compoundNBT.getFloat(name);
    }
    
    return defaultValue;
  }
  
  public static boolean getBoolean(CompoundNBT compoundNBT, String name, boolean defaultValue)
  {
    if (compoundNBT.contains(name))
    {
      return compoundNBT.getBoolean(name);
    }
    
    return defaultValue;
  }
}
