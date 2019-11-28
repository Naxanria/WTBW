package com.wtbw.config;

import com.wtbw.WTBW;
import net.minecraftforge.common.ForgeConfigSpec;

/*
  @author: Naxanria
*/
public class CommonConfig
{
  private static CommonConfig instance;
  public static CommonConfig get()
  {
    return instance;
  }
  // debug //
  public ForgeConfigSpec.BooleanValue debugOutput;
  
  // tools //
  public ForgeConfigSpec.IntValue toolsDurabilityMultiplier;
  
  // furnaces //
  public ForgeConfigSpec.IntValue ironFurnaceSpeed;
  
  // redstone //
  public ForgeConfigSpec.IntValue redstoneClockRepeat;
  public ForgeConfigSpec.IntValue redstoneClockDuration;
  
  private final ForgeConfigSpec.Builder builder;
  
  public CommonConfig(ForgeConfigSpec.Builder builder)
  {
    instance = this;
    
    this.builder = builder;
    builder.comment("WTBW common config");
    
    debug();
    blocks();
    tools();
  }
  
  private void blocks()
  {
    builder.push("blocks");
    
    furnaces();
    redstone();
    
    builder.pop();
  }
  
  private void redstone()
  {
    builder.push("redstone");
    
    builder.push("timer");
    
    redstoneClockRepeat = builder
      .comment("The time in ticks for the delay between pulses")
      .translation(key("redstone.redstone_timer_repeat"))
      .defineInRange("repeat", 10, 5, 100);
    
    redstoneClockDuration = builder
      .comment("The duration of the redstone pulse for the timer")
      .translation(key("redstone.redstone_timer_length"))
      .defineInRange("duration", 6, 1, 100);
    
    builder.pop();
    
    builder.pop();
  }
  
  private void furnaces()
  {
    builder.push("furnace");
    
    builder.push("iron");
    ironFurnaceSpeed = builder
      .comment("Iron furnace speed")
      .translation("furnace.iron_furnace_speed")
      .defineInRange("speed", 180, 1, 500);
    
    builder.pop();
    builder.pop();
  }
  
  private void tools()
  {
    builder.push("tools");
    
    toolsDurabilityMultiplier = builder
      .comment
        (
          "The multiplier for the tools durability.",
          "The durability is calculated by the equivalant tools durability",
          "multiplied by this multiplier"
        )
      .translation(key("tools.multiplier"))
      .defineInRange("multiplier", 7, 1, 15);
    
    
    builder.pop();
  }
  
  private void debug()
  {
    builder.push("debug").comment("Debug options");
    
    debugOutput = builder
      .comment("Show debug output")
      .translation(key("debug.output"))
      .define("output", false);
    
    builder.pop();
  }
  
  private String key(String name)
  {
    return WTBW.MODID + ".config.common." + name;
  }
}
