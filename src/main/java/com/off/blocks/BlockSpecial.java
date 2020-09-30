package com.off.blocks;

import java.util.List;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.IHasModel;
import com.off.util.ItemLore;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSpecial extends Block implements IHasModel
{

	public BlockSpecial(String name, Material material, SoundType sound, Float hardness, Float resistance, String tooltype, Integer harvestlevel, Integer opacity, Integer luminence, Boolean unbreakable)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(sound);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(tooltype, harvestlevel);
		setLightOpacity(opacity);
		setLightLevel(luminence);
		if (unbreakable == true)
		{
			setBlockUnbreakable();
		}
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		if (this == ModBlocks.VOID_TREE_LEAVES)
		{
			return BlockRenderLayer.CUTOUT;
		}
		else
		{
			return BlockRenderLayer.SOLID;
		}
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		if (this == ModBlocks.VOID_TREE_LEAVES)
		{
			return false;
		}
		else
		{
			return true;
		}
	}



	@Override
	public void registerModels()
	{
		MainInit.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (this == ModBlocks.VOID_TREE_LEAVES || this == ModBlocks.VOID_TREE_STEM)
		{
			tooltip.add(ItemLore.loreAll[24]);
		}
	}
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.EPIC;
	}

}
