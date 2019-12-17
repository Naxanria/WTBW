package com.wtbw;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class ClientSetup
{
  public static void init()
  {
    ClientRegistration.registerScreens();
    
    ClientRegistration.setupRenderLayers();
  }

  public static World getWorld()
  {
    return Minecraft.getInstance().world;
  }
}
