package com.off.blocks;

import java.util.List;
import java.util.Random;

import com.off.MainInit;
import com.off.init.ModBlocks;
import com.off.init.ModItems;
import com.off.util.IHasModel;
import com.off.util.ItemLore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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

public class BlockOre extends Block implements IHasModel
{
	public BlockOre(String name, Material material, Float hardness, Float resistance)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainInit.tabOFFBlocks);
		setHardness(hardness);
		setResistance(resistance);
		setLightOpacity(0);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		if (this == ModBlocks.ORE_SMOKE)
		{
			return 3 + rand.nextInt(12);
		}
		if (this == ModBlocks.ORE_SMOKE_EMPTY)
		{
			return 7 + rand.nextInt(21);
		}
		return 1;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (this == ModBlocks.ORE_SMOKE || this == ModBlocks.ORE_SMOKE_EMPTY)
		{
			switch(rand.nextInt(2))
			{
				case 0: return ModItems.FRAGMENT_POOR_METAL;
				case 1: return ModItems.POOR_METAL;
				default: return ModItems.POOR_METAL;
			}
		}
		else
		{
			return Item.getItemFromBlock(this);
		}
	}
	
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public void registerModels()
	{
		MainInit.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");	
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (this == ModBlocks.ORE_SMOKE || this == ModBlocks.ORE_SMOKE_EMPTY)
		{
			tooltip.add(ItemLore.elementLore[0]);
		}
		if (this == ModBlocks.ORE_SMOKE_EMPTY)
		{
			tooltip.add(ItemLore.loreAll[25]);
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		// TODO Auto-generated method stub
		return EnumRarity.COMMON;
	}

}