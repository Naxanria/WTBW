package com.wtbw;

import com.wtbw.block.ctm.CTMBakedModel;
import com.wtbw.block.ctm.CTMBlock;
import com.wtbw.block.ctm.CTMTextureData;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.HashMap;
import java.util.Map;

/*
  @author: Naxanria
*/
public class ClientRegistration
{
  private static Map<CTMBlock, CTMTextureData> textureData = new HashMap<>();
  
  public static void onTextureStitch(final TextureStitchEvent.Pre event)
  {
    if (!event.getMap().getBasePath().equals("textures"))
    {
      return;
    }
  
    for (CTMBlock ctmBlock : Registrator.ctmBlocks)
    {
      
      
      String textureBase = ctmBlock.getTextureLocation();
      WTBW.LOGGER.info("Registering textures for {} [{}]", ctmBlock.getRegistryName(), textureBase);
      CTMTextureData data = new CTMTextureData(WTBW.MODID, textureBase);
      data.addToStitch(event);
      textureData.put(ctmBlock, data);
    }
  }
  
  public static void onModelBake(final ModelBakeEvent event)
  {
//    WTBW.LOGGER.info("Registering custom models");
    
    for (CTMBlock ctmBlock : Registrator.ctmBlocks)
    {
      if (!textureData.containsKey(ctmBlock))
      {
        WTBW.LOGGER.error("No textures for " + ctmBlock.getRegistryName().toString());
        continue;
      }
      CTMBakedModel bakedModel = new CTMBakedModel(DefaultVertexFormats.BLOCK, textureData.get(ctmBlock));
      WTBW.LOGGER.info("Registering models for {}", ctmBlock.getRegistryName());
      for (int i = 0; i < 64; i++)
      {
        boolean north = (i & 1) != 0;
        boolean south = ((i >> 1) & 1) != 0;
        boolean west = ((i >> 2) & 1) != 0;
        boolean east = ((i >> 3) & 1) != 0;
        boolean up = ((i >> 4) & 1) != 0;
        boolean down = ((i >> 5) & 1) != 0;
        
        String variant = "down="+down+",east="+east+",north="+north+",south="+south+",up="+up+",west="+west;
        event.getModelRegistry().put(new ModelResourceLocation(ctmBlock.getRegistryName(), variant), bakedModel);
      }
      
      event.getModelRegistry().put(new ModelResourceLocation(ctmBlock.getRegistryName(), "inventory"), new CTMBakedModel(DefaultVertexFormats.ITEM, textureData.get(ctmBlock)));
    }
  }
}
