package com.wtbw.block.spikes;

import net.minecraft.util.DamageSource;

/*
  @author: Naxanria
*/
public class SpikesDamageSource extends DamageSource
{
  private SpikesType type;
  // todo: have an option for fakeplayer kills
  public SpikesDamageSource(SpikesType type)
  {
    super("spikes." + type.name);
    this.type = type;
  
    setDamageBypassesArmor();
    setDamageIsAbsolute();
  }
}
