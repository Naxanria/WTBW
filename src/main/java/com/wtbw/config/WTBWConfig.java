package com.wtbw.config;

import com.wtbw.WTBW;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

/*
  @author: Naxanria
*/
public class WTBWConfig
{
  public static Common common;
  private static ForgeConfigSpec commonSpec;
  
  public static void initCommon()
  {
    final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
    commonSpec = specPair.getRight();
    common = specPair.getLeft();
  }
  
  public static void register(final ModLoadingContext context)
  {
    initCommon();
    
    FMLJavaModLoadingContext.get().getModEventBus().addListener(WTBWConfig::reload);
    
    context.registerConfig(ModConfig.Type.COMMON, commonSpec);
  }
  
  public static void reload(ModConfig.ModConfigEvent event)
  {
//    ModConfig config = event.getConfig();
    // todo: reload client config if needed
    
  }

  public static class Common
  {
    // tools //
    public ForgeConfigSpec.IntValue toolsDurabilityMultiplier;
    
    // furnaces //
    public ForgeConfigSpec.IntValue ironFurnaceSpeed;
    
    // redstone //
    public ForgeConfigSpec.IntValue redstoneClockRepeat;
    public ForgeConfigSpec.IntValue redstoneClockDuration;
    
    private final ForgeConfigSpec.Builder builder;
    
    public Common(ForgeConfigSpec.Builder builder)
    {
      this.builder = builder;
      builder.comment("WTBW common config");
      
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
    
    private String key(String name)
    {
      return WTBW.MODID + ".config.common." + name;
    }
  }
  
  public static class Client
  {}
}
