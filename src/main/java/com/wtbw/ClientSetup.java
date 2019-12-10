package com.wtbw;

import com.wtbw.gui.container.ModContainers;
import com.wtbw.gui.screen.*;
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
    ScreenManager.registerFactory(ModContainers.TIERED_FURNACE, TieredFurnaceScreen::new);
    ScreenManager.registerFactory(ModContainers.TRASHCAN, TrashCanScreen::new);
    ScreenManager.registerFactory(ModContainers.VACUUM_CHEST, VacuumChestScreen::new);
    ScreenManager.registerFactory(ModContainers.BLOCK_BREAKER, BlockBreakerScreen::new);
    ScreenManager.registerFactory(ModContainers.BLOCK_PLACER, BlockPlacerScreen::new);
  }

  public static World getWorld()
  {
    return Minecraft.getInstance().world;
  }
}
