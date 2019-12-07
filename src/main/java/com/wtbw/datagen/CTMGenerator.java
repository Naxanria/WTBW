package com.wtbw.datagen;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;
import com.wtbw.WTBW;
import com.wtbw.block.ctm.CTMTextureData;
import com.wtbw.block.ctm.CTMTextureProvider;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/*
  @author: Naxanria
*/
public class CTMGenerator implements IDataProvider
{
  private final DataGenerator generator;
  
  private static Map<String, Map<String, NativeImage>> imagesMap = new HashMap<>();
  
  private static Map<String, NativeImage> convert(Path imagePath, String name)
  {
    NativeImage loaded = CTMTextureProvider.load(imagePath);
    if (loaded == null)
    {
      return new HashMap<>();
    }
    
    if (name.endsWith(".png"))
    {
      name = name.substring(0, name.length() - 4);
    }
  
    NativeImage[] split = CTMTextureProvider.split(loaded);
    Map<String, NativeImage> saveMap = new HashMap<>();
  
    for (int i = 0; i < split.length; i++)
    {
      String suffix = CTMTextureData.ctmSuffixes[i];
      String filename = name + "_" + suffix + ".png";
      saveMap.put(filename, split[i]);
    }
//
//    for (int i = 0; i < split.length; i++)
//    {
//      String suff = CTMTextureData.ctmSuffixes[i];
//      String filename = name + "_" + suff + ".png";
//      Path path = savePath.resolve(filename);
//      try
//      {
//        File file = path.toFile();
//        if (!file.exists())
//        {
//          File parentFile = file.getParentFile();
//          if (!parentFile.exists())
//          {
//            WTBW.LOGGER.info("Creating folders for {}", parentFile);
//            parentFile.mkdirs();
//          }
//          WTBW.LOGGER.info("Creating image file {}", path);
//          file.createNewFile();
//        }
//
//        split[i].write(path);
//      }
//      catch (IOException e)
//      {
//        WTBW.LOGGER.error("Could not save image to {}", path);
//        e.printStackTrace();
//      }
//    }
    
    return saveMap;
  }
  
  public CTMGenerator(DataGenerator generator)
  {
    this.generator = generator;
  }
  
  private void convertAll(Path inputFolder)
  {
    // read all images
    // convert them, output into folder by <filename/filename_suffix>
//    WTBW.LOGGER.info("Current directory: {}", inputFolder);
    try
    {
      Files.find(inputFolder, 1, (path, basicFileAttributes) -> !basicFileAttributes.isDirectory())
        .forEach(path ->
        {
          String imageName = path.getFileName().toString();
          WTBW.LOGGER.info("Found to convert: {}", imageName);
          
          String name = imageName.substring(0, imageName.length() - 4);
          imagesMap.put(name, convert(path, name));
        });
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  @Override
  public void act(DirectoryCache cache) throws IOException
  {
    convertAll(generator.getOutputFolder().resolve("../connected"));
  
    Path output = generator.getOutputFolder().resolve("assets/" + WTBW.MODID + "/textures/block/connected/");
    for (Map.Entry<String, Map<String, NativeImage>> imageData : imagesMap.entrySet())
    {
      String name = imageData.getKey();
      Path folderOutput = output.resolve(name + "/");
      Files.createDirectories(folderOutput);
      
      for (Map.Entry<String, NativeImage> images : imageData.getValue().entrySet())
      {
        String fileName = images.getKey();
        NativeImage image = images.getValue();
        Path path = folderOutput.resolve(fileName);
        if (!Files.exists(path))
        {
          Files.createFile(path);
        }
        image.write(path);
  
        cache.recordHash(path, fileName + System.currentTimeMillis());
      }
    }
  }
  
  @Override
  public String getName()
  {
    return "CTM";
  }
}
