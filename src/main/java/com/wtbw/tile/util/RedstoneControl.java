package com.wtbw.tile.util;

import com.wtbw.gui.tools.ClickType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

/*
  @author: Naxanria
*/
public class RedstoneControl implements IButtonHandler
{
  public static final int BUTTON_IGNORE = 0x1000;
  public static final int BUTTON_ON = 0x1001;
  public static final int BUTTON_OFF = 0x1002;
  public static final int BUTTON_PULSE = 0x1003;
  
  protected final TileEntity tileEntity;
  private boolean active;
  private RedstoneMode mode;
  private int count;
  
  public RedstoneControl(TileEntity tileEntity, RedstoneMode mode)
  {
    this.tileEntity = tileEntity;
    this.mode = mode;
  }
  
  public boolean update()
  {
    boolean powered = tileEntity.getWorld().isBlockPowered(tileEntity.getPos());
    
    if (count > 0 && mode == RedstoneMode.PULSE && !powered)
    {
      count--;
    }
    else if (count > 0 && mode != RedstoneMode.PULSE)
    {
      count--;
    }
    
    if (count < 0)
    {
      count = 0;
    }
  
    active = false;
    switch (mode)
    {
      case IGNORE:
        active = count == 0;
        break;
      case ON:
        active = powered && count == 0;
        break;
      case OFF:
        active = !powered && count == 0;
        break;
      case PULSE:
        active = powered && count == 0;
        if (active)
        {
          resetCooldown();
        }
        break;
    }
    
    return active;
  }
  
  public void resetCooldown()
  {
    count = mode == RedstoneMode.PULSE ? 5 : 2;
  }
  
  public boolean active()
  {
    return active;
  }
  
  public RedstoneControl setMode(RedstoneMode mode)
  {
    if (this.mode != mode)
    {
      count = 0;
      this.mode = mode;
    }
    
    return this;
  }
  
  public RedstoneMode getMode()
  {
    return mode;
  }
  
  @Override
  public boolean handleButton(int buttonID, ClickType clickType)
  {
    boolean handled = false;
    switch (buttonID)
    {
      case BUTTON_IGNORE:
        mode = RedstoneMode.IGNORE;
        handled = true;
        break;
        
      case BUTTON_OFF:
        mode = RedstoneMode.OFF;
        handled = true;
        break;
        
      case BUTTON_ON:
        mode = RedstoneMode.ON;
        handled = true;
        break;
        
      case BUTTON_PULSE:
        mode = RedstoneMode.PULSE;
        handled = true;
        break;
    }
    
    if (handled)
    {
      tileEntity.markDirty();
    }
    
    return handled;
  }
  
  public int getButtonId(RedstoneMode mode)
  {
    switch (mode)
    {
      case IGNORE:
        return BUTTON_IGNORE;
        
      case ON:
        return BUTTON_ON;
        
      case OFF:
        return BUTTON_OFF;
        
      case PULSE:
        return BUTTON_PULSE;
    }
    
    return -1;
  }
  
  public CompoundNBT serialize()
  {
    CompoundNBT compound = new CompoundNBT();
    
    compound.putBoolean("active", active);
    compound.putInt("count", count);
    compound.putInt("mode", mode.ordinal());
    
    return compound;
  }
  
  public RedstoneControl deserialize(CompoundNBT compound)
  {
    active = compound.getBoolean("active");
    count = compound.getInt("count");
    mode = RedstoneMode.values()[compound.getInt("mode") % RedstoneMode.values().length];
    
    return this;
  }
}
