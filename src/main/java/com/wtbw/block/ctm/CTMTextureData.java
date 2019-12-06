package com.wtbw.block.ctm;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

/*
  @author: Naxanria
*/
public class CTMTextureData
{
  public static final CTMTextureData EMPTY = new CTMTextureData(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
  
  public static final String[] ctmSuffixes = new String[]{ "m", "t", "r", "tr", "b", "tb", "br", "trb", "l", "tl", "lr", "ltr", "bl", "tlb", "lbr", "full" };
  
  private final ResourceLocation[] textures;
  
  public CTMTextureData
  (
    ResourceLocation top_left, ResourceLocation top, ResourceLocation top_right,
    ResourceLocation left, ResourceLocation middle, ResourceLocation right,
    ResourceLocation bottom_left, ResourceLocation bottom, ResourceLocation bottom_right,
    ResourceLocation full, ResourceLocation top_bottom, ResourceLocation left_right,
    ResourceLocation top_left_bottom, ResourceLocation left_bottom_right, ResourceLocation top_right_bottom, ResourceLocation left_top_right
  )
  {
    textures = new ResourceLocation[]
    {
      middle, top, right, top_right, bottom,
      top_bottom, bottom_right, top_right_bottom,
      left, top_left, left_right, left_top_right,
      bottom_left, top_left_bottom, left_bottom_right, full
    };
  }
  
  public CTMTextureData(String modid, String textureBaseName)
  {
    textures = new ResourceLocation[16];
    for (int i = 0; i < 16; i++)
    {
      textures[i] = new ResourceLocation(modid, textureBaseName + "_" + ctmSuffixes[i]);
    }
  }
  
  public void addToStitch(TextureStitchEvent.Pre event)
  {
    for (ResourceLocation texture : textures)
    {
      event.addSprite(texture);
    }
  }
  
  public ResourceLocation[] getTextures()
  {
    return textures;
  }
}
