package com.wtbw.keybinds;

import com.wtbw.WTBW;
import com.wtbw.item.tools.ICycleTool;
import com.wtbw.network.CycleToolPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import org.lwjgl.glfw.GLFW;

/*
  @author: Naxanria
*/
public class KeyEventListener
{
  public static final String KEY_BIND_CATEGORY = WTBW.MODID;
  
  public static void registerKeys()
  {
    KeyHandler.register(new KeyParser(createKeyBinding("increase_tool_radius", GLFW.GLFW_KEY_EQUAL, KeyConflictContext.IN_GAME))
    {
      @Override
      public void onKeyDown()
      {
        ItemStack inHand = getInHand();
        if (inHand.getItem() instanceof ICycleTool)
        {
          CycleToolPacket.send((byte) 1);
        }
      }
  
      @Override
      public boolean isListening()
      {
        return mc.currentScreen == null && getInHand().getItem() instanceof ICycleTool;
      }
    });
  
    KeyHandler.register(new KeyParser(createKeyBinding("decrease_tool_radius", GLFW.GLFW_KEY_MINUS, KeyConflictContext.IN_GAME))
    {
      @Override
      public void onKeyDown()
      {
        ItemStack inHand = getInHand();
        if (inHand.getItem() instanceof ICycleTool)
        {
          CycleToolPacket.send((byte) -1);
        }
      }
    
      @Override
      public boolean isListening()
      {
        return mc.currentScreen == null && getInHand().getItem() instanceof ICycleTool;
      }
    });
  }
  
  private static ItemStack getInHand()
  {
    ClientPlayerEntity player = Minecraft.getInstance().player;
    if (player != null)
    {
      return player.getHeldItemMainhand();
    }
    else
    {
      return ItemStack.EMPTY;
    }
  }
  
  private static KeyBinding createKeyBinding(String name, int key, KeyConflictContext conflictContext)
  {
    return new KeyBinding(name, conflictContext, InputMappings.Type.KEYSYM, key, KEY_BIND_CATEGORY);
  }
  
  public static void update(final TickEvent.ClientTickEvent event)
  {
    if (event.phase == TickEvent.Phase.START)
    {
      KeyHandler.update();
    }
  }
}
