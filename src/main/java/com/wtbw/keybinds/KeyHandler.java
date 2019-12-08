package com.wtbw.keybinds;


import com.wtbw.WTBW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyHandler
{
  private static List<KeyParser> parsers = new ArrayList<>();
  private static Map<Integer, Boolean> keyStates = new HashMap<>();
  private static Map<Integer, Boolean> previousStates = new HashMap<>();
  private static List<Integer> toWatch = new ArrayList<>();
  
  KeyHandler()
  {
  }
  
  public static void register(KeyParser parser)
  {
    if (Minecraft.getInstance() == null || parser == null || parser.keyBinding == null)
    {
      WTBW.LOGGER.warn("Failed to add keybind. This is ok in data generation mode!");
      return;
    }
    
    ClientRegistry.registerKeyBinding(parser.keyBinding);
    parsers.add(parser);
  }
  
  static void update()
  {
    previousStates = keyStates;
    keyStates = new HashMap<>();
    
    for (int code : toWatch)
    {
      keyStates.put(code, InputMappings.isKeyDown(Minecraft.getInstance().mainWindow.getHandle(), code));
    }
    
    for (KeyParser kp :
      parsers)
    {
      kp.update();
    }
  }
  
  public static boolean isKeyPressed(int keyCode)
  {
    if (!isWatching(keyCode))
    {
      return false;
    }
    
    return !previousStates.getOrDefault(keyCode, false) && keyStates.getOrDefault(keyCode, false);
  }
  
  public static boolean isKeyDown(int keyCode)
  {
    if (isWatching(keyCode))
    {
      return keyStates.getOrDefault(keyCode, false);
    }
    
    toWatch.add(keyCode);
    
    return InputMappings.isKeyDown(Minecraft.getInstance().mainWindow.getHandle(), keyCode);
  }
  
  public static boolean isKeyUp(int keyCode)
  {
    if (!isWatching(keyCode))
    {
      return false;
    }
    
    return previousStates.getOrDefault(keyCode, false) && !keyStates.getOrDefault(keyCode, false);
  }
  
  public static void watch(int keyCode)
  {
    if (!toWatch.contains(keyCode))
    {
      toWatch.add(keyCode);
    }
  }
  
  public static void watch(int... keyCodes)
  {
    for (int code : keyCodes)
    {
      watch(code);
    }
  }
  
  public static boolean isWatching(int keyCode)
  {
    return toWatch.contains(keyCode);
  }
}
