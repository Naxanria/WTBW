package com.wtbw.block.spikes;

import com.wtbw.WTBW;
import com.wtbw.util.TextComponentBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/*
  @author: Naxanria
*/
public class SpikesBlock extends Block
{
  private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 5, 16);
  
  private final SpikesType type;
  
  public SpikesBlock(Properties properties, SpikesType type)
  {
    super(properties);
    this.type = type;
  }
  
  @Override
  public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
  {
    if (entityIn instanceof LivingEntity)
    {
      LivingEntity livingEntity = (LivingEntity) entityIn;
      if (type.lethal || livingEntity.getHealth() > 1f)
      {
//        livingEntity.attackEntityFrom(DamageSource.HOT_FLOOR, 1f);
        livingEntity.attackEntityFrom(new SpikesDamageSource(type), type.damage);
      }
    }
  }
//
//  @Override
//  public BlockRenderLayer getRenderLayer()
//  {
//    return BlockRenderLayer.CUTOUT;
//  }
  
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
  {
    return SHAPE;
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    String baseKey = WTBW.MODID + ".tooltip.spikes";
    tooltip.add(TextComponentBuilder.createTranslated(baseKey).aqua().build());
    
    String lethal = baseKey + "_lethal";
    String nonLethal = baseKey + "_non_lethal";
    tooltip.add(TextComponentBuilder.createTranslated(type.lethal ? lethal : nonLethal).yellow().build());
    
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
