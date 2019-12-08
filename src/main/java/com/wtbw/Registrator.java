package com.wtbw;

import com.wtbw.block.*;
import com.wtbw.block.ctm.CTMBlock;
import com.wtbw.block.decoration.LavaBlock;
import com.wtbw.block.decoration.WaterBlock;
import com.wtbw.block.redstone.RedstoneEmitterBlock;
import com.wtbw.block.redstone.RedstoneTimerBlock;
import com.wtbw.block.spikes.SpikesBlock;
import com.wtbw.block.spikes.SpikesType;
import com.wtbw.block.trashcan.EnergyTrashCanBlock;
import com.wtbw.block.trashcan.FluidTrashCanBlock;
import com.wtbw.block.trashcan.TrashCanBlock;
import com.wtbw.config.CommonConfig;
import com.wtbw.gui.container.BaseTileContainer;
import com.wtbw.gui.container.TieredFurnaceContainer;
import com.wtbw.gui.container.TrashCanContainer;
import com.wtbw.gui.container.VacuumChestContainer;
import com.wtbw.item.MagnetItem;
import com.wtbw.item.tools.*;
import com.wtbw.tile.EntityPusherTileEntity;
import com.wtbw.tile.MagnetInhibitorTileEntity;
import com.wtbw.tile.VacuumChestTileEntity;
import com.wtbw.tile.furnace.FurnaceTier;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;
import com.wtbw.util.TextComponentBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class Registrator
{
  private static List<BlockItem> blockItems = new ArrayList<>();
  static List<CTMBlock> ctmBlocks = new ArrayList<>();
  private static IForgeRegistry<Block> blockRegistry;
  private static IForgeRegistry<Item> itemRegistry;
  private static IForgeRegistry<TileEntityType<?>> tileRegistry;
  private static IForgeRegistry<ContainerType<?>> containerRegistry;

  private static void registerAllBlocks()
  {
    // register blocks here
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(5, 6)), "charcoal_block", false);
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(5, 6)), "blaze_block", false);

    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).lightValue(15)), "lava_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).lightValue(15)), "lava_chiseled_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "water_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "water_chiseled_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "polished_andesite_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "polished_diorite_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "polished_granite_brick");

    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "blue_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "brown_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "cyan_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "gray_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "green_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "lime_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "light_blue_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "light_gray_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "magenta_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "orange_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "pink_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "purple_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "red_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "white_redstone_lamp");
    register(new RedstoneLampBlock(getBlockProperties(Material.REDSTONE_LIGHT).lightValue(15).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "yellow_redstone_lamp");

    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(3)), "groundium");

    register(new LavaBlock(getBlockProperties(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).lightValue(15)), "lava_glass");
    register(new WaterBlock(getBlockProperties(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), "water_glass");

    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.IRON), "iron_furnace");
    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.GOLD), "gold_furnace");
    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.DIAMOND), "diamond_furnace");
    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.END), "end_furnace");

    register(new RedstoneTimerBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "redstone_timer");
    register(new RedstoneEmitterBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "redstone_emitter");

    register(new TrashCanBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "trashcan");
    register(new FluidTrashCanBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "fluid_trashcan");
    register(new EnergyTrashCanBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "energy_trashcan");

    register(new BaseTileBlock<MagnetInhibitorTileEntity>(getBlockProperties(Material.IRON).hardnessAndResistance(4),
               (world, state) -> new MagnetInhibitorTileEntity())
             {
               @Override
               public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag)
               {
                 tooltip.add(TextComponentBuilder.createTranslated(WTBW.MODID + ".tooltip.magnet_inhibitor", 3).aqua().build());

                 super.addInformation(stack, world, tooltip, flag);
               }
             },
      "magnet_inhibitor");
    
    register(new BaseTileBlock<>(getBlockProperties(Material.IRON).hardnessAndResistance(4), (world, state) -> new VacuumChestTileEntity()), "vacuum_chest");
    
    register(new PushBlock(getBlockProperties(Material.IRON).hardnessAndResistance(1), EntityPusherTileEntity.PushMode.PUSH), "pusher");
    register(new PushBlock(getBlockProperties(Material.IRON).hardnessAndResistance(1), EntityPusherTileEntity.PushMode.PULL), "puller");
    
    register(new SpikesBlock(getBlockProperties(Material.ROCK).hardnessAndResistance(3), SpikesType.BAMBOO), "bamboo_spikes");
    register(new SpikesBlock(getBlockProperties(Material.ROCK).hardnessAndResistance(3), SpikesType.WOODEN), "wooden_spikes");
    register(new SpikesBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4), SpikesType.IRON), "iron_spikes");
    register(new SpikesBlock(getBlockProperties(Material.IRON).hardnessAndResistance(5), SpikesType.GOLD), "gold_spikes");
    register(new SpikesBlock(getBlockProperties(Material.IRON).hardnessAndResistance(6), SpikesType.DIAMOND), "diamond_spikes");
    
    register(new GreenHouseGlass(getBlockProperties(Material.GLASS).hardnessAndResistance(1)), "greenhouse_glass");
    /*
    //CTM is currently broken, dont use it
    
    register(new CTMBlock(getBlockProperties(Material.IRON).hardnessAndResistance(1), "connected/test/test"), "test_block");
    register(new CTMBlock(getBlockProperties(Material.GLASS).hardnessAndResistance(1), "connected/clearglass/clearglass")
    {
      @Override
      public BlockRenderLayer getRenderLayer()
      {
        return BlockRenderLayer.CUTOUT;
      }
    }, "clearglass");
    */
  }

  private static void registerAllItems()
  {
    // register items here
    register(new Item(getItemProperties())
    {
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 200;
      }
    }, "tiny_coal");

    register(new Item(getItemProperties())
    {
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 200;
      }
    }, "tiny_charcoal");

    register(new BlockItem(ModBlocks.CHARCOAL_BLOCK, getItemProperties())
    {
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 14400;
      }
    }, "charcoal_block");

    register(new BlockItem(ModBlocks.BLAZE_BLOCK, getItemProperties())
    {
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 21600;
      }
    }, "blaze_block");

    // durability multiplier
    int dMul = CommonConfig.get().toolsDurabilityMultiplier.get();
    register(new HammerItem(ItemTier.STONE, 6, -3.6f, getItemProperties().maxDamage(Items.STONE_PICKAXE.getMaxDamage(null) * dMul)), "stone_hammer");
    register(new HammerItem(ItemTier.IRON, 8, -3.6f, getItemProperties().maxDamage(Items.IRON_PICKAXE.getMaxDamage(null) * dMul)), "iron_hammer");
    register(new HammerItem(ItemTier.GOLD, 6, -3.2f, getItemProperties().maxDamage(Items.GOLDEN_PICKAXE.getMaxDamage(null) * dMul)), "gold_hammer");
    register(new HammerItem(ItemTier.DIAMOND, 11, -3.6f, getItemProperties().maxDamage(Items.DIAMOND_PICKAXE.getMaxDamage(null) * dMul)), "diamond_hammer");
    
    register(new GreatAxeItem(ItemTier.STONE, 6, -2.8f, getItemProperties().maxDamage(Items.STONE_AXE.getMaxDamage(null) * dMul)), "stone_greataxe");
    register(new GreatAxeItem(ItemTier.IRON, 7, -2.8f, getItemProperties().maxDamage(Items.IRON_AXE.getMaxDamage(null) * dMul)), "iron_greataxe");
    register(new GreatAxeItem(ItemTier.GOLD, 7, -2.8f, getItemProperties().maxDamage(Items.GOLDEN_AXE.getMaxDamage(null) * dMul)), "gold_greataxe");
    register(new GreatAxeItem(ItemTier.DIAMOND, 8, -2.8f, getItemProperties().maxDamage(Items.DIAMOND_AXE.getMaxDamage(null) * dMul)), "diamond_greataxe");
    
    register(new ExcavatorItem(ItemTier.STONE, 4, -2.5f, getItemProperties().maxDamage(Items.STONE_SHOVEL.getMaxDamage(null) * dMul)), "stone_excavator");
    register(new ExcavatorItem(ItemTier.IRON, 5, -2.5f, getItemProperties().maxDamage(Items.IRON_SHOVEL.getMaxDamage(null) * dMul)), "iron_excavator");
    register(new ExcavatorItem(ItemTier.GOLD, 6, -2.5f, getItemProperties().maxDamage(Items.GOLDEN_SHOVEL.getMaxDamage(null) * dMul)), "gold_excavator");
    register(new ExcavatorItem(ItemTier.DIAMOND, 6, -2.5f, getItemProperties().maxDamage(Items.DIAMOND_SHOVEL.getMaxDamage(null) * dMul)), "diamond_excavator");
    
    register(new Trowel(getItemProperties()), "trowel");

    register(new SwapTool(ItemTier.STONE, getItemProperties()), "stone_swap_tool");
    register(new SwapTool(ItemTier.IRON, getItemProperties()), "iron_swap_tool");
    register(new SwapTool(ItemTier.DIAMOND, getItemProperties()), "diamond_swap_tool");

    register(new MagnetItem(getItemProperties().maxStackSize(1)), "magnet");
  }

  private static void registerAllTiles()
  {
    register(ModBlocks.IRON_FURNACE);
    register(ModBlocks.GOLD_FURNACE);
    register(ModBlocks.DIAMOND_FURNACE);
    register(ModBlocks.END_FURNACE);

    register(RedstoneTimerTileEntity::new, ModBlocks.REDSTONE_TIMER, "redstone_timer");

    register(ModBlocks.TRASHCAN);
    register(ModBlocks.FLUID_TRASHCAN);
    register(ModBlocks.ENERGY_TRASHCAN);

    register(ModBlocks.MAGNET_INHIBITOR);
    
    register(ModBlocks.PUSHER);
    register(ModBlocks.PULLER);
    
    register(ModBlocks.VACUUM_CHEST);
  }

  private static void registerAllContainers()
  {
    registerContainer(TieredFurnaceContainer::new, "tiered_furnace");
    registerContainer(TrashCanContainer::new, "trashcan");
    registerContainer(VacuumChestContainer::new, "vacuum_chest");
  }

  ////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////

  private static Item.Properties getItemProperties()
  {
    return new Item.Properties().group(WTBW.GROUP);
  }

  private static Block.Properties getBlockProperties(Material material)
  {
    return Block.Properties.create(material);
  }


  public static void registerBlocks(RegistryEvent.Register<Block> event)
  {
    blockRegistry = event.getRegistry();

    registerAllBlocks();
  }

  public static void registerItems(RegistryEvent.Register<Item> event)
  {
    itemRegistry = event.getRegistry();
    for (BlockItem blockItem : blockItems)
    {
      itemRegistry.register(blockItem);
    }

    blockItems.clear();

    registerAllItems();
  }

  public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event)
  {
    tileRegistry = event.getRegistry();

    registerAllTiles();
  }

  public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
  {
    containerRegistry = event.getRegistry();

    registerAllContainers();
  }

  private static void registerContainer(IContainerFactory factory, String name)
  {
    containerRegistry.register(IForgeContainerType.create((windowId, inv, data) ->
      factory.create(windowId, ClientSetup.getWorld(), data.readBlockPos(), inv)
    ).setRegistryName(WTBW.MODID, name));
  }

  private static <T extends Block> T register(T block, String registryName)
  {
    return register(block, registryName, true, null);
  }

  private static <T extends Block> T register(T block, String registryName, boolean createBlockItem)
  {
    return register(block, registryName, createBlockItem, null);
  }

  private static <T extends Block> T register(T block, String registryName, boolean createBlockItem, Item.Properties blockItemProperties)
  {
    block.setRegistryName(WTBW.MODID, registryName);
    if (createBlockItem)
    {
      if (blockItemProperties == null)
      {
        blockItemProperties = getItemProperties();
      }
      blockItems.add((BlockItem) new BlockItem(block, blockItemProperties).setRegistryName(WTBW.MODID, registryName));
    }
    
    if (block instanceof CTMBlock)
    {
      ctmBlocks.add((CTMBlock) block);
    }

    blockRegistry.register(block);

    return block;
  }

  private static <T extends Item> T register(T item, String name)
  {
    itemRegistry.register(item.setRegistryName(WTBW.MODID, name));
    return item;
  }

  private static TileEntityType<?> register(BaseTileBlock tileBlock)
  {
    return register(tileBlock.tileEntityProvider::get, tileBlock, tileBlock.getRegistryName().getPath());
  }

  private static TileEntityType<?> register(Supplier<TileEntity> factory, Block block, String registryName)
  {
    TileEntityType<TileEntity> type = TileEntityType.Builder.create(factory, block).build(null);
    type.setRegistryName(WTBW.MODID, registryName);
    tileRegistry.register(type);
    return type;
  }

  interface IContainerFactory
  {
    BaseTileContainer<?> create(int windowId, World world, BlockPos pos, PlayerInventory inv);
  }

}
