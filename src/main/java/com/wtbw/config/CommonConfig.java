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
  private final ForgeConfigSpec.Builder builder;
  // debug //
  public ForgeConfigSpec.BooleanValue debugOutput;
  
  // tools //
  public ForgeConfigSpec.IntValue toolsDurabilityMultiplier;
  
  public ForgeConfigSpec.ConfigValue<String> swapperBlackList;
  public ForgeConfigSpec.DoubleValue swapperMaxHardness;
  
  public ForgeConfigSpec.BooleanValue magnetCheckCanPickUp;
  public ForgeConfigSpec.IntValue magnetTickRate;
  public ForgeConfigSpec.IntValue magnetRadius;
  
  public ForgeConfigSpec.IntValue vacuumRange;
  public ForgeConfigSpec.IntValue vacuumTickRate;
  
  public ForgeConfigSpec.IntValue pusherRange;
  public ForgeConfigSpec.DoubleValue pusherStrength;
  public ForgeConfigSpec.IntValue pusherTickRate;
  
  // furnaces //
  public FurnaceConfig ironFurnace;
  public FurnaceConfig goldFurnace;
  public FurnaceConfig diamondFurnace;
  public FurnaceConfig endFurnace;
  
  // redstone //
  public ForgeConfigSpec.IntValue redstoneClockRepeat;
  public ForgeConfigSpec.IntValue redstoneClockDuration;
  
  public CommonConfig(ForgeConfigSpec.Builder builder)
  {
    instance = this;
    
    this.builder = builder;
    builder.comment("WTBW common config");
    
    debug();
    blocks();
    tools();
  }
  
  public static CommonConfig get()
  {
    return instance;
  }
  
  private static String key(String name)
  {
    return WTBW.MODID + ".config.common." + name;
  }
  
  public void reload()
  {
    ironFurnace.reload();
    goldFurnace.reload();
    diamondFurnace.reload();
    endFurnace.reload();
  }
  
  private void blocks()
  {
    builder.push("blocks");
    
    furnaces();
    redstone();
    vacuumChest();
    puller();
    
    builder.pop();
  }
  
  private void puller()
  {
    builder.push("pusher_puller").comment("For both pusher and puller");
    
    pusherStrength = builder
      .comment("Strength of pull/push", "default: 0.8")
      .translation(key("pusher.strength"))
      .defineInRange("strength", 0.8, 0.01, 5);
    
    pusherRange = builder
      .comment("Range of pusher/puller", "default: 6")
      .translation(key("pusher.range"))
      .defineInRange("range", 6, 1, 16);
    
    pusherTickRate = builder
      .comment("Ticks between pulls/pushes", "default: 10")
      .translation(key("pusher.tickRate"))
      .defineInRange("tick_rate", 10, 1, 100);
    
    builder.pop();
  }
  
  private void vacuumChest()
  {
    builder.push("vacuum");
    
    vacuumTickRate = builder
      .comment("The time in ticks between trying to suck up items", "default: 10")
      .translation(key("blocks.vacuum.tick_rate"))
      .defineInRange("tick_rate", 10, 1, 100);
    
    vacuumRange = builder
      .comment("The radius in which the vacuum can check", "default: 6")
      .translation(key("blocks.vacuum.range"))
      .defineInRange("range", 6, 1, 16);
    
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
    
    hammers();
    
    swapping();
    
    magnet();
    
    builder.pop();
  }
  
  private void hammers()
  {
    builder.push("hammer");
    toolsDurabilityMultiplier = builder
      .comment
        (
          "The multiplier for the tools durability.",
          "The durability is calculated by the equivalant tools durability",
          "multiplied by this multiplier"
        )
      .translation(key("tools.hammer.multiplier"))
      .defineInRange("multiplier", 7, 1, 15);
    
    builder.pop();
  }
  
  private void swapping()
  {
    builder.push("swapping");
    
    swapperBlackList = builder
      .comment("The blacklisted blocks")
      .translation(key("tools.swapping.blacklist"))
      .define("blacklist", "minecraft:bedrock");
    
    swapperMaxHardness = builder
      .comment("Maximum hardness that can be swapped")
      .translation(key("tools.swapping.hardness"))
      .defineInRange("max_hardness", 49, 0, Double.MAX_VALUE);
    
    builder.pop();
  }
  
  private void magnet()
  {
    builder.push("magnet");
    
    magnetTickRate = builder
      .comment("How often the magnet needs to check to magnetize, in ticks", "default: 10")
      .translation(key("tools.magnet.tick_rate"))
      .defineInRange("tick_rate", 10, 1, 60);
    
    magnetRadius = builder
      .comment("The radius (cube) that items can be magnetized", "default: 6")
      .translation(key("tools.magnet.radius"))
      .defineInRange("radius", 6, 1, 16);
    
    magnetCheckCanPickUp = builder
      .comment("Should the magnet only pickup items that fit into the inventory", "default: true")
      .translation(key("tools.magnet.pick_up"))
      .define("pick_up", true);
    
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
}
