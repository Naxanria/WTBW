package com.wtbw;

import com.wtbw.gui.container.ModContainers;
import com.wtbw.gui.screen.IronFurnaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;

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
  
  public static World getWorld()
  {
    return Minecraft.getInstance().world;
  }
}
