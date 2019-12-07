package com.wtbw.datagen;

import com.wtbw.WTBW;
import com.wtbw.profiling.Timer;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.nio.file.Path;

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
