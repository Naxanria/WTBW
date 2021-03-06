package com.wtbw.config;

import com.wtbw.WTBW;
import net.minecraftforge.common.ForgeConfigSpec;

/*
  @author: Naxanria
*/
public class ClientConfig
{
  private static ClientConfig instance;
  private final ForgeConfigSpec.Builder builder;
  public ForgeConfigSpec.BooleanValue showFullDurabilityOfTools;
  public ForgeConfigSpec.BooleanValue showTags;
  public ForgeConfigSpec.BooleanValue showTagsRequireShift;
  public ForgeConfigSpec.BooleanValue showBurnTime;
  
  public ClientConfig(ForgeConfigSpec.Builder builder)
  {
    instance = this;
    this.builder = builder;
    
    builder.comment("WTBW client settings");
    
    visuals();
    
    qol();
  }
  
  private void visuals()
  {
    builder.push("visuals").comment("Visual options");
    
    builder.pop();
  }
  
  public static ClientConfig get()
  {
    return instance;
  }
  
  private static String key(String name)
  {
    return WTBW.MODID + ".config.client." + name;
  }
  
  private void qol()
  {
    builder.push("qol").push("tooltip").comment("Quality of life options");
    
    showFullDurabilityOfTools = builder
      .comment("Shows durability in the tooltip even if tools have full durability")
      .translation(key("qol.show_full_durability"))
      .define("show_full_durability", true);
    
    showTags = builder
      .comment("Show the tags of an item")
      .translation(key("qol.show_tags"))
      .define("show_tags", true);
    
    showTagsRequireShift = builder
      .comment("Require shift to show tags")
      .translation(key("qol.show_tags_shift"))
      .define("show_tags_shift", true);
    
    showBurnTime = builder
      .comment("Shows the burn time of the item, in ticks")
      .translation(key("qol.show_burn_time"))
      .define("burn_time", true);
    
    builder.pop();
    builder.pop();
  }
  
  public void reload()
  {
  
  }
}
