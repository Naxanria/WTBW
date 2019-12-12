package com.wtbw.gui.util;

import com.wtbw.util.QuadInt;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
  @author: Naxanria
*/
public class SpriteMap
{
  public final ResourceLocation ID;
  public final int width;
  public final int height;

  private Map<QuadInt, Sprite> spriteMap = new HashMap<>();
  
  public SpriteMap(int size, ResourceLocation ID)
  {
    this.ID = ID;
    this.width = size;
    this.height = size;
  }
  
  public SpriteMap(int width, int height, ResourceLocation ID)
  {
    this.ID = ID;
    this.width = width;
    this.height = height;
  }
  
  public Sprite getSprite(int u, int v, int size)
  {
    return getSprite(u, v, size, size);
  }
  
  public Sprite getSprite(int u, int v, int width, int height)
  {
    QuadInt index = new QuadInt(u, v, width, height);
    if (spriteMap.containsKey(index))
    {
      return spriteMap.get(index);
    }
    
    Sprite sprite = new Sprite(this, width, height, u, v);
    
    spriteMap.put(index, sprite);
    
    return sprite;
  }
  
  public List<Sprite> split(int size)
  {
    return split(size, size);
  }
  
  /**
   * Splits the map in sprites, with equal size and spacing
   * @return the list of generated sprites
   */
  public List<Sprite> split(int spriteWidth, int spriteHeight)
  {
    List<Sprite> list = new ArrayList<>();
  
    for (int u = 0; u < width; u += spriteWidth)
    {
      for (int v = 0; v < height; v += spriteHeight)
      {
        list.add(getSprite(u, v, spriteWidth, spriteHeight));
      }
    }
    
    return list;
  }
  
  public SpriteMap render(int x, int y, int width, int height, int u, int v)
  {
    return render(x, y, width, height, u, v, 0xffffffff);
  }
  
  public SpriteMap render(int x, int y, int u, int v, int width, int height, int color)
  {
    GuiUtil.renderTexture(x, y, width, height, u, v, width, height, color, ID);
    
    return this;
  }
  
  public SpriteMap render(int x, int y, Sprite sprite)
  {
    return render(x, y, 0xffffffff, sprite);
  }
  
  public SpriteMap render(int x, int y, int color, Sprite sprite)
  {
    GuiUtil.renderTexture(x, y, sprite.width, sprite.height, sprite.u, sprite.v, width, height, color, ID);
    return this;
  }
}
