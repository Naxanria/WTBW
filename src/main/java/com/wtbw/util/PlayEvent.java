package com.wtbw.util;

import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class PlayEvent
{
  public static class Particle
  {
    public static final int SMOKE = 2000;
    public static final int PORTAL = 2003;
    public static final int FLAME_SMOKE = 2004;
    public static final int BONEMEAL = 2005;
    public static final int EXPLOSION = 2008;
  }
  
  public static class Sound
  {
    public static final int BLOCK_DISPENSER_DISPENSE = 1000;
    public static final int BLOCK_DISPENSER_FAIL = 1001;
    public static final int BLOCK_DISPENSER_LAUNCH = 1002;
    public static final int ENTITY_ENDER_EYE_LAUNCH = 1003;
    public static final int ENTITY_FIREWORK_ROCKET_SHOOT = 1004;
    public static final int BLOCK_IRON_DOOR_OPEN = 1005;
    public static final int BLOCK_WOODEN_DOOR_OPEN = 1006;
    public static final int BLOCK_WOODEN_TRAPDOOR_OPEN = 1007;
    public static final int BLOCK_FENCE_GATE_OPEN = 1008;
    public static final int BLOCK_FIRE_EXTINGUISH = 1009;
    public static final int RECORD = 1010;
    public static final int BLOCK_IRON_DOOR_CLOSE = 1011;
    public static final int BLOCK_WOODEN_DOOR_CLOSE = 1012;
    public static final int BLOCK_WOODEN_TRAPDOOR_CLOSE = 1013;
    public static final int BLOCK_FENCE_GATE_CLOSE = 1014;
    public static final int ENTITY_GHAST_WARN = 1015;
    public static final int ENTITY_GHAST_SHOOT = 1016;
    public static final int ENTITY_ENDER_DRAGON_SHOOT = 1017;
    public static final int ENTITY_BLAZE_SHOOT = 1018;
    public static final int ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR = 1019;
    public static final int ENTITY_ZOMBIE_ATTACK_IRON_DOOR = 1020;
    public static final int ENTITY_ZOMBIE_BREAK_WOODEN_DOOR = 1021;
    public static final int ENTITY_WITHER_BREAK_BLOCK = 1022;
    public static final int ENTITY_WITHER_SHOOT = 1024;
    public static final int ENTITY_BAT_TAKEOFF = 1025;
    public static final int ENTITY_ZOMBIE_INFECT = 1026;
    public static final int ENTITY_ZOMBIE_VILLAGER_CONVERTED = 1027;
    public static final int BLOCK_ANVIL_DESTROY = 1029;
    public static final int BLOCK_ANVIL_USE = 1030;
    public static final int BLOCK_ANVIL_LAND = 1031;
    public static final int BLOCK_PORTAL_TRAVEL = 1032;
    public static final int BLOCK_CHORUS_FLOWER_GROW = 1033;
    public static final int BLOCK_CHORUS_FLOWER_DEATH = 1034;
    public static final int BLOCK_BREWING_STAND_BREW = 1035;
    public static final int BLOCK_IRON_TRAPDOOR_CLOSE = 1036;
    public static final int BLOCK_IRON_TRAPDOOR_OPEN = 1037;
    public static final int ENTITY_PHANTOM_BITE = 1039;
    public static final int ENTITY_ZOMBIE_CONVERTED_TO_DROWNED = 1040;
    public static final int ENTITY_HUSK_CONVERTED_TO_ZOMBIE = 1041;
    public static final int BLOCK_GRINDSTONE_USE = 1042;
    public static final int ITEM_BOOK_PAGE_TURN = 1043;
    
    public static final int ENTITY_ENDER_DRAGON_GROWL = 3001;
  }
  
  public static class Combined
  {
    public static final int COMPOSTER = 1500;
    public static final int BLOCK_LAVA_EXTINGUISH = 1501;
    public static final int BLOCK_REDSTONE_TORCH_BURNOUT = 1502;
    public static final int BLOCK_END_PORTAL_FRAME_FILL = 1503;
    
    public static final int BLOCK_DESTROYED = 2001;
    public static final int SPLASH_POTION = 2002;
    public static final int SPLASH_POTION_INSTANT = 2007;
    public static final int DRAGON_BREATH = 2006;
    
    public static final int BLOCK_END_GATEWAY_SPAWN = 3000;
  }
  
  public static void boneMeal(World world, BlockPos pos, int count)
  {
    world.playEvent(Particle.BONEMEAL, pos, count);
  }
  
  public static void smoke(World world, BlockPos pos, Direction direction)
  {
    world.playEvent(Particle.SMOKE, pos, direction.ordinal());
  }
}
