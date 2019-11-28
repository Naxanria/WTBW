package com.wtbw.config;

import com.wtbw.WTBW;
import com.wtbw.tile.furnace.FurnaceTier;
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
  public FurnaceConfig ironFurnace;
  public FurnaceConfig goldFurnace;
  public FurnaceConfig diamondFurnace;
  public FurnaceConfig endFurnace;
  
  // redstone //
  public ForgeConfigSpec.IntValue redstoneClockRepeat;
  public ForgeConfigSpec.IntValue redstoneClockDuration;
  
  private final ForgeConfigSpec.Builder builder;
  
  public void reload()
  {
    ironFurnace.reload();
    goldFurnace.reload();
    diamondFurnace.reload();
    endFurnace.reload();
  }
  
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
    
    ironFurnace = new FurnaceConfig(FurnaceTier.IRON, builder);
    goldFurnace = new FurnaceConfig(FurnaceTier.GOLD, builder);
    diamondFurnace = new FurnaceConfig(FurnaceTier.DIAMOND, builder);
    endFurnace = new FurnaceConfig(FurnaceTier.END, builder);
    
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
  
  public static class FurnaceConfig
  {
    public final FurnaceTier tier;
    public final String name;
    public ForgeConfigSpec.IntValue speed;
  
    public FurnaceConfig(FurnaceTier tier, ForgeConfigSpec.Builder builder)
    {
      this.tier = tier;
      this.name = tier.name;
      init(builder);
    }
    
    void init(ForgeConfigSpec.Builder builder)
    {
      builder.push(name);
      
      speed = builder
        .comment(name + " furnace smelting speed, in how many ticks it takes to smelt")
        .translation(key("furnace." + name + "_furnace_speed"))
        .defineInRange("speed", tier.getCookTime(), 1, 500);
      
      builder.pop();
    }
  
    public void reload()
    {
      tier.setCookTime(speed.get());
    }
  }
  
  private static String key(String name)
  {
    return WTBW.MODID + ".config.common." + name;
  }
}
