package com.wtbw.block.ctm;

import com.wtbw.WTBW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/*
  @author: Naxanria
*/
public class CTMTextureProvider
{
  public static Map<Integer, Integer> convertMap = new HashMap<Integer, Integer>()
  {{
    put(0, 15); put(1, 9); put(2, 1); put(3, 3);
    put(4, 5); put(5, 8); put(6, 0); put(7, 2);
    put(8, 10); put(9, 12); put(10, 4); put(11, 6);
    put(12, 13); put(13, 14); put(14, 7); put(15, 11);
  }};
  
  public static NativeImage load(Path path)
  {
    try
    {
      NativeImage image = NativeImage.read(NativeImage.PixelFormat.RGBA, new FileInputStream(path.toFile()));
      
      return image;
    }
    catch (IOException e)
    {
      WTBW.LOGGER.error("Failed to load image at path {}", path);
      e.printStackTrace();
    }
    
    return null;
  }
  
  public static NativeImage[] split(NativeImage source)
  {
    int width = source.getWidth();
    int height = source.getHeight();
    if (width != height)
    {
      throw new IllegalStateException("Width and height needs to be the same!");
    }
    
    NativeImage[] array = new NativeImage[16];
    int s = width / 4;
    
    for (int i = 0; i < 16; i++)
    {
      int x = (i % 4) * s;
      int y = (i / 4) * 4;
      
      array[convertMap.get(i)] = getPartial(source, x, y, s, s);
    }
    
    return array;
  }
  
  public static NativeImage getPartial(NativeImage source, int x, int y, int width, int height)
  {
    NativeImage image = new NativeImage(NativeImage.PixelFormat.RGBA, width, height, false);
    for (int xx = 0; xx < width; xx++)
    {
      int xp = x + xx;
      for (int yy = 0; yy < height; yy++)
      {
        int yp = y + yy;
        image.setPixelRGBA(xx, yy, source.getPixelRGBA(xp, yp));
      }
    }
    
    return image;
  }
}
