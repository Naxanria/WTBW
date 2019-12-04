package com.wtbw.client.rendering;

import com.wtbw.item.tools.HammerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/*
  @author: Naxanria
*/
public class RenderManager
{
  public static void render(final RenderWorldLastEvent event)
  {
    Minecraft instance = Minecraft.getInstance();
    ClientPlayerEntity player = instance.player;
    ItemStack heldStack = player.getHeldItem(Hand.MAIN_HAND);

    if (heldStack.getItem() instanceof HammerItem)
    {
      Renderers.HAMMER_RENDERER.render(heldStack, player);
    }
  }
}
