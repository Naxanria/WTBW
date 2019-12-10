package com.wtbw.tile.util;

import com.wtbw.gui.tools.ClickType;

/*
  @author: Naxanria
*/
public interface IRedstoneControlled extends IButtonHandler
{
  RedstoneControl getControl();
  
  default RedstoneMode getRedstoneMode()
  {
    return getControl().getMode();
  }
  
  default RedstoneMode[] availableModes()
  {
    return RedstoneMode.values();
  }
  
  @Override
  default boolean handleButton(int buttonID, ClickType clickType)
  {
    return getControl().handleButton(buttonID, clickType);
  }
}
