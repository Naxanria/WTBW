package com.wtbw.client.rendering;

import com.wtbw.item.tools.ExcavatorItem;
import com.wtbw.item.tools.GreatAxeItem;
import com.wtbw.item.tools.HammerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/*
  @author: Naxanria
*/
public class RenderManager
{
//  public static void render(final RenderWorldLastEvent event)
//  {
//    Minecraft instance = Minecraft.getInstance();
//    ClientPlayerEntity player = instance.player;
//    ItemStack heldStack = player.getHeldItem(Hand.MAIN_HAND);
//
//    if (heldStack.getItem() instanceof HammerItem)
//    {
//      Renderers.HAMMER_RENDERER.render(heldStack, player, event.getContext(), event.getInfo());
//    }
//  }
  
  public static void renderOutline(final DrawBlockHighlightEvent event)
  {
    Minecraft instance = Minecraft.getInstance();
    ClientPlayerEntity player = instance.player;
    ItemStack heldStack = player.getHeldItem(Hand.MAIN_HAND);
    // todo: fix outline rendering
//
//    if (heldStack.getItem() instanceof HammerItem)
//    {
//      if (Renderers.HAMMER_RENDERER.render(heldStack, player, event.getTarget(), event.getContext(), event.getInfo()))
//      {
//        event.setCanceled(true);
//      }
//    }
//    else if (heldStack.getItem() instanceof ExcavatorItem)
//    {
//      if (Renderers.EXCAVATOR_RENDERER.render(heldStack, player, event.getTarget(), event.getContext(), event.getInfo()))
//      {
//        event.setCanceled(true);
//      }
//    }
//    else if (heldStack.getItem() instanceof GreatAxeItem)
//    {
//      if (Renderers.GREAT_AXE_RENDERER.render(heldStack, player, event.getTarget(), event.getContext(), event.getInfo()))
//      {
//        event.setCanceled(true);
//      }
//    }
  }
}
