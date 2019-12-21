package com.wtbw.config;

import net.minecraftforge.common.ForgeConfigSpec;

/*
  @author: Naxanria
*/
public abstract class SubConfig
{
  protected final ForgeConfigSpec.Builder builder;
  
  public SubConfig(ForgeConfigSpec.Builder builder)
  {
    this.builder = builder;
  }
  
  protected abstract void init();
  
  protected abstract void reload();
  
  protected ForgeConfigSpec.Builder push(String name)
  {
    return builder.push(name);
  }
  
  protected ForgeConfigSpec.Builder pop()
  {
    return builder.pop();
  }
}
