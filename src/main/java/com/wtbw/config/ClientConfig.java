package com.wtbw.config;

import com.wtbw.WTBW;
import net.minecraftforge.common.ForgeConfigSpec;

/*
  @author: Naxanria
*/
public class ClientConfig
{
  private static ClientConfig instance;
  public static ClientConfig get()
  {
    return instance;
  }
  
  private final ForgeConfigSpec.Builder builder;
  
  public ForgeConfigSpec.BooleanValue showFullDurabilityOfTools;
  public ForgeConfigSpec.BooleanValue showTags;
  
  public ClientConfig(ForgeConfigSpec.Builder builder)
  {
    instance = this;
    this.builder = builder;
    
    builder.push("WTBW client settings");
    
    qol();
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
      .define(key("show_tags"), true);
      
    builder.pop();
    builder.pop();
  }
  
  private String key(String name)
  {
    return WTBW.MODID + ".config.client." + name;
  }
}
