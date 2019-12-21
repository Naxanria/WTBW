package com.wtbw.config;

import com.wtbw.WTBW;
import com.wtbw.item.tools.WateringCan;
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
  
  // watering cans //
  
  public ForgeConfigSpec.BooleanValue wateringCanSpreadGrass;
  public ForgeConfigSpec.IntValue wateringCanRefillRate;
  public ForgeConfigSpec.BooleanValue wateringCanMoisterize;
  
  public WateringCanConfig basicWateringCan;
  public WateringCanConfig quartzWateringCan;
  public WateringCanConfig diamondWateringCan;
  public WateringCanConfig enderWateringCan;
  
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
    push("blocks");
    
    furnaces();
    redstone();
    vacuumChest();
    puller();
    
    pop();
  }
  
  private void puller()
  {
    push("pusher_puller").comment("For both pusher and puller");
    
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
    
    pop();
  }
  
  private void vacuumChest()
  {
    push("vacuum");
    
    vacuumTickRate = builder
      .comment("The time in ticks between trying to suck up items", "default: 10")
      .translation(key("blocks.vacuum.tick_rate"))
      .defineInRange("tick_rate", 10, 1, 100);
    
    vacuumRange = builder
      .comment("The radius in which the vacuum can check", "default: 6")
      .translation(key("blocks.vacuum.range"))
      .defineInRange("range", 6, 1, 16);
    
    pop();
  }
  
  
  private void redstone()
  {
    push("redstone");
    
    push("timer");
    
    redstoneClockRepeat = builder
      .comment("The time in ticks for the delay between pulses")
      .translation(key("redstone.redstone_timer_repeat"))
      .defineInRange("repeat", 10, 5, 100);
    
    redstoneClockDuration = builder
      .comment("The duration of the redstone pulse for the timer")
      .translation(key("redstone.redstone_timer_length"))
      .defineInRange("duration", 6, 1, 100);
    
    pop();
    
    pop();
  }
  
  private void furnaces()
  {
    push("furnace");
    
    ironFurnace = new FurnaceConfig(FurnaceTier.IRON, builder);
    goldFurnace = new FurnaceConfig(FurnaceTier.GOLD, builder);
    diamondFurnace = new FurnaceConfig(FurnaceTier.DIAMOND, builder);
    endFurnace = new FurnaceConfig(FurnaceTier.END, builder);
    
    pop();
  }
  
  private void tools()
  {
    push("tools");
    
    hammers();
    
    swapping();
    
    magnet();
    
    wateringCans();
    
    pop();
  }
  
  private void wateringCans()
  {
    push("wateringCans");
    
    wateringCanSpreadGrass = builder
      .comment("Can the watering cans spread grass faster")
      .translation(key("tools.wateringcan.spread_grass"))
      .define("canSpreadGrass", true);
    
    wateringCanRefillRate = builder
      .comment("The refill rate of water/tick")
      .translation(key("tools.wateringcan.refill_rate"))
      .defineInRange("refillRate", 30, 1, 100000);
    
    wateringCanMoisterize = builder
      .comment("Moisterize farm land")
      .translation(key("tools.wateringcan.moisterize"))
      .define("moisterize", true);
    
    basicWateringCan = new WateringCanConfig(WateringCan.Tier.BASIC, "basic", builder);
    quartzWateringCan = new WateringCanConfig(WateringCan.Tier.QUARTZ, "quartz", builder);
    diamondWateringCan = new WateringCanConfig(WateringCan.Tier.DIAMOND, "diamond", builder);
    enderWateringCan = new WateringCanConfig(WateringCan.Tier.ENDER, "ender", builder);
    
    pop();
  }
  
  private void hammers()
  {
    push("hammer");
    toolsDurabilityMultiplier = builder
      .comment
        (
          "The multiplier for the tools durability.",
          "The durability is calculated by the equivalant tools durability",
          "multiplied by this multiplier"
        )
      .translation(key("tools.hammer.multiplier"))
      .defineInRange("multiplier", 7, 1, 15);
    
    pop();
  }
  
  private void swapping()
  {
    push("swapping");
    
    swapperBlackList = builder
      .comment("The blacklisted blocks")
      .translation(key("tools.swapping.blacklist"))
      .define("blacklist", "minecraft:bedrock");
    
    swapperMaxHardness = builder
      .comment("Maximum hardness that can be swapped")
      .translation(key("tools.swapping.hardness"))
      .defineInRange("max_hardness", 49, 0, Double.MAX_VALUE);
    
    pop();
  }
  
  private void magnet()
  {
    push("magnet");
    
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
    
    pop();
  }
  
  private void debug()
  {
    push("debug").comment("Debug options");
    
    debugOutput = builder
      .comment("Show debug output")
      .translation(key("debug.output"))
      .define("output", false);
    
    pop();
  }
  
  private ForgeConfigSpec.Builder push(String name)
  {
    return builder.push(name);
  }
  
  private ForgeConfigSpec.Builder pop()
  {
    return builder.pop();
  }
  
  public static class FurnaceConfig extends SubConfig
  {
    public final FurnaceTier tier;
    public final String name;
    public ForgeConfigSpec.IntValue speed;
    
    public FurnaceConfig(FurnaceTier tier, ForgeConfigSpec.Builder builder)
    {
      super(builder);
      this.tier = tier;
      this.name = tier.name;
      init();
    }
    
    @Override
    protected void init()
    {
      push(name);
      
      speed = builder
        .comment(name + " furnace smelting speed, in how many ticks it takes to smelt")
        .translation(key("furnace." + name + "_furnace_speed"))
        .defineInRange("speed", tier.getCookTime(), 1, 500);
      
      pop();
    }
    
    @Override
    public void reload()
    {
      tier.setCookTime(speed.get());
    }
  }
  
  public static class WateringCanConfig extends SubConfig
  {
    public final WateringCan.Tier tier;
    public final String name;
    
    public ForgeConfigSpec.IntValue radius;
    public ForgeConfigSpec.IntValue maxWater;
    public ForgeConfigSpec.IntValue drainRate;
    public ForgeConfigSpec.IntValue chance;
  
    public WateringCanConfig(WateringCan.Tier tier, String name, ForgeConfigSpec.Builder builder)
    {
      super(builder);
      this.tier = tier;
      this.name = name;
      init();
    }
  
    @Override
    protected void init()
    {
      push(name);
      
      WateringCan.WateringCanData data = WateringCan.getData(tier);
      String baseKey = "tools.wateringcan." + name + ".";
    
      radius = builder
        .comment("The radius of effect for the watering can", "Number MUST be uneven")
        .translation(key(baseKey + "radius"))
        .defineInRange("radius", data.radius, 1, 15);
      
      maxWater = builder
        .comment("The maximum amount of water the watering can can hold")
        .translation(key(baseKey + "max_water"))
        .defineInRange("maxWater", data.maxWater, 100, 100000);
      
      drainRate = builder
        .comment("The amount of water consumed per tick")
        .translation(key(baseKey + "drain_rate"))
        .defineInRange("drainRate", data.waterUse, 1, 10000);
      
      chance = builder
        .comment("The chance to further a growth stage")
        .translation(key(baseKey + "chance"))
        .defineInRange("chance", data.chance, 1, 100);
      
      pop();
    }
  
    @Override
    protected void reload()
    {
      WateringCan.WateringCanData data = WateringCan.getData(tier);
      data.radius = radius.get();
      data.maxWater = maxWater.get();
      data.waterUse = drainRate.get();
      data.chance = chance.get();
    }
  }
}
