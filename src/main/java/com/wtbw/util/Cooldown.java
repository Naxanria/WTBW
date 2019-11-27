package com.wtbw.util;

import net.minecraft.nbt.CompoundNBT;

/*
  @author: Naxanria
*/
public class Cooldown
{
  private int count;
  private int cooldown;
  private boolean reset = false;
  private boolean active = false;
  
  private Action callback;
  
  public Cooldown(int cooldown)
  {
    this.cooldown = cooldown;
  }
  
  public void update()
  {
    if (active)
    {
      count++;
      if (count >= cooldown)
      {
        if (active && callback != null)
        {
          callback.call();
        }
        
        if (reset)
        {
          active = true;
          count = 0;
        }
        else
        {
          active = false;
        }
      }
    }
  }
  
  public Cooldown start()
  {
    active = true;
    return this;
  }
  
  public Cooldown stop()
  {
    active = false;
    return this;
  }
  
  public Cooldown restart()
  {
    count = 0;
    active = true;
    return this;
  }
  
  public boolean isFinished()
  {
    return count >= cooldown;
  }
  
  public Cooldown setCount(int count)
  {
    this.count = count;
    return this;
  }
  
  public Cooldown setCooldown(int cooldown)
  {
    this.cooldown = cooldown;
    return this;
  }
  
  public Cooldown setReset(boolean reset)
  {
    this.reset = reset;
    return this;
  }
  
  public Cooldown setCallback(Action callback)
  {
    this.callback = callback;
    return this;
  }
  
  public int getCount()
  {
    return count;
  }
  
  public int getCooldown()
  {
    return cooldown;
  }
  
  public boolean isReset()
  {
    return reset;
  }
  
//  private int count;
//  private int cooldown;
//  private boolean reset;
//  private boolean active = false;
  
  public CompoundNBT serialize(CompoundNBT compoundNBT)
  {
    compoundNBT.putInt("count", count);
    compoundNBT.putInt("cooldown", cooldown);
    compoundNBT.putBoolean("reset", reset);
    compoundNBT.putBoolean("active", active);
    
    return compoundNBT;
  }
  
  public Cooldown deserialize(CompoundNBT compoundNBT)
  {
    count = compoundNBT.getInt("count");
    cooldown = compoundNBT.getInt("cooldown");
    reset = compoundNBT.getBoolean("reset");
    active = compoundNBT.getBoolean("active");
    
    return this;
  }
}
