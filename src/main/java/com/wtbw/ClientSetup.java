package com.wtbw;

import com.wtbw.gui.container.ModContainers;
import com.wtbw.gui.screen.TieredFurnaceScreen;
import com.wtbw.gui.screen.TrashCanScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class ClientSetup {
    public static void init() {
        ScreenManager.registerFactory(ModContainers.TIERED_FURNACE, TieredFurnaceScreen::new);
        ScreenManager.registerFactory(ModContainers.TRASHCAN, TrashCanScreen::new);
    }

    public static World getWorld() {
        return Minecraft.getInstance().world;
    }
}
