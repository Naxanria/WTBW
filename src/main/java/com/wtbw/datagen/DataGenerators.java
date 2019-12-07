package com.wtbw.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/*
  @author: Naxanria
*/
public class DataGenerators
{
  public static void gatherData(GatherDataEvent event)
  {
    DataGenerator generator = event.getGenerator();
    generator.addProvider(new CTMGenerator(generator));
  }
}
