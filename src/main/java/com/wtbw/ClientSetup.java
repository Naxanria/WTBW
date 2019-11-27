package com.wtbw;

import com.wtbw.gui.container.ModContainers;
import com.wtbw.gui.screen.IronFurnaceScreen;
import net.minecraft.client.gui.ScreenManager;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class ClientSetup
{
  public static void init()
  {
    ScreenManager.registerFactory(ModContainers.IRON_FURNACE, IronFurnaceScreen::new);
  }
}
